package festivalmanager.inventory;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class InventoryInitializer implements DataInitializer {
	private Catalog<Item> catalog;
	private InventoryManager inventory;

	/**
	 *
	 * @param catalog: Catalog object
	 * @param inventory: InventoryManager object
	 */
	public InventoryInitializer(Catalog<Item> catalog, InventoryManager inventory) {
		this.catalog = catalog;
		this.inventory = inventory;
	}

	@Override
	public void initialize() {
		if(inventory.findAll().iterator().hasNext()) {
			return;
		}

		Iterable<Item> items =  catalog.findAll();

		for(Item item : items) {
			inventory.save(new UniqueInventoryItem(item, Quantity.of(10)));
		}
	}
}
