package festivalmanager.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContractListTest{

    @Autowired ContractsRepository contractsRepository;
    private ContractList contractList= new ContractList();

    private Contract contract = new Contract("contract", "artist", 100, false, 10, 13, 22);
    private Contract contract2 = new Contract("contract2", "artist2", 100, false, 10, 13, 22);

    @Test
    public void shouldCreateContractList(){
        assertEquals(contractList.getList(), new ContractList().getList());
    }

    @Test
    public void shouldAddEntrysToContractList(){
        contractList.add(contract);
        assertTrue(contractList.getList().contains(contract));
    }

    @Test
    public void shouldDeleteEntryFromContractList(){
        contractList.add(contract);
        contractsRepository.save(contract);
        contractList.add(contract2);
        contractsRepository.save(contract2);
        contractList.delete(contract);
        assertFalse(contractList.getList().contains(contract));
        assertTrue(contractList.getList().contains(contract2));
    }

    @Test
    public void shouldGetTheRightSizeOfContractList(){
        assertEquals(contractList.size(), contractList.getList().size());
    }
}
