package festivalmanager.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import festivalmanager.festival.Festival;

@SpringBootTest
public class TicketTest {

    @Autowired TicketRepository ticketRepository;
    Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);

    @Test
    public void checkAllTheCampingGetters(){
        Campingticket ticket = new Campingticket("Campingticket", Money.of(20, "EUR"), festival);
        ticketRepository.save(ticket);

        assertEquals("Campingticket", ticket.getName());
        assertEquals(Money.of(20, "EUR"), ticket.getPrice());
        assertEquals(Sort.CAMPINGTICKET, ticket.getSort());
        assertEquals(false, ticket.getUsed());
        assertEquals(festival, ticket.getFestival());
        assertNotNull(ticket.getId());
    }

    @Test
    public void checkAllTheDayGetters(){
        Dayticket ticket = new Dayticket("Dayticket", Money.of(3, "EUR"), festival);
        ticketRepository.save(ticket);

        assertEquals("Dayticket", ticket.getName());
        assertEquals(Money.of(3, "EUR"), ticket.getPrice());
        assertEquals(Sort.DAYTICKET, ticket.getSort());
        assertEquals(false, ticket.getUsed());
        assertEquals(festival, ticket.getFestival());
        assertNotNull(ticket.getId());
    }

    @Test
    public void checkAllTheCampingSetters(){
        Campingticket ticket = new Campingticket("Camp", Money.of(12, "EUR"), festival);

        ticket.setUsed(true);

        assertEquals(true, ticket.getUsed());
    }

    @Test
    public void checkAllTheDaySetters(){
        Dayticket ticket = new Dayticket("Ticket", Money.of(5, "EUR"), festival);

        ticket.setUsed(true);

        assertEquals(true, ticket.getUsed());
    }
}