package festivalmanager.ticket;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.Errors;

import festivalmanager.AbstractIntegrationTests;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalIdForm;
import festivalmanager.festival.FestivalManager;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest<FestivalRepository> extends AbstractIntegrationTests {

    @Autowired
    TicketManagement ticketManagement;
    @Autowired
    TicketController ticketController;
    @Autowired
    FestivalManager festivalManager;
    @Autowired
    MockMvc mockMvc;
    @Mock
    Errors errors;
    @Mock
    HttpServletResponse response;

    @Test
    public void shouldRejectUnauthorizedAccess() {
        assertThatExceptionOfType(AuthenticationException.class)
                .isThrownBy(() -> ticketController.ticketOverview(new ExtendedModelMap()));
    }

    @Test
    @WithMockUser(roles = "TICKET_SALESMAN")
    public void shouldGetTicketOverview() {
        ExtendedModelMap model = new ExtendedModelMap();
        ticketController.ticketOverview(model);

        assertNotNull(model.get("festivallist"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldBuyCampingTicket() {
        Festival festival = new Festival("test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");

        ExtendedModelMap model = new ExtendedModelMap();
        ticketController.buyCampticket(festivalIdForm, errors, model);

        assertNotNull(model.get("festivalName"));
        assertNotNull(model.get("festivalStart"));
        assertNotNull(model.get("festivalEnd"));
        assertNotNull(model.get("ticketType"));
        assertNotNull(model.get("id"));
        assertNotNull(model.get("price"));
    }

    @Test
    @WithMockUser(roles = "TICKET_SALESMAN")
    public void shouldBuyDayTicket() {
        Festival festival = new Festival("test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");

        ExtendedModelMap model = new ExtendedModelMap();
        ticketController.buyDayticket(festivalIdForm, errors, model);

        assertNotNull(model.get("festivalName"));
        assertNotNull(model.get("festivalStart"));
        assertNotNull(model.get("festivalEnd"));
        assertNotNull(model.get("ticketType"));
        assertNotNull(model.get("id"));
        assertNotNull(model.get("price"));
    }

    @Test
    @WithMockUser(roles = "TICKET_SALESMAN")
    public void shouldCheckValidTicket() {
        Festival festival = new Festival("test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        Ticket ticket = ticketManagement.buyCampingticket(festival);
        festivalManager.save(festival);

        try {
            ticketController.checkTicket("test", "CAMPINGTICKET", ticket.getId().toString(), response);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
    }

}