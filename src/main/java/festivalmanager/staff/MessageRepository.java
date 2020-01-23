package festivalmanager.staff;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {


	Streamable<Message> findAllByReceiver(Account account);
	Streamable<Message> findAllBySender(Account account);

	@Override
	Streamable<Message> findAll();


}
