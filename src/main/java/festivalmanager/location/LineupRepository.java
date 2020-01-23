package festivalmanager.location;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineupRepository extends CrudRepository<Lineup, Long> {

    /**
	 *
	 * @return all lineups
	 */
    List<Lineup> findAll();
}
