

package festivalmanager.festival;


import festivalmanager.inventory.InventoryManager;
import festivalmanager.inventory.Item;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30)
public class FestivalInitializer implements DataInitializer {
	private FestivalManager festivals;
	private InventoryManager inventory;

	/**
	 *
	 * @param festivals: FestivalManager object
	 * @param inventory: InventoryManager inventory
	 */
	public FestivalInitializer(FestivalManager festivals, InventoryManager inventory) {
		this.festivals = festivals;
		this.inventory = inventory;
	}
	
	@Override
	public void initialize() {
		if(festivals.findAll().iterator().hasNext()) {
			return;
		}

		Festival f = festivals.save(new Festival("Abriss", "Dresden", "2020-03-20","2020-03-22", 100, 50, 20, 40, 2, true));

		festivals.updateInventoryItem(f, inventory.findAll().iterator().next().getId(), Quantity.of(7));

		festivals.save(new Festival("Phaze", "Dresden", "2020-09-30", "2020-10-02", 1000, 400, 50, 100, 2, true));
		festivals.save(new Festival("Abiball", "Dresden", "2020-8-16", "2020-8-18", 1000, 400, 50, 100, 2, true));
		festivals.save(new Festival("Silvester", "Dresden", "2020-12-31", "2021-01-01", 1000, 400, 50, 100, 2, true));
	}
}
