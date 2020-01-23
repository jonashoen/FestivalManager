package festivalmanager.contract;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import festivalmanager.AbstractIntegrationTests;
import festivalmanager.economics.EconomicManager;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalIdForm;
import festivalmanager.festival.FestivalManager;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTest<FestivalRepository> extends AbstractIntegrationTests {
    
    @Autowired EconomicManager economicManager;
    @Autowired ContractController contractController;
    @Autowired ContractsRepository contractRepository;
    @Autowired MockMvc mockMvc;
    @Autowired FestivalManager festivalManager;
    @Mock Errors errors;
    @Mock BindingResult result;

    @Test
    public void shouldRejectUnauthorizedAccess(){
        assertThatExceptionOfType(AuthenticationException.class) 
        .isThrownBy(() -> contractController.showSignUpForm(new Contract()));
    }

    @Test
    @WithMockUser(roles = "FESTIVAL_MANAGER")
    public void shouldWorkWithRoleAdmin(){
        assertEquals(contractController.showSignUpForm(new Contract()), "createContract");
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldGiveBackUpdateForm(){
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");
        ExtendedModelMap model = new ExtendedModelMap();

        contractController.showUpdateForm(festivalIdForm, errors, model);

        assertNotNull(model.get("contract"));
        assertEquals("contractManagement", contractController.showUpdateForm(festivalIdForm, errors, model));
    }

    @Test
    @WithMockUser(roles = "FESTIVAL_MANAGER")
    public void shouldUpdateform(){
        Contract contract = new Contract("contract", "artist", 100, false, 10, 13, 22);
        contractRepository.save(contract);
        ExtendedModelMap model = new ExtendedModelMap();

        contractController.showUpdateForm(contract.getId(), model);
        
        assertNotNull(model.get("contract"));
        assertEquals(contractController.showUpdateForm(contract.getId(), model), "update-Contract");
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldUpdateContract(){
        Contract contract = new Contract("contract", "artist", 100, false, 10, 13, 22);
        contractRepository.save(contract);
        ExtendedModelMap model = new ExtendedModelMap();

        contractController.updateContract(contract.getId(), contract, result, model);
        
        assertNotNull(model.get("contract"));
        assertEquals(contractController.updateContract(contract.getId(), contract, result, model), "redirect:/#festivals");
    } 
    
    @Test
    public void shouldGiveBackAllContracts(){
        assertNotNull(contractController.getContracts());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldDeleteContract(){
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");

        Contract contract1 = new Contract("contract1", "artist", 100, false, 10, 13, 22);
        Contract contract2 = new Contract("contract2", "artist", 100, false, 10, 13, 22);
        contractRepository.save(contract1);
        contractRepository.save(contract2);
        ExtendedModelMap model1 = new ExtendedModelMap();
        ExtendedModelMap model2 = new ExtendedModelMap();

        contractController.showUpdateForm(festivalIdForm, errors, model1);
        contractController.deleteContract(contract2.getId(), model2);

        assertNotNull(model2.get("contract"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldAddContract(){
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");
        
        Contract contract2 = new Contract("contract2", "artist", 100, false, 10, 13, 22);
        contractRepository.save(contract2);
        ExtendedModelMap model1 = new ExtendedModelMap();
        ExtendedModelMap model2 = new ExtendedModelMap();

        contractController.showUpdateForm(festivalIdForm, errors, model1);

        assertEquals(contractController.addContract(contract2, result, model2), "redirect:/#festivals");
    }
}
