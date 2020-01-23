package festivalmanager.ticket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Override
    Streamable<Ticket> findAll();
}
