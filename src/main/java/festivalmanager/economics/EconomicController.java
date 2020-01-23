package festivalmanager.economics;

import java.util.Optional;

import javax.validation.Valid;

import festivalmanager.festival.FestivalManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalIdForm;

@Controller
public class EconomicController {

    public final EconomicManager economicManager;
    private final FestivalManager festivalManager;

    public EconomicController(EconomicManager economicManager, FestivalManager festivalManager) {
        Assert.notNull(economicManager, "TicketManagement must not be null!");
        this.economicManager = economicManager;
        this.festivalManager = festivalManager;
    }

    // GetMapping

    /**
     * lists all the accountancy entrys for one festival
     * @param festivalIdForm contains the id of the festival that it is about
     */

    @PreAuthorize("hasAuthority('MANAGER') or hasRole('MANAGER') ")
    @GetMapping(path = "/accountancy")
    public String goToAccountancy(@Valid @ModelAttribute("form") FestivalIdForm festivalIdForm, Errors result,
            Model model) {
        Optional<Festival> festivalOptinal = festivalManager.findById(festivalIdForm.getId());

		if(result.hasErrors() || festivalOptinal.isEmpty()){
			return "festivals";
		}

        Festival festival = festivalOptinal.get();

        model.addAttribute("festivalname", festival.getName());
		model.addAttribute("entrylist", economicManager.getAll(festival));
		model.addAttribute("festivals", festivalManager.findAllSortedByDate());
        model.addAttribute("sumRevenues", economicManager.getRevenues(festival));
        model.addAttribute("sumExpenses", economicManager.getExpenses(festival));
        model.addAttribute("sumAll", economicManager.getSum(festival));
		return "accountancy";
    }

    /**
     * gives an overview over all festivals and their overallSum
     */

	@GetMapping("/totalAccountancy")
	String festivals(Model model , FestivalIdForm festivalIdForm) {

		model.addAttribute("festivals", festivalManager.findAll());
		model.addAttribute("economicManager", economicManager);

		return "totalAccountancy";
	}

}
