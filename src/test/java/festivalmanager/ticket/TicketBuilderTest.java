package festivalmanager.ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.boot.test.context.SpringBootTest;

import festivalmanager.festival.Festival;

@SpringBootTest
public class TicketBuilderTest{

    TicketBuilder ticketBuilder = new TicketBuilder(2, 100, 50, 20, 50);
    Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);

    @Test
    public void checkAllTheGetters(){
        assertEquals(Quantity.of(100, Metric.UNIT), ticketBuilder.getAmountDaytickets());
        assertEquals(Quantity.of(50, Metric.UNIT), ticketBuilder.getAmountCampingtickets());
        assertEquals(Money.of(20, "EUR"), ticketBuilder.getPriceDayticket());
        assertEquals(Money.of(50, "EUR"), ticketBuilder.getPriceCampingticket());
        assertEquals(100, ticketBuilder.getAmountDayticketsInt());
        assertEquals(50, ticketBuilder.getAmountCampingticketsInt());
        assertEquals(20, ticketBuilder.getFormattedPriceDayticket());
        assertEquals(20, ticketBuilder.getFormattedPriceCampingticket());
    }

    @Test
    public void checkAllTheSetters(){
        ticketBuilder.setAmountCampingtickets(Quantity.of(76, Metric.UNIT));
        ticketBuilder.setAmountDaytickets(Quantity.of(1500, Metric.UNIT));
        ticketBuilder.setPriceCampingticket(Money.of(11, "EUR"));
        ticketBuilder.setPriceDayticket(Money.of(2, "EUR"));

        assertEquals(Quantity.of(1500, Metric.UNIT), ticketBuilder.getAmountDaytickets());
        assertEquals(Quantity.of(76, Metric.UNIT), ticketBuilder.getAmountCampingtickets());
        assertEquals(Money.of(2, "EUR"), ticketBuilder.getPriceDayticket());
        assertEquals(Money.of(11, "EUR"), ticketBuilder.getPriceCampingticket());
    }

    @Test
    public void shouldCreateTickets(){
        assertNotNull(ticketBuilder.createCampingticket(festival));
        assertNotNull(ticketBuilder.createDayticket(festival));
    }
}