package festivalmanager.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContractTest{

    private Contract contract = new Contract("contract", "artist", 100, false, 10, 13, 22);

    @Test
    public void checkAllTheGetters(){
        assertEquals(contract.getName(), "contract");
        assertEquals(contract.getArtist(), "artist");
        assertEquals(contract.getPrice(), 100);
        assertEquals(contract.getAccepted(), false);
        assertEquals(contract.getTechnicianscount(), 10);
        assertEquals(contract.getWorkerswage(), 22);
        assertEquals(contract.getWorkinghours(), 13);        
    }

    @Test
    public void checkAllTheSetters(){
        contract.setId(1222);
        contract.setName("Test");
        contract.setArtist("ARTIST");
        contract.setPrice(1000);
        contract.setAccepted(true);
        contract.setTechnicianscount(20);
        contract.setWorkerswage(50);
        contract.setWorkinghours(11);

        assertEquals(contract.getId(), 1222);
        assertEquals(contract.getName(), "Test");
        assertEquals(contract.getArtist(), "ARTIST");
        assertEquals(contract.getPrice(), 1000);
        assertEquals(contract.getAccepted(), true);
        assertEquals(contract.getTechnicianscount(), 20);
        assertEquals(contract.getWorkerswage(), 50);
        assertEquals(contract.getWorkinghours(), 11);        
    }

    @Test
    public void shouldCalculateOverallCost(){
        assertEquals(contract.totalCost(), contract.getPrice() + contract.getWorkinghours() * contract.getWorkerswage() * contract.getTechnicianscount());
    }

    @Test
    public void shouldGiveBackString(){
        assertEquals(contract.getArtist(), contract.toString());
    }
}