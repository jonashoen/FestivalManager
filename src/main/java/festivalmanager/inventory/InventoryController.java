package festivalmanager.inventory;

import festivalmanager.festival.FestivalManager;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItemIdentifier;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class InventoryController {
	private InventoryManager inventory;
	private Catalog<Item> catalog;

	/**
	 *
	 * @param inventory: InventoryManager object
	 * @param catalog: Catalog object
	 */
	public InventoryController(InventoryManager inventory, Catalog<Item> catalog) {
		this.inventory = inventory;
		this.catalog = catalog;
	}

	/**
	 *
	 * @param model: model to inject data to template
	 * @return name of template that should be display
	 */
	@GetMapping("/inventory")
	public String inventory(Model model) {
		model.addAttribute("inventory", inventory);

		return "inventory";
	}

	/**
	 *
	 * @return name of template that should be display
	 */
	@GetMapping("/inventory/add")
	public String add() {
		return "inventory_add_item";
	}

	/**
	 *
	 * @param name: item name
	 * @param price: item price
	 * @param cost: item cost
	 * @param minimalQuantity: item minimal quantity
	 * @param quantity: item quantity
	 * @param category: item category
	 * @return name of template that should be display
	 */
	@PostMapping("/inventory/add")
	public String add(@RequestParam String name,
					  @RequestParam float price,
					  @RequestParam float cost,
					  @RequestParam int minimalQuantity,
					  @RequestParam int quantity,
					  @RequestParam String category) {

		inventory.addItem(name, price, cost, minimalQuantity, category, quantity);

		return "redirect:/inventory";
	}

	/**
	 *
	 * @param id: item id
	 * @param amount: amount to save
	 * @return name of template that should be display
	 */
	@PostMapping("/inventory/addExisting")
	public String add(@RequestParam ProductIdentifier id,
					  @RequestParam int amount) {

		Optional<Item> itemOptional = catalog.findById(id);

		if(itemOptional.isPresent() && amount > 0) {
			inventory.save(new UniqueInventoryItem(itemOptional.get(), Quantity.of(amount, Metric.UNIT)));
		}

		return "redirect:/inventory/edit";
	}

	/**
	 *
	 * @param model: model to inject data to template
	 * @return name of template that should be display
	 */
	@GetMapping("inventory/edit")
	public String edit(Model model) {
		model.addAttribute("inventory", inventory);

		return "inventory_edit";
	}

	/**
	 *
	 * @param id: inventory item id
	 * @param amount: new amount
	 * @return name of template that should be display
	 */
	@PostMapping("inventory/edit")
	public String edit(@RequestParam InventoryItemIdentifier id, @RequestParam int amount) {
		Optional<UniqueInventoryItem> itemOptional = inventory.findById(id);

		if(itemOptional.isPresent()) {
			UniqueInventoryItem	item = itemOptional.get();
			Quantity quantity = Quantity.of(amount, Metric.UNIT);

			inventory.setQuantity(item, quantity);
		}

		return "redirect:/inventory/";
	}
}
