package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import festivalmanager.contract.Contract;
import festivalmanager.festival.Festival;

@SpringBootTest
public class LineupFormTest{	
    @Test
    public void checkAllTheGetters() {
        LineupForm lineupForm = new LineupForm(LocalDateTime.of(2015, 4, 17, 23, 47, 5));

        assertEquals(LocalDateTime.of(2015, 4, 17, 23, 47, 5), lineupForm.getDate());
    }

    @Test
    public void checkAllTheSetters(){
        LineupForm lineupForm = new LineupForm(LocalDateTime.of(2015, 4, 17, 23, 47, 5));
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);

        lineupForm.setDate(LocalDateTime.of(2020, 12, 17, 23, 47, 5));
        lineupForm.setStageId(1234567890);
        lineupForm.setId(1111);
        lineupForm.festival = festival;

        assertEquals(LocalDateTime.of(2020, 12, 17, 23, 47, 5), lineupForm.getDate());
        assertEquals(1234567890, lineupForm.getStageId());
        assertEquals(1111, lineupForm.getId());
        assertEquals(festival, lineupForm.getFestival());
    }

    @Test
    public void shouldDealWithContracts(){
        LineupForm lineupForm = new LineupForm(LocalDateTime.of(2020, 12, 17, 23, 47, 5));
        Contract contract = new Contract("contract", "artist", 100, false, 10, 13, 22);

        lineupForm.setArtist(contract);

        assertEquals(contract, lineupForm.getArtist());
    }

    @Test
    public void convertLineupFormToLineup(){
        LineupForm lineupForm = new LineupForm(LocalDateTime.of(2020, 12, 17, 23, 47, 5));

        assertNotNull(lineupForm.toLineup());
    }
}