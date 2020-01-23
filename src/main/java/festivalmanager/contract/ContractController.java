package festivalmanager.contract;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import festivalmanager.contract.Contract;
import festivalmanager.contract.ContractsRepository;
import festivalmanager.economics.EconomicManager;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalIdForm;
import festivalmanager.festival.FestivalManager;

import java.util.Optional;

@Controller
@RequestMapping("/contracts/")
public class ContractController {

	private final ContractsRepository contractsRepository;
    public final EconomicManager economicManager;
	public final FestivalManager festivalManager;
	private Festival festivalForCreation;

	@Autowired
	public ContractController(ContractsRepository contractsRepository, 
	                          EconomicManager economicManager, FestivalManager festivalManager) {
		this.contractsRepository = contractsRepository;
		this.economicManager = economicManager;
		this.festivalManager = festivalManager;
	}

	/**
	 * Shows form to create a new contract
	 * @param contract 
	 * @return leads to the form where a new contract can be created
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('FESTIVAL_MANAGER') or hasRole('FESTIVAL_MANAGER')")
	@GetMapping("create")
	public String showSignUpForm(Contract contract) {
		return "createContract";
	}

	/**
	 * after klicking on a festival, this festival is set as "festivalForCreation" and an overview over the contract related to this festival is shown
	 * @param festivalIdForm containing the id of the festival
	 * @param result containing errors possibly
	 * @param model the model to which the contracts are added to be listed on the next page
	 * @return leads to the contract overview for one festival
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('FESTIVAL_MANAGER') or hasRole('FESTIVAL_MANAGER')")
	@GetMapping("list")
	public String showUpdateForm(@Valid @ModelAttribute("form") FestivalIdForm festivalIdForm, Errors result, Model model){
		festivalForCreation = festivalManager.findById(festivalIdForm.getId()).get();
		if(result.hasErrors()){
			return "festivals";
		}

		model.addAttribute("contract", festivalForCreation.getContractList().getList());

		return "contractManagement";
	}

	/**
	 * creates new contract
	 * @param contract the newly added contract
	 * @param result containing possible errors
	 * @param model 
	 * @return to the festival overview
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('FESTIVAL_MANAGER') or hasRole('FESTIVAL_MANAGER')")
	@PostMapping("add")
	public String addContract(@Valid @ModelAttribute Contract contract, BindingResult result, Model model) {
		Optional<Festival> festivalOptional = festivalManager.findById(festivalForCreation.getId());

		if (result.hasErrors() || festivalOptional.isEmpty()) {
			return "welcome";
		}

		Festival festival = festivalOptional.get();

		contractsRepository.save(contract);

		festival.getContractList().add(contract);
		economicManager.add(contract.totalCost()*(-1), contract.getName(), festival);

		festivalManager.save(festival);

		return "redirect:/#festivals";
	}

	/**
	 * updates a contract when it was edited
	 * @param id of contract
	 * @param model to show the newly edited contract
	 * @return back to the edited contract
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('FESTIVAL_MANAGER') or hasRole('FESTIVAL_MANAGER')")
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Contract contract = contractsRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid Contract Id:" + id));
		model.addAttribute("contract", contract);
		return "update-Contract";
	}

	/**
	 * opens up the contract form and fill in the current values, so those can be edited
	 * @param id of contract
	 * @param contract containing the current information
	 * @param result containing possible errors
	 * @param model to show the current contract
	 * @return leads to the edit-page of the contract filled with its current information
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('FESTIVAL_MANAGER') or hasRole('FESTIVAL_MANAGER')")
	@PostMapping("update/{id}")
	public String updateContract(@PathVariable("id") long id, @Valid Contract contract, 
	                              BindingResult result, Model model) {
		if (result.hasErrors()) {
			contract.setId(id);
			return "update-Contract";
		}

		contractsRepository.save(contract);
		model.addAttribute("contract", contractsRepository.findAll());
		return "redirect:/#festivals";
	}

	/**
	 * deletes a contract
	 * @param id of contract
	 * @param model to add all the remaining contracts of the festival
	 * @return list of remaining contracts
	 */

	@PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') or hasAuthority('FESTIVAL_MANAGER') or hasRole('FESTIVAL_MANAGER')")
	@GetMapping("delete/{id}")
	public String deleteContract(@PathVariable("id") long id, Model model) {
		Contract contract = contractsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));

		festivalForCreation.getContractList().delete(contract);
		festivalManager.save(festivalForCreation);
		contractsRepository.delete(contract);

		model.addAttribute("contract", contractsRepository.findAll());
		return "contractManagement";
	}

	public ContractsRepository getContracts(){
		return this.contractsRepository;
	}
}
