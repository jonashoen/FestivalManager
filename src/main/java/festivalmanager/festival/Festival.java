package festivalmanager.festival;

import javax.persistence.*;

import festivalmanager.contract.Contract;
import festivalmanager.contract.ContractList;
import festivalmanager.economics.EconomicList;
import festivalmanager.inventory.Item;
import festivalmanager.ticket.TicketBuilder;

import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItemIdentifier;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Festival {
	public static final int START_DATE = 0;
	public static final int END_DATE = 1;

	private @Id @GeneratedValue long id;

	private String name;
	private String location;

	private Date[] festivalDate = new Date[2];

	@Embedded
	private TicketBuilder ticketBuilder;

	private EconomicList economicList = new EconomicList();
	private ContractList contractList = new ContractList();

	private int maxVisitors;
	private int currentVisitors;

	private boolean sellingTickets;

	@ElementCollection
	private final List<String> plan = new ArrayList<>();

	@ElementCollection
	private final Map<InventoryItemIdentifier, Quantity> inventory = new HashMap<>();

	@Transient
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Transient
	private boolean hasErrors;

	private Festival() {}

	/**
	 *
	 * @param name: name of the festival
	 * @param location: location of the festival
	 * @param startDate: start date of the festival
	 * @param endDate: end date of the festival
	 * @param amountDaytickets: amount of available day tickets
	 * @param amountCampingtickets: amount of available camping tickets
	 * @param priceDayticket: price of day ticket
	 * @param priceCampingticket: price of camping ticket
	 * @param maxVisitors: amount of maximum visitors
	 * @param sellingTickets: tickets are available to sell
	 */
	public Festival(String name, String location,
					String startDate, String endDate,
					int amountDaytickets, int amountCampingtickets,
					float priceDayticket, float priceCampingticket,
					int maxVisitors, boolean sellingTickets) {

		this.name = name;
		this.location = location;

		this.ticketBuilder = new TicketBuilder(id, amountDaytickets, amountCampingtickets, priceDayticket, priceCampingticket);

		if(endDate == null) {
			endDate = startDate;
		}

		setDate(START_DATE, startDate);
		setDate(END_DATE, endDate);

		this.maxVisitors = maxVisitors;
		this.sellingTickets = sellingTickets;
	}

	/**
	 *
	 * @param f1: first festival which gets compared
	 * @param f2: second festival which gets compared
	 * @return if the festivals take place at same time and location
	 */
	public static boolean areAtTheSameTimeAndPlace(Festival f1, Festival f2) {
		Date[] d1 = f1.getDate();
		Date[] d2 = f2.getDate();

		boolean sameDate =
			(d1[START_DATE].getTime() <= d2[START_DATE].getTime() && d2[START_DATE].getTime() <= d1[END_DATE].getTime()) ||
				(d2[START_DATE].getTime() <= d1[START_DATE].getTime() && d1[START_DATE].getTime() <= d2[END_DATE].getTime());

		boolean sameLocation = f1.getLocation().equals(f2.getLocation());

		return sameDate && sameLocation;
	}

	/**
	 *
	 * @return festival id
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
	 * @return festival name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 *
	 * @return url encoded festival name
	 */
	public String getUrlName() {
		return this.name.replaceAll(" ", "%20");
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
	 * @return festival location
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
	 * @return date array containing start and end date
	 */
	public Date[] getDate() {
		return festivalDate;
	}

	/**
	 *
	 * @return string array containing start and end date human readable
	 */
	public String[] getFormattedDate() {
		return new String[] { dateFormat.format(festivalDate[START_DATE]), dateFormat.format(festivalDate[END_DATE]) };
	}

	/**
	 *
	 * @return festival start date
	 */
	public String getStartDate() {
		return getFormattedDate()[START_DATE];
	}

	/**
	 *
	 * @return festival end date
	 */
	public String getEndDate() {
		return getFormattedDate()[END_DATE];
	}

	/**
	 *
	 * @param dateType: START_DATE or END_DATE
	 * @param date: formatted date string
	 */
	public void setDate(int dateType, String date) {
		try {
			festivalDate[dateType] = dateFormat.parse(date);

			switch(dateType) {
				case START_DATE:
					if(festivalDate[START_DATE].getTime() < System.currentTimeMillis()) {
						this.hasErrors = true;
					}
					break;
				case END_DATE:
					if(festivalDate[END_DATE].getTime() < festivalDate[START_DATE].getTime()) {
						this.hasErrors = true;
					}
					break;
			}

		} catch (ParseException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

			this.hasErrors = true;
		}
	}

	/**
	 *
	 * @return festival maxVisitors
	 */
	public int getMaxVisitors() {
		return maxVisitors;
	}

	/**
	 *
	 * @param maxVisitors: max visitor count to which festival maxVisitors should set
	 */
	public void setMaxVisitors(int maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	/**
	 *
	 * @return festival visitorCount
	 */
	public int getVisitorCount() {
		return currentVisitors;
	}

	/**
	 *
	 * @return festival sellingTickets
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
	 * @return festival plan as Iterable
	 */
	public Iterable<String> getPlan() {
		return plan;
	}

	/**
	 *
	 * @return festival plan as List
	 */
	public List<String> editPlan() {
		return plan;
	}

	/**
	 *
	 * @return festival inventory
	 */
	public Map<InventoryItemIdentifier, Quantity> getInventory() {
		return inventory;
	}

	/**
	 *
	 * @return festival inventory
	 */
	public Map<InventoryItemIdentifier, Quantity> editInventory() {
		return inventory;
	}

	/**
	 *
	 * @return festival ticketBuilder
	 */
	public TicketBuilder getTicketBuilder() {
		return ticketBuilder;
	}

	/**
	 *
	 * @param ticketBuilder: ticketBuilder to which festival ticketBuilder should set
	 */
	public void setTicketBuilder(TicketBuilder ticketBuilder) {
		this.ticketBuilder = ticketBuilder;
	}

	/**
	 *
	 * @return list of AccountancyEntrys
	 */
	public List<AccountancyEntry> getEconomicList(){
		return this.economicList.getList();
	}

	/**
	 *
	 * @param economicList: economicList to which festival economicList should set
	 */
	public void setEconomicList(EconomicList economicList){
		this.economicList = economicList;
	}

	/**
	 *
	 * @return contractList object
	 */
	public ContractList getContractList(){
		return contractList;
	}

	/**
	 *
	 * @param contractList: contractList to which festival contractList should set
	 */
	public void setContractList(ContractList contractList){
		this.contractList = contractList;
	}

	/**
	 *
	 * @return string representation of festival
	 */
	public String toString() {
		return 	this.id + ": " +
			this.name + " in " +
			this.location + " from " +
			this.getStartDate() + " to " +
			this.getEndDate();
	}

	/**
	 *
	 * @return boolean value if any malformed data has been passed to festival
	 */
	public boolean hasErrors() {
		return hasErrors;
	}
}
