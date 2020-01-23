package festivalmanager.ticket;

import org.javamoney.moneta.Money;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;

import festivalmanager.festival.Festival;

import javax.persistence.*;

@Embeddable
public class TicketBuilder {
	@Transient
	private long id;

	@AttributeOverrides({
			@AttributeOverride(name="amount", column= @Column(name="day_quantity_amount")),
			@AttributeOverride(name="metric", column= @Column(name="day_quantity_metric"))
	})
	private Quantity amountDaytickets;

	@AttributeOverrides({
			@AttributeOverride(name="amount", column= @Column(name="camping_quantity_amount")),
			@AttributeOverride(name="metric", column= @Column(name="camping_quantity_metric"))
	})
	private Quantity amountCampingticket;

	private Money priceDayticket;
	private Money priceCampingticket;

	public TicketBuilder(){}

	/**
	 * 	The TicketBuilder is used to hold all the tickets related to one festival.
	 * @param amountDaytickets quantity representing the amount of day tickets, that still can be sold
	 * @param amountCampingticket quantity representing the amount of camping tickets, that still can be sold
	 * @param priceDayticket price per day ticket
	 * @param priceCampingticket price per camping ticket
	 */

	public TicketBuilder(long id, int amountDaytickets , int amountCampingticket, 
						double priceDayticket, double priceCampingticket) {
		this.id = id;
		this.amountDaytickets = Quantity.of(amountDaytickets, Metric.UNIT);
		this.amountCampingticket = Quantity.of(amountCampingticket, Metric.UNIT);
		this.priceDayticket = Money.of(priceDayticket, "EUR");
		this.priceCampingticket = Money.of(priceCampingticket, "EUR");
	}

	public Quantity getAmountDaytickets() {
		return amountDaytickets;
	}

	public int getAmountDayticketsInt() {
		return amountDaytickets.getAmount().intValue();
	}

	public void setAmountDaytickets(Quantity dayticketAmount) {
		this.amountDaytickets = dayticketAmount;
	}

	public Money getPriceDayticket() {
		return priceDayticket;
	}

	public void setPriceDayticket(Money dayticketPrice) {
		this.priceDayticket = dayticketPrice;
	}

	public Quantity getAmountCampingtickets() {
		return amountCampingticket;
	}

	public int getAmountCampingticketsInt() {
		return amountCampingticket.getAmount().intValue();
	}

	public void setAmountCampingtickets(Quantity campingticketAmount) {
		this.amountCampingticket = campingticketAmount;
	}

	public Money getPriceCampingticket() {
		return priceCampingticket;
	}

	public void setPriceCampingticket(Money campingticketPrice) {
		this.priceCampingticket = campingticketPrice;
	}

	public int getFormattedPriceDayticket() {
		return priceDayticket.getNumber().intValueExact();
	}

	public int getFormattedPriceCampingticket() {
		return priceDayticket.getNumber().intValueExact();
	}

	public Campingticket createCampingticket(Festival festival) {
		return new Campingticket(String.valueOf(id), priceCampingticket, festival);
	}

	public Dayticket createDayticket(Festival festival) {
		return new Dayticket(String.valueOf(id), priceDayticket, festival);
	}
}
