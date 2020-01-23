package festivalmanager.contract;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable

/**
 * The contractList is used to bind Contracts to one festival
 */

public class ContractList {

    @OneToMany
    private List<Contract> list;

    public ContractList(){
        list = new ArrayList<>();
    }

    public List<Contract> getList(){
        return list;
    }

    public void add(Contract contract){
        list.add(contract);
    }

	public void delete(Contract delete) {

        List<Contract> contractList2 = new ArrayList<>();
        for(Contract contract : list){
            if(contract.getId() != delete.getId()){
                contractList2.add(contract);
            }
        }
        list = contractList2;
	}
	public int size(){
    	return list.size();
	}
}
