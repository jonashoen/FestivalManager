package festivalmanager.inventory;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.InventoryItemIdentifier;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InventoryManager {
	private InventoryRepository inventory;
	private Catalog<Item> catalog;

	/**
	 *
	 * @param inventory: InventoryManager object
	 * @param catalog: Catalog object
	 */
	public InventoryManager(InventoryRepository inventory, Catalog<Item> catalog) {
		this.inventory = inventory;
		this.catalog = catalog;
	}

	/**
	 *
	 * @param item: item to be saved
	 */
	public void save(UniqueInventoryItem item) {
		inventory.save(item);
	}

	/**
	 *
	 * @return Iterable containing all UniqueInventoryItems
	 */
	public Iterable<UniqueInventoryItem> findAll() {
		return inventory.findAll();
	}

	/**
	 *
	 * @param exceptValues: set of InventoryItemIdentifier
	 * @return Iterable containing all UniqueInventoryItems except all items with id from exceptValues set
	 */
	public Iterable<UniqueInventoryItem> findAllExcept(Set<InventoryItemIdentifier> exceptValues) {
		List<UniqueInventoryItem> list = new ArrayList<>();

		for(UniqueInventoryItem item : findAll()) {
			boolean found = false;

			for(InventoryItemIdentifier entry : exceptValues) {
				if(item.getId() != null && item.getId().equals(entry)) {
					found = true;
					break;
				}
			}

			if(!found) {
				list.add(item);
			}
		}

		return list;
	}

	/**
	 *
	 * @param item: searched item
	 * @return optional object containing UniqueInventoryItem or nothing
	 */
	public Optional<UniqueInventoryItem> findByItem(Item item) {
		return inventory.findByProduct(item);
	}

	/**
	 *
	 * @param identifier: id of UniqueInventoryItem
	 * @return optional object containing UniqueInventoryItem or nothing
	 */
	public Optional<UniqueInventoryItem> findById(InventoryItemIdentifier identifier) {
		return inventory.findById(identifier);
	}

	/**
	 *
	 * @param category: category of searched product
	 * @return Iterable containing all UniqueInventoryItems except all items not having the searched category
	 */
	public Iterable<UniqueInventoryItem> findByCategory(String category) {
		Iterable<UniqueInventoryItem> allItems = findAll();
		List<UniqueInventoryItem> categoryItems = new ArrayList<>();

		for(UniqueInventoryItem item : allItems) {
			Streamable<String> categories = item.getProduct().getCategories();

			for(String itemCategory : categories) {
				if(itemCategory.equals(category)) {
					categoryItems.add(item);
					break;
				}
			}
		}

		return categoryItems;
	}

	/**
	 *
	 * @param item: item to delete
	 */
	public void delete(UniqueInventoryItem item) {
		inventory.delete(item);
	}

	/**
	 *
	 * @return Iterable containing all Items not in the inventory
	 */
	public Iterable<Item> findAllNotInStock() {
		Iterable<Item> allItems = catalog.findAll();
		List<Item> notInStockItems = new ArrayList<>();

		for(Item item : allItems) {
			if(inventory.findByProduct(item).isEmpty()) {
				notInStockItems.add(item);
			}
		}

		return notInStockItems;
	}

	/**
	 *
	 * @param item: item to be edited
	 * @param quantity: new quantity of the item
	 */
	public void setQuantity(UniqueInventoryItem item, Quantity quantity) {
		if(quantity.isLessThan(Quantity.of(1, Metric.UNIT))) {
			inventory.delete(item);
		} else {
			Quantity oldQuantity = item.getQuantity();

			if(quantity.isGreaterThan(oldQuantity)) {
				item.increaseQuantity(quantity.subtract(oldQuantity));
			} else if(quantity.isLessThan(oldQuantity)) {
				item.decreaseQuantity(oldQuantity.subtract(quantity));
			}

			inventory.save(item);
		}
	}

	/**
	 *
	 * @param name: item name
	 * @param price: item price
	 * @param cost: item cost
	 * @param minimalQuantity: item minimal quantity
	 * @param category:item category
	 * @param quantity: item quantity
	 * @return saved item object
	 */
	public Item addItem(String name, float price, float cost, int minimalQuantity, String category, int quantity) {
		Item item = catalog.save(
				new Item(
						name,
						Money.of(price, "EUR"),
						Money.of(cost, "EUR"),
						Quantity.of(minimalQuantity, Metric.UNIT),
						new String[]{ category }
				)
		);

		if(quantity > 0) {
			inventory.save(new UniqueInventoryItem(item, Quantity.of(quantity, Metric.UNIT)));
		}

		return item;
	}
}
