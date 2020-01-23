package festivalmanager.economics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import org.salespointframework.accountancy.AccountancyEntry;

	/**
	 * The EconomicList is used to make AccountancyEntrys (Salespoint) specific to festivals
     * Each festival has one EconomicList which contains all the economic data related to this festival
	 */

@Embeddable
public class EconomicList {

    @OneToMany
    private List<AccountancyEntry> accountancyEntryList;

    public EconomicList(){
        accountancyEntryList = new ArrayList<>();
    }

    public List<AccountancyEntry> getList(){
        return accountancyEntryList;
    }

    public void add(AccountancyEntry accountancyEntry){
        accountancyEntryList.add(accountancyEntry);
    }
}