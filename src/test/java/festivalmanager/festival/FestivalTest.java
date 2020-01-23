package festivalmanager.festival;

import festivalmanager.contract.ContractList;
import festivalmanager.economics.EconomicList;
import festivalmanager.economics.EconomicManager;
import festivalmanager.ticket.TicketBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FestivalTest {
	@Test
	public void canGenerateUrlName() {
		Festival festival = new Festival(
				"Test 1",
				"Dresden",
				"1987-01-01",
				"1987-01-01",
				100,
				100,
				50.0f,
				100.0f,
				1000,
				true
		);

		assertEquals("Test%201", festival.getUrlName() );
	}

	@Test
	public void canSetAndGetAttributes() {
		Festival festival = new Festival(
				"Test 1",
				"Dresden",
				"1988-01-01",
				"1988-01-01",
				100,
				100,
				50.0f,
				100.0f,
				1000,
				true
		);

		String location = "testLocation";
		festival.setLocation(location);

		assertEquals(location, festival.getLocation());


		int maxVisitors = 200;
		festival.setMaxVisitors(maxVisitors);

		assertEquals(maxVisitors, festival.getMaxVisitors());


		boolean sellingTickets = true;
		festival.setSellingTickets(sellingTickets);

		assertEquals(sellingTickets, festival.isSellingTickets());


		TicketBuilder ticketBuilder = new TicketBuilder();
		festival.setTicketBuilder(ticketBuilder);

		assertEquals(ticketBuilder, festival.getTicketBuilder());


		EconomicList economicList = new EconomicList();
		festival.setEconomicList(economicList);

		assertEquals(economicList.getList(), festival.getEconomicList());


		ContractList contractList = new ContractList();
		festival.setContractList(contractList);

		assertEquals(contractList, festival.getContractList());
	}

	@Test
	public void canDetectWrongDates() {
		Festival festival = new Festival(
				"Test 1",
				"Dresden",
				"1989-01-01",
				"1989-01-01",
				100,
				100,
				50.0f,
				100.0f,
				1000,
				true
		);

		festival.setDate(Festival.START_DATE, "test");

		assertTrue(festival.hasErrors());
	}
}
