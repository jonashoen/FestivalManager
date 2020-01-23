package festivalmanager.economics;

import static org.assertj.core.api.Assertions.*;
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
import org.springframework.validation.Errors;

import festivalmanager.AbstractIntegrationTests;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalIdForm;
import festivalmanager.festival.FestivalManager;

@SpringBootTest
@AutoConfigureMockMvc
public class EconomicControllerTest<FestivalRepository> extends AbstractIntegrationTests {
    
    @Autowired EconomicController economicController;
    @Autowired EconomicManager economicManager;
    @Autowired MockMvc mockMvc;
    @Autowired FestivalManager festivalManager;
    @Mock Errors errors;

    @Test
    public void shouldRejectUnauthorizedAccess(){
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");
        
        assertThatExceptionOfType(AuthenticationException.class) 
        .isThrownBy(() -> economicController.goToAccountancy(festivalIdForm, errors, new ExtendedModelMap()));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldWorkWithRoleAdmin(){
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");
        
        ExtendedModelMap modelMap = new ExtendedModelMap();
        economicController.goToAccountancy(festivalIdForm, errors, modelMap);

        assertNotNull(modelMap.get("entrylist"));
        assertNotNull(modelMap.get("sumRevenues"));
        assertNotNull(modelMap.get("sumExpenses"));
        assertNotNull(modelMap.get("sumAll"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void shouldGiveBackTotalAccountancy(){
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);
        festivalManager.save(festival);
        FestivalIdForm festivalIdForm = new FestivalIdForm(festival.getId(), "CAMPINGTICKET");
        
        ExtendedModelMap modelMap = new ExtendedModelMap();
        economicController.festivals(modelMap, festivalIdForm);

        assertNotNull(modelMap.get("festivals"));
        assertNotNull(modelMap.get("economicManager"));
    }
}