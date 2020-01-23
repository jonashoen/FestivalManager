package festivalmanager.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import festivalmanager.festival.Festival;

@SpringBootTest
public class EconomicManagerTest{
    @Autowired Accountancy accountancy;
    @Autowired EconomicManager economicManager;

    Festival festival1 = new Festival( "test", "Dresden", "1980-01-01", "1980-01-01", 100, 100, 50.0f, 100.0f, 1000, true);
    Festival festival2 = new Festival( "test", "Dresden", "1980-01-01", "1980-01-01", 100, 100, 50.0f, 100.0f, 1000, true);

    @Test
    public void shouldAddEntrysWithInt(){
        economicManager.add(20, "Zwanzig", festival1);
        assertNotNull(economicManager.getAll(festival1));
    }

    @Test
    public void shouldAddEntrysWithMoney(){
        economicManager.add(Money.of(20, "EUR"), "Zwanzig", festival1);
        assertNotNull(economicManager.getAll(festival1));
    }
    
    @Test
    public void shouldGetRevenues(){
        AccountancyEntry entry1 = new AccountancyEntry(Money.of(100, "EUR"));
        AccountancyEntry entry2 = new AccountancyEntry(Money.of(10, "EUR"));
        AccountancyEntry entry3 = new AccountancyEntry(Money.of(0, "EUR"));
        AccountancyEntry entry4 = new AccountancyEntry(Money.of(-100, "EUR"));
        AccountancyEntry entry5 = new AccountancyEntry(Money.of(-10, "EUR"));

        economicManager.addEntry(entry1, festival1);
        economicManager.addEntry(entry2, festival1);
        economicManager.addEntry(entry3, festival1);
        economicManager.addEntry(entry4, festival1);
        economicManager.addEntry(entry5, festival1);

        assertEquals(economicManager.getRevenues(festival1), Money.of(110, "EUR"));
    }

    @Test
    public void shouldGetExpenses(){
        AccountancyEntry entry1 = new AccountancyEntry(Money.of(100, "EUR"));
        AccountancyEntry entry2 = new AccountancyEntry(Money.of(10, "EUR"));
        AccountancyEntry entry3 = new AccountancyEntry(Money.of(0, "EUR"));
        AccountancyEntry entry4 = new AccountancyEntry(Money.of(-100, "EUR"));
        AccountancyEntry entry5 = new AccountancyEntry(Money.of(-10, "EUR"));

        economicManager.addEntry(entry1, festival1);
        economicManager.addEntry(entry2, festival1);
        economicManager.addEntry(entry3, festival1);
        economicManager.addEntry(entry4, festival1);
        economicManager.addEntry(entry5, festival1);

        assertEquals(economicManager.getExpenses(festival1), Money.of(-110, "EUR"));
    }

    @Test
    public void shouldGetSum(){
        AccountancyEntry entry1 = new AccountancyEntry(Money.of(100, "EUR"));
        AccountancyEntry entry2 = new AccountancyEntry(Money.of(10, "EUR"));
        AccountancyEntry entry3 = new AccountancyEntry(Money.of(0, "EUR"));
        AccountancyEntry entry4 = new AccountancyEntry(Money.of(-100, "EUR"));
        AccountancyEntry entry5 = new AccountancyEntry(Money.of(-10, "EUR"));

        economicManager.addEntry(entry1, festival1);
        economicManager.addEntry(entry2, festival1);
        economicManager.addEntry(entry3, festival1);
        economicManager.addEntry(entry4, festival1);
        economicManager.addEntry(entry5, festival1);

        assertEquals(economicManager.getSum(festival1), Money.of(0, "EUR"));
    }

    @Test
    public void shouldCalculateOverallSum(){
        AccountancyEntry entry1 = new AccountancyEntry(Money.of(100, "EUR"));
        AccountancyEntry entry2 = new AccountancyEntry(Money.of(10, "EUR"));
        AccountancyEntry entry3 = new AccountancyEntry(Money.of(0, "EUR"));
        AccountancyEntry entry4 = new AccountancyEntry(Money.of(-100, "EUR"));
        AccountancyEntry entry5 = new AccountancyEntry(Money.of(-10, "EUR"));

        economicManager.addEntry(entry1, festival1);
        economicManager.addEntry(entry2, festival1);
        economicManager.addEntry(entry3, festival2);
        economicManager.addEntry(entry4, festival2);
        economicManager.addEntry(entry5, festival2);

        assertNotNull(economicManager.getOverallSum());
    }
} 