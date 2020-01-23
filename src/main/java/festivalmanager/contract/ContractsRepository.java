package festivalmanager.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractsRepository extends CrudRepository<Contract, Long> {

    List<Contract> findByName(String name);
    Streamable<Contract> findAll();
}
