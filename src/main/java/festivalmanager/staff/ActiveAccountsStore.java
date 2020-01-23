package festivalmanager.staff;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


public class ActiveAccountsStore {

	public List<String> accounts;
	public ActiveAccountsStore(){ accounts = new ArrayList<String>();
	}



	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
}

