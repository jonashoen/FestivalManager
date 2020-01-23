package festivalmanager.inventory;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.quantity.Quantity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.salespointframework.core.Currencies.EURO;

@Component
@Order(10)
public class CatalogInitializer implements DataInitializer {
	private Catalog<Item> catalog;

	/**
	 *
	 * @param catalog: Catalog object
	 */
	public CatalogInitializer(Catalog<Item> catalog) {
		this.catalog = catalog;
	}

	@Override
	public void initialize() {
		if (catalog.findAll().iterator().hasNext()) {
			return;
		}

		catalog.save(new Item("Cola", 		Money.of(3.00, EURO), Money.of(0.3, EURO), Quantity.of(10), new String[]{ "drink" }));
		catalog.save(new Item("Wasser", 		Money.of(2.00, EURO), Money.of(0.2, EURO), Quantity.of(10), new String[]{ "drink" }));
		catalog.save(new Item("Bier", 		Money.of(3.50, EURO), Money.of(0.5, EURO), Quantity.of(10), new String[]{ "drink" }));

		catalog.save(new Item("Bratwurst", 	Money.of(4.00, EURO), Money.of(1, EURO),	Quantity.of(10), new String[]{ "food"}));
		catalog.save(new Item("Schnitzelbrötchen",	 Money.of(5.50, EURO), Money.of(1, EURO),	Quantity.of(10), new String[]{ "food"}));

		catalog.save(new Item("Stehtisch",	 Money.of(0, EURO), Money.of(0, EURO),	Quantity.of(10), new String[]{ "furniture"}));
		catalog.save(new Item("Bank",	 	 Money.of(0, EURO), Money.of(0, EURO),	Quantity.of(10), new String[]{ "furniture"}));
	}
}
