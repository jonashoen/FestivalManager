package festivalmanager.location;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends CrudRepository<Area, Long> {

    /**
	 *
	 * @return all areas
	 */
    List<Area> findAll();
}