package festivalmanager.economics;

import javax.money.MonetaryAmount;

import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalManager;

@Service
@Transactional
public class EconomicManager{

    private final Accountancy accountency;
    private final FestivalManager festivalManager;

    /**
	 *The EconomicManager manages everything concerning money connected to a festival and gives you an overview per festival
	 */

    public EconomicManager(Accountancy accountency, @Lazy FestivalManager festivalManager) {
        this.accountency = accountency;
        this.festivalManager = festivalManager;
    }

    /**
     * creates an entry to the economic overview of a festival
	 * @param amount is the amount of money. Revenues have positive values, expenses negative ones
     * @param description describes the entry so that you assign the entrys to events later
     * @param festival is the festival the entry belongs to
	 */

	public void add(int amount, String description, Festival festival){
        MonetaryAmount value = Money.of(amount, "EUR");
        AccountancyEntry entry = new AccountancyEntry(value, description);
        addEntry(entry, festival);
    }

    /**
     * creates an entry to the economic overview of a festival
	 * @param value is the amount of money. Revenues have positive values, expenses negative ones
     * @param description describes the entry so that you assign the entrys to events later
     * @param festival is the festival the entry belongs to
	 */

    public void add(Money value, String description, Festival festival){
        AccountancyEntry entry = new AccountancyEntry(value, description);
        addEntry(entry, festival);
    }

    /**
     * adds an entry to the economic overview of a festival
     * @param entry is the entry representing the expense or revenue
     * @param festival is the festival the entry belongs to
	 */

    public void addEntry(AccountancyEntry entry, Festival festival){
        accountency.add(entry);
        festival.getEconomicList().add(entry);
    }
    
    /**
     * gives back all the entrys assigned to the festival
	 */

    public List<AccountancyEntry> getAll(Festival festival){
        return festival.getEconomicList();
    }
    
    /**
     * calculates the sum of all the revenues assigned to one festival
	 */

    public MonetaryAmount getRevenues(Festival festival){
        List<AccountancyEntry> entrys = festival.getEconomicList();
        MonetaryAmount sum = Money.of(0, "EUR");
        for (AccountancyEntry entry : entrys){
            if(entry.isRevenue()){
                sum = sum.add(entry.getValue());
            }
        }
        return sum;
    }
    
    /**
     * calculates the sum of all the expenses assigned to one festival
	 */

    public MonetaryAmount getExpenses(Festival festival){
        List<AccountancyEntry> entrys = festival.getEconomicList();
        MonetaryAmount sum = Money.of(0, "EUR");
        for (AccountancyEntry entry : entrys){
            if(entry.isExpense()){
                sum = sum.add(entry.getValue());
            }
        }
        return sum;
    }
    
    /**
     * calculates the sum of all the revenues AND expenses assigned to one festival
	 */

    public MonetaryAmount getSum(Festival festival){
        MonetaryAmount sum = Money.of(0, "EUR");
        sum = sum.add(getRevenues(festival));
        sum = sum.add(getExpenses(festival));
        return sum;
    }
    
    /**
     * calculates the sum of all the revenues AND expenses of all festivals 
	 */

    public MonetaryAmount getOverallSum(){
        MonetaryAmount amount = Money.of(0, "EUR");
        for(Festival festival : festivalManager.findAll()){
            amount = amount.add(getSum(festival));
        }
    return amount;
    }
}