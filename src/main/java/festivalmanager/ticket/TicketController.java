package festivalmanager.ticket;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import festivalmanager.festival.FestivalIdForm;

@Controller
public class TicketController {

	private final TicketManagement ticketManagement;

	public TicketController(TicketManagement ticketManagement){
		Assert.notNull(ticketManagement, "TicketManagement must not be null!");
		this.ticketManagement = ticketManagement;
	}

	//GetMapping

	/**
	 * lists all the tickets created
	 * @param model adds the tickets to the model
	 * @return links to page /ticketManagement
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('TICKET_SALESMAN') or hasRole('TICKET_SALESMAN')")
	@GetMapping(path = "/ticketManagement")
	public String ticketOverview(Model model){
		model.addAttribute("festivallist", ticketManagement.findAll());
		return "ticketManagement";
	}

	//PostMapping

	/**
	 * link to buy a camping ticket
	 * @param festivalIdForm contains the id of the festival the ticket is needed for
	 * @param result contains possible errors
	 * @param model all the information needed to print the ticket are added to this model
	 * @return leads to the ticket print page
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('TICKET_SALESMAN') or hasRole('TICKET_SALESMAN')")
	@PostMapping(path = "/ticketCamping")
	public String buyCampticket(@Valid @ModelAttribute("form") FestivalIdForm festivalIdForm, Errors result, Model model){
		return buyTicket(festivalIdForm, result, model);
	}

	/**
	 * link to buy a day ticket
	 * @param festivalIdForm contains the id of the festival the ticket is needed for
	 * @param result contains possible errors
	 * @param model all the information needed to print the ticket are added to this model
	 * @return leads to the ticket print page
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('TICKET_SALESMAN') or hasRole('TICKET_SALESMAN')")
	@PostMapping(path = "/ticketDay")
	public String buyDayticket(@Valid @ModelAttribute("form") FestivalIdForm festivalIdForm, Errors result, Model model){
		return buyTicket(festivalIdForm, result, model);
	}

	/**
	 * outsourced part used during the ticket buying process
	 * @param festivalIdForm contains the id of the festival the ticket is needed for
	 * @param result contains possible errors
	 * @param model all the information needed to print the ticket are added to this model
	 * @return leads to the ticket print page
	 */

	public String buyTicket(FestivalIdForm festivalIdForm, Errors result, Model model){
		if(result.hasErrors()){
			return "welcome";
		}
		Ticket ticket = ticketManagement.buyTicket(festivalIdForm);
		
		model.addAttribute("festivalName", ticket.getFestival().getName());
		model.addAttribute("festivalStart", ticket.getFestival().getStartDate());
		model.addAttribute("festivalEnd", ticket.getFestival().getEndDate());
		model.addAttribute("ticketType", ticket.getSort());
		model.addAttribute("id", ticket.getId());
		model.addAttribute("price", ticket.getPrice());
		return "ticket";

	}
	
	/**
	 * hands in all the information needed to check wheather a ticket is valid or has already been used
	 * @param festival name of the festival
	 * @param sort_str "CAMPINGTICKET" or "DAYTICKET"
	 * @param id_str id of the ticket
	 * @return leeds back to the main page
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('SECURITY') or hasRole('SECURITY')")
	@PostMapping(path = "/checkTicket")
	public String checkTicket(	@RequestParam("festival") String festival,
								@RequestParam("ticketType") String sort_str,
								@RequestParam("id") String id_str,
								HttpServletResponse response) throws Exception {
		festival = festival.substring(0, festival.length()-1);
		Ticket ticket = ticketManagement.checkTicket(festival, sort_str, Long.parseLong(id_str));
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(ticket == null) {
			out.println("<script>alert('Ticket does not exist.'); location.href='/';</script>");
		} else if(ticket.getUsed()) {
			out.println("<script>alert('This ticket has already been used.'); location.href='/';</script>");
		} else {
			ticketManagement.setTicketStatus(ticket);
			out.println("<script>alert('Welcome!'); location.href='/';</script>");
		}
		out.flush();
		
		return "redirect:/";
	}
}