package festivalmanager.staff;

import org.h2.engine.User;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
interface AccountRepository extends CrudRepository<Account, Long> {

	@Override
	Streamable<Account> findAll();


	Optional<Account> findByUserAccount(UserAccount userAccount);

}