package festivalmanager.inventory;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;

import javax.persistence.Entity;

@Entity
public class Item extends Product {
	private Money cost;
	private Quantity minimalQuantity;

	private Item() {}

	/**
	 *
	 * @param name: name of the item
	 * @param price: price of the item
	 * @param cost: cost of the item
	 * @param minimalQuantity: minimalQuantity of the item
	 * @param categories: item categories
	 */
	public Item(String name, Money price, Money cost, Quantity minimalQuantity, String[] categories) {
		super(name, price, Metric.UNIT);

		this.cost = cost;
		this.minimalQuantity = minimalQuantity;

		for(String category : categories) {
			addCategory(category);
		}
	}

	/**
	 *
	 * @return item cost
	 */
	public Money getCost() {
		return cost;
	}

	/**
	 *
	 * @param cost: cost to which item cost is set
	 */
	public void setCost(Money cost) {
		this.cost = cost;
	}

	/**
	 *
	 * @return item minimal quantity
	 */
	public Quantity getMinimalQuantity() {
		return minimalQuantity;
	}

	/**
	 *
	 * @param minimalQuantity: minimal quantity to which item minimalQuantity is set
	 */
	public void setMinimalQuantity(Quantity minimalQuantity) {
		this.minimalQuantity = minimalQuantity;
	}
}
