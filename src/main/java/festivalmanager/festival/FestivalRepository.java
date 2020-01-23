package festivalmanager.festival;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

@Repository
interface FestivalRepository extends CrudRepository<Festival, Long> {

	/**
	 *
	 * @param name: name of the festival
	 * @return Festival object with the given name or null
	 */
	Festival findByName (String name);
}
