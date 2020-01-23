package festivalmanager.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import festivalmanager.festival.Festival;
import festivalmanager.economics.EconomicManager;
import festivalmanager.festival.FestivalManager;
import festivalmanager.location.LocationManager;

@SpringBootTest
public class TicketManagementTest{

    @Autowired TicketRepository ticketRepository;
    @Autowired EconomicManager economicManager;
    @Autowired FestivalManager festivalManager;
    @Autowired LocationManager locationManager;
    @Autowired TicketManagement ticketManagement;

    private Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);

    @Test
    public void ticketsShouldBeAvailable(){
        assertEquals(ticketManagement.dayTicketIsAvailable(Sort.DAYTICKET, festival), true);
        assertEquals(ticketManagement.campingTicketIsAvailable(Sort.CAMPINGTICKET, festival), true);
    }

    @Test
    public void ticketsShouldNotBeAvailable(){
        Festival festival2 = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 0, 0, 50, 100, 1000, false);

        assertEquals(ticketManagement.dayTicketIsAvailable(Sort.DAYTICKET, festival2), false);
        assertEquals(ticketManagement.campingTicketIsAvailable(Sort.CAMPINGTICKET, festival2), false);
    }

    @Test
    public void shouldBuyDayticket(){
        assertNotNull(ticketManagement.buyDayticket(festival));
    }

    @Test
    public void shouldBuyCampingticket(){
        assertNotNull(ticketManagement.buyCampingticket(festival));
    }

    @Test
    public void shouldSetTicketUsed(){
        Ticket ticket = ticketManagement.buyDayticket(festival);
        ticketRepository.save(ticket);

        ticketManagement.setTicketStatus(ticket);

        assertEquals(true, ticket.getUsed());
    }

}