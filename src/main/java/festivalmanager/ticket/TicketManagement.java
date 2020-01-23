package festivalmanager.ticket;

import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import festivalmanager.economics.EconomicManager;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalIdForm;
import festivalmanager.festival.FestivalManager;
import festivalmanager.location.LocationManager;

@Service
@Transactional
public class TicketManagement{

    private final TicketRepository ticketRepository;
    private final EconomicManager economicManager;
    private final FestivalManager festivalManager;
    private final LocationManager locationManager;

    public TicketManagement(FestivalManager festivalManager,
    						TicketRepository ticketRepository,
    						EconomicManager economicManager,
    						LocationManager locationManager){
		Assert.notNull(festivalManager, "FestivalManager must not be null!");
		Assert.notNull(ticketRepository, "TicketRepository must not be null!");
		Assert.notNull(economicManager, "EconomicManager must not be null!");

        this.festivalManager = festivalManager;
        this.ticketRepository = ticketRepository;
        this.economicManager = economicManager;
        this.locationManager = locationManager;
    }

    public Iterable<Festival> findAll() {
        return festivalManager.findAll();
    }

    public Festival findById(Long id) {
        return festivalManager.findById(id).isPresent() ? festivalManager.findById(id).get() : null;
    }

    /**
     * checks wheather there are day tickets available for sale for this festival
     * @param sort checks if there are tickets of the relevant sort
     * @param festival is the festival for which tickets should be sold
     * @return true when tickets of this sort are still available, false when not
     */

      public boolean dayTicketIsAvailable(Sort sort, Festival festival){
        if(festival.isSellingTickets() != false && sort == Sort.DAYTICKET &&
        festival.getTicketBuilder().getAmountDaytickets().isGreaterThan(Quantity.NONE)){
            return true;
        }
        return false;
    }

    /**
     * checks wheather there are camping tickets available for sale for this festival
     * @param sort checks if there are tickets of the relevant sort
     * @param festival is the festival for which tickets should be sold
     * @return true when tickets of this sort are still available, false when not
     */

    public boolean campingTicketIsAvailable(Sort sort, Festival festival){
        if(festival.isSellingTickets() != false && sort == Sort.CAMPINGTICKET &&
        festival.getTicketBuilder().getAmountCampingtickets().isGreaterThan(Quantity.NONE)){
            return true;
        }
        return false;

    }

    /**
     * orders a ticket after checking availability
     * @param festivalIdForm contains the id of the relevant festival and the sort to create the right kind of ticket
     * @return the created ticket
     */

    public Ticket buyTicket(FestivalIdForm festivalIdForm){
        Festival festival = findById(festivalIdForm.getId());
        if(dayTicketIsAvailable(festivalIdForm.getSort(), festival)&&festivalIdForm.getSort()==Sort.DAYTICKET){
            Ticket ticket = buyDayticket(festival);
            return ticket;
        }
        if(campingTicketIsAvailable(festivalIdForm.getSort(),festival)&&festivalIdForm.getSort()==Sort.CAMPINGTICKET){
            Ticket ticket = buyCampingticket(festival);
            return ticket;
        }
        return null;
    }

    /**
     * creates the ticket, saves it to the repository, recalculates the availability and adds an entry to the accountancy
     * @param festival is the festival that the ticket will be sold for
     * @return the created ticket
     */

    public Dayticket buyDayticket(Festival festival){
		Quantity newQuantity = festival.getTicketBuilder().getAmountDaytickets().subtract(Quantity.of(1));

        economicManager.add(festival.getTicketBuilder().getPriceDayticket(), "Day Ticket", festival);

        festival.getTicketBuilder().setAmountDaytickets(newQuantity);
        Dayticket ticket = festival.getTicketBuilder().createDayticket(festival);
        ticketRepository.save(ticket);
        return ticket;
    }

    /**
     * creates the ticket, saves it to the repository, recalculates the availability and adds an entry to the accountancy
     * @param festival is the festival that the ticket will be sold for
     * @return the created ticket
     */

    public Campingticket buyCampingticket(Festival festival){
        Quantity newQuantity = festival.getTicketBuilder().getAmountCampingtickets().subtract(Quantity.of(1));

        economicManager.add(festival.getTicketBuilder().getPriceCampingticket(), "Camping Ticket", festival);

        festival.getTicketBuilder().setAmountCampingtickets(newQuantity);
        Campingticket ticket = festival.getTicketBuilder().createCampingticket(festival);
        ticketRepository.save(ticket);
        return ticket;
    }

    /**
     * checks wheather a ticket is already used or not
     * @param festival name of the festival the ticket is meant for
     * @param sort_str "CAMPINGTICKET" or "DAYTICKET"
     * @param id is the unique id of the ticket
     * @return the found ticket
     */

    public Ticket checkTicket(String festival, String sort_str, Long id){
        Ticket ticket = ticketRepository.findById(id).isPresent() ? ticketRepository.findById(id).get() : null;

        if(ticket!=null&&(!(ticket.getFestival().getName().equals(festival))||!(ticket.getSort().toString().equals(sort_str)))){
            ticket = null;
        }
        
        return ticket;
    }

    /**
     * marks a ticket as "used" so it cannot used twice
     * @param ticket is the ticket which status needs to be changes
     * @return the ticket
     */

    public Ticket setTicketStatus(Ticket ticket){
    	locationManager.findByName(ticket.getFestival().getLocation()).countVisitors(1);
        ticket.setUsed(true);
        ticketRepository.save(ticket);
        
        return ticket;
    }
}
