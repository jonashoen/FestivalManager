package festivalmanager.festival;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FestivalForm {
	@NotNull
	private long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String location;

	@NotEmpty
	private String startDate;

	private String endDate;

	@NotNull
	private int amountDaytickets;

	@NotNull
	private int amountCampingtickets;

	@NotNull
	private float priceDayticket;

	@NotNull
	private float priceCampingticket;

	@Min(0)
	private int maxVisitors;

	private boolean sellingTickets;

	/**
	 *
	 * @return id of the festival
	 */
	public long getId() {
		return id;
	}

	/**
	 *
	 * @param id: id to which festival id should set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 *
	 * @return name of the festival
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name: name to which festival name should set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return location of the festival
	 */
	public String getLocation() {
		return location;
	}

	/**
	 *
	 * @param location: location to which festival location should set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 *
	 * @return start date of festival
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 *
	 * @param startDate: start date to which festival start date should set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 *
	 * @return end date of festival
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 *
	 * @param endDate: end date to which festival end date should set
	 */
	public void setEndDate(String endDate) {
		if(endDate.equals("")) {
			endDate = null;
		}

		this.endDate = endDate;
	}

	/**
	 *
	 * @return max visitors of festival
	 */
	public int getMaxVisitors() {
		return maxVisitors;
	}

	/**
	 *
	 * @param maxVisitors: amount of visitors to which festival amountVisitors should set
	 */
	public void setMaxVisitors(int maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	/**
	 *
	 * @return if festival is selling tickets
	 */
	public boolean isSellingTickets() {
		return sellingTickets;
	}

	/**
	 *
	 * @param sellingTickets: boolean value to which festival sellingTickets should set
	 */
	public void setSellingTickets(boolean sellingTickets) {
		this.sellingTickets = sellingTickets;
	}

	/**
	 *
	 * @return festival with attributes of this FestivalForm instance
	 */
	public Festival toFestival() {
		Festival festival =
				new Festival(
						name,
						location,
						startDate,
						endDate,
						amountDaytickets,
						amountCampingtickets,
						priceDayticket,
						priceCampingticket,
						maxVisitors,
						sellingTickets
				);

		festival.setId(id);

		return festival;
	}

	/**
	 *
	 * @return amount of available day tickets
	 */
	public int getAmountDaytickets(){
		return this.amountDaytickets;
	}

	/**
	 *
	 * @return amount of available camping tickets
	 */
	public int getAmountCampingtickets(){
		return this.amountCampingtickets;
	}

	/**
	 *
	 * @return price of day tickets
	 */
	public float getPriceDayticket(){
		return priceDayticket;
	}

	/**
	 *
	 * @return price of camping tickets
	 */
	public float getPriceCampingticket(){
		return priceCampingticket;
	}

	/**
	 *
	 * @param amount: amount of day tickets which festival ticketBuilder amountDayTickets should set
	 */
	public void setAmountDaytickets(int amount){
		this.amountDaytickets = amount;
	}

	/**
	 *
	 * @param amount: amount of camping tickets which festival ticketBuilder amountCampingTickets should set
	 */
	public void setAmountCampingtickets(int amount){
		this.amountCampingtickets = amount;
	}

	/**
	 *
	 * @param price: price of day tickets which festival ticketBuilder priceCampingTickets should set
	 */
	public void setPriceDayticket(float price){
		this.priceDayticket = price;
	}

	/**
	 *
	 * @param price: price of camping tickets which festival ticketBuilder priceCampingTickets should set
	 */
	public void setPriceCampingticket(float price){
		this.priceCampingticket = price;
	}
}
