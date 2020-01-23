package festivalmanager.festival;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.quantity.Quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FestivalFormTest {

	@Test
	public void canGetAndSetValues() {
		FestivalForm festivalForm = new FestivalForm();

		int id = 10;
		festivalForm.setId(id);

		assertEquals(id, festivalForm.getId());


		String name = "testName";
		festivalForm.setName(name);

		assertEquals(name, festivalForm.getName());


		String location = "testLocation";
		festivalForm.setLocation(location);

		assertEquals(location, festivalForm.getLocation());


		String startDate = "2020-12-02";
		festivalForm.setStartDate(startDate);

		assertEquals(startDate, festivalForm.getStartDate());


		String endDate = "2020-12-05";
		festivalForm.setEndDate(endDate);

		assertEquals(endDate, festivalForm.getEndDate());


		int maxVisitors = 2000;
		festivalForm.setMaxVisitors(maxVisitors);

		assertEquals(maxVisitors, festivalForm.getMaxVisitors());


		boolean sellingTickets = true;
		festivalForm.setSellingTickets(sellingTickets);

		assertEquals(sellingTickets, festivalForm.isSellingTickets());


		int amountDayTickets = 100;
		festivalForm.setAmountDaytickets(amountDayTickets);

		assertEquals(amountDayTickets, festivalForm.getAmountDaytickets());


		int amountCampingTickets = 100;
		festivalForm.setAmountCampingtickets(amountDayTickets);

		assertEquals(amountCampingTickets, festivalForm.getAmountCampingtickets());


		float priceDayTicket = 50.00f;
		festivalForm.setPriceDayticket(priceDayTicket);

		assertEquals(priceDayTicket, festivalForm.getPriceDayticket());


		float priceCampingTickets = 100.00f;
		festivalForm.setPriceCampingticket(amountDayTickets);

		assertEquals(priceCampingTickets, festivalForm.getPriceCampingticket());

		Festival festival = festivalForm.toFestival();

		assertEquals(festival.getId(), festivalForm.getId());
		assertEquals(festival.getName(), festivalForm.getName());
		assertEquals(festival.getLocation(), festivalForm.getLocation());
		assertEquals(festival.getStartDate(), festivalForm.getStartDate());
		assertEquals(festival.getEndDate(), festivalForm.getEndDate());
		assertEquals(festival.getTicketBuilder().getAmountDaytickets(), Quantity.of(festivalForm.getAmountDaytickets()));
		assertEquals(festival.getTicketBuilder().getAmountCampingtickets(), Quantity.of(festivalForm.getAmountCampingtickets()));
		assertEquals(festival.getTicketBuilder().getPriceDayticket(), Money.of(festivalForm.getPriceDayticket(), "EUR"));
		assertEquals(festival.getTicketBuilder().getPriceCampingticket(), Money.of(festivalForm.getPriceCampingticket(), "EUR"));
		assertEquals(festival.getMaxVisitors(), festivalForm.getMaxVisitors());
		assertEquals(festival.isSellingTickets(), festivalForm.isSellingTickets());
	}

	@Test
	public void canSaveOneDayFestivals() {
		FestivalForm festivalForm = new FestivalForm();

		festivalForm.setEndDate("");

		assertNull(festivalForm.getEndDate());
	}
}
