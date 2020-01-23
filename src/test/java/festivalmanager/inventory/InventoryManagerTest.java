package festivalmanager.inventory;

import festivalmanager.inventory.InventoryManager;
import festivalmanager.inventory.Item;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.inventory.InventoryItemIdentifier;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InventoryManagerTest {
	@Autowired InventoryManager inventory;

	@Test
	public void canSaveItem() {
		Item item = saveTestItem(true);

		Optional<UniqueInventoryItem> uniqueInventoryItemOptional = inventory.findByItem(item);

		assertTrue(uniqueInventoryItemOptional.isPresent());
	}

	@Test
	public void canFindAllItemsExceptGivenSet() {
		Item item = saveTestItem(true);

		Optional<UniqueInventoryItem> uniqueInventoryItemOptional = inventory.findByItem(item);

		assertTrue(uniqueInventoryItemOptional.isPresent());

		UniqueInventoryItem uniqueInventoryItem = uniqueInventoryItemOptional.get();

		Set<InventoryItemIdentifier> idSet = new HashSet<>();
		idSet.add(uniqueInventoryItem.getId());

		Iterable<UniqueInventoryItem> items = inventory.findAllExcept(idSet);

		for(UniqueInventoryItem listItem : items) {
			assertNotEquals(listItem.getId(), uniqueInventoryItem.getId());
		}
	}

	@Test
	public void canFindItemsOutOfStock() {
		Item item = saveTestItem(false);

		Iterable<Item> notInStockItems = inventory.findAllNotInStock();

		boolean foundItem = false;
		for(Item notInStockItem : notInStockItems) {
			if(notInStockItem.getId() != null && notInStockItem.getId().equals(item.getId())) {
				foundItem = true;
				break;
			}
		}

		assertTrue(foundItem);
	}

	@Test
	public void canDeleteItem() {
		Item item = saveTestItem(true);

		Optional<UniqueInventoryItem> uniqueInventoryItemOptional = inventory.findByItem(item);

		assertTrue(uniqueInventoryItemOptional.isPresent());

		UniqueInventoryItem uniqueInventoryItem = uniqueInventoryItemOptional.get();

		inventory.delete(uniqueInventoryItem);

		assertTrue(inventory.findById(uniqueInventoryItem.getId()).isEmpty());
	}

	@Test
	public void canFindByCategory() {
		Iterable<UniqueInventoryItem> items = inventory.findAll();
		List<UniqueInventoryItem> foodItems = (List<UniqueInventoryItem>) inventory.findByCategory("food");

		int foodItemCount = 0;
		for(UniqueInventoryItem item : items) {
			if(item.getProduct().getCategories().toList().contains("food")) {
				foodItemCount += 1;
			}
		}

		assertEquals(foodItemCount, foodItems.size());
	}

	@Test
	public void canSetQuantity() {
		Iterable<UniqueInventoryItem> items = inventory.findAll();

		if(!items.iterator().hasNext()) {
			fail();
		}

		UniqueInventoryItem item = items.iterator().next();

		Quantity quantity = Quantity.of(20);

		inventory.setQuantity(item, quantity);

		UniqueInventoryItem updatedItem = inventory.findById(item.getId()).get();


		assertEquals(updatedItem.getQuantity().getAmount().intValue(), quantity.getAmount().intValue());
	}

	private Item saveTestItem(boolean saveToInventory) {
		return inventory.addItem("test item", 12.00f, 5.00f, 10, "test", saveToInventory ? 10 : 0);
	}
}
