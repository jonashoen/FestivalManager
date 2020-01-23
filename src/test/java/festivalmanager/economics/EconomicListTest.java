package festivalmanager.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.accountancy.AccountancyEntry;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EconomicListTest{

    private EconomicList economicList= new EconomicList();

    @Test
    public void shouldCreateEconomicList(){
        assertEquals(economicList.getList(), new EconomicList().getList());
    }

    @Test
    public void shouldAddEntrysToEconomicList(){
        AccountancyEntry entry = new AccountancyEntry(Money.of(12, "EUR"), "Test");
        economicList.add(entry);
        assertTrue(economicList.getList().contains(entry));
    }
}
