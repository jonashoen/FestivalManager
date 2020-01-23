package festivalmanager.festival;

import festivalmanager.economics.EconomicManager;
import festivalmanager.inventory.InventoryManager;
import festivalmanager.inventory.Item;
import festivalmanager.location.Location;
import festivalmanager.location.LocationManager;
import festivalmanager.staff.AccountManager;
import festivalmanager.staff.MessageForm;
import org.javamoney.moneta.Money;
import org.salespointframework.inventory.InventoryItemIdentifier;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class FestivalManager {
	private final FestivalRepository festivalRepository;
	private final InventoryManager inventory;
	private final LocationManager locations;
	private final EconomicManager economics;
	private final AccountManager accounts;

	/**
	 *
	 * @param festivalRepository: FestivalRepository object
	 * @param inventory: InventoryManager object
	 * @param locations: LocationManager locations
	 * @param economics: EconomicManager economics
	 * @param accounts: AccountManager object
	 */
	public FestivalManager(FestivalRepository festivalRepository,
						   InventoryManager inventory,
						   LocationManager locations,
						   EconomicManager economics,
						   @Lazy AccountManager accounts) {

		this.festivalRepository = festivalRepository;
		this.inventory = inventory;
		this.locations = locations;
		this.economics = economics;
		this.accounts = accounts;
	}

	/**
	 *
	 * @return iterable object containing all festivals
	 */
	public Iterable<Festival> findAll() {
		return festivalRepository.findAll();
	}

	/**
	 *
	 * @param id: id of festival
	 * @return optional object containing festival or nothing
	 */
	public Optional<Festival> findById(long id) {
		return festivalRepository.findById(id);
	}

	/**
	 *
	 * @param name: name of festival
	 * @return festival object with the given name or null
	 */
	public Festival findByName(String name) {
		return festivalRepository.findByName(name);
	}

	/**
	 *
	 * @param festival: festival to be saved
	 * @return updated festival object
	 */
	public Festival save(Festival festival) {
		if(festival.hasErrors()) {
			return null;
		}

		Iterable<Location> locationList = locations.findAllLocations();

		boolean foundLocation = false;
		for(Location location : locationList) {
			if(festival.getLocation().equals(location.getName())) {
				foundLocation = true;
				break;
			}
		}

		if(!foundLocation) {
			return null;
		}

		Iterable<Festival> festivals = festivalRepository.findAll();

		for(Festival f : festivals) {
			if(festival.getId() == f.getId()) {
				continue;
			}

			if(Festival.areAtTheSameTimeAndPlace(festival, f)) {
				return null;
			}
		}

		long idBeforeSaving = festival.getId();

		Festival savedFestival = festivalRepository.save(festival);

		if(idBeforeSaving == 0) {
			economics.add(
					locations.findByName(festival.getLocation()).getPrice()*-1,
					"booked location",
					savedFestival
			);
		}

		return festivalRepository.save(savedFestival);
	}

	/**
	 *
	 * @param festival: festival to be updated
	 * @return updated festival object
	 */
	public Festival update(Festival festival) {
		Optional<Festival> festivalOptional = festivalRepository.findById(festival.getId());

		if(festivalOptional.isPresent()) {
			Festival f = festivalOptional.get();

			festival.editPlan().addAll(f.editPlan());
			festival.editInventory().putAll(f.editInventory());
			festival.getEconomicList().addAll(f.getEconomicList());
			festival.getContractList().getList().addAll(f.getContractList().getList());

			return save(festival);
		}

		return null;
	}

	/**
	 *
	 * @param festival: festival to be edited
	 * @param itemId: item to be edited
	 * @param newQuantity: quantity to which item quantity is set to
	 * @return updated festival object
	 */
	public Festival updateInventoryItem(Festival festival, InventoryItemIdentifier itemId, Quantity newQuantity) {
		if(newQuantity.isLessThan(Quantity.NONE)) {
			return null;
		}

		Quantity oldQuantity = festival.editInventory().getOrDefault(itemId, Quantity.NONE);

		festival.editInventory().put(itemId, newQuantity);

		Optional<UniqueInventoryItem> itemOptional = inventory.findById(itemId);

		if(itemOptional.isPresent()) {
			UniqueInventoryItem item = itemOptional.get();

			if(item.getQuantity().add(oldQuantity).isLessThan(newQuantity)) {
				return null;
			} else if(newQuantity.equals(Quantity.NONE)) {
				festival.editInventory().remove(itemId);
			}

			boolean isFurniture = item.getProduct().getCategories().toList().contains("furniture");

			if(oldQuantity.isLessThan(newQuantity)) {
				Quantity difference = newQuantity.subtract(oldQuantity);

				item.decreaseQuantity(difference);

				if(!isFurniture) {
					economics.add(
							((Item) item.getProduct()).getCost().multiply(difference.getAmount().intValue()).negate(),
							"added " + difference + "x " + item.getProduct().getName() + " to stock ",
							festival
					);
				}
			} else if(oldQuantity.isGreaterThan(newQuantity)) {
				Quantity difference = oldQuantity.subtract(newQuantity);

				item.increaseQuantity(difference);

				if(!isFurniture) {
					economics.add(
							((Item) item.getProduct()).getCost().multiply(difference.getAmount().intValue()),
							"removed " + difference + "x " + item.getProduct().getName() + " from stock ",
							festival
					);
				}
			}

		} else {
			return null;
		}

		return save(festival);
	}

	/**
	 *
	 * @param festival: festival to be edited
	 * @param itemId: item to be edited
	 * @param quantity: quantity is bought
	 */
	public void buyInventoryItem(Festival festival, InventoryItemIdentifier itemId, Quantity quantity) {
		Optional<UniqueInventoryItem> uniqueInventoryItemOptional = inventory.findById(itemId);

		if(uniqueInventoryItemOptional.isPresent()) {
			UniqueInventoryItem item = uniqueInventoryItemOptional.get();

			Quantity newQuantity = festival.getInventory().get(itemId).subtract(quantity);

			if(newQuantity.isLessThan(Quantity.NONE)) {
				return;

			} else if(newQuantity.isLessThan(((Item) item.getProduct()).getMinimalQuantity())) {
				accounts.sendMessage(new MessageForm(
						"MANAGER",
						"MANAGER",
						null,
						"Quantity for item " + item.getProduct().getName() + " at festival " + festival.getName() + " is below minimal quantity."
				));
			}

			economics.add(
					(Money) item.getProduct().getPrice().multiply(quantity.getAmount().intValue()),
					"sold " + quantity + "x " + item.getProduct().getName(),
					festival
			);
			
			if(newQuantity.isGreaterThan(Quantity.NONE)) {
				festival.getInventory().put(itemId, newQuantity);
			} else {
				festival.getInventory().remove(itemId);
			}

			save(festival);
		}
	}

	/**
	 *
	 * @return Iterable containing all festivals sorted by date
	 */
	public Iterable<Festival> findAllSortedByDate() {
		List<Festival> festivalList = (List<Festival>) findAll();

		festivalList.sort(new Comparator<Festival>() {
			@Override
			public int compare(Festival festival1, Festival festival2) {
				Long date1 = festival1.getDate()[Festival.START_DATE].getTime();
				Long date2 = festival2.getDate()[Festival.START_DATE].getTime();
				
				return date1.compareTo(date2);
			}
		});

		return festivalList;
	}

	/**
	 *
	 * @param festival: festival that should be deleted
	 */
	public void delete(Festival festival) {
		festivalRepository.delete(festival);
	}

	/**
	 *
	 * @return amount of stored festivals
	 */
	public long getCount() {
		return festivalRepository.count();
	}
}
