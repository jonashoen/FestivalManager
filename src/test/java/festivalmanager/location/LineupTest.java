package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import festivalmanager.contract.Contract;
import festivalmanager.festival.Festival;

@SpringBootTest
public class LineupTest{	
    @Test
    public void checkAllTheGetters() {
        Lineup lineup = new Lineup(LocalDateTime.of(2015, 4, 17, 23, 47, 5));

        assertEquals(LocalDateTime.of(2015, 4, 17, 23, 47, 5), lineup.getDate());
    }

    @Test
    public void checkAllTheSetters(){
        Lineup lineup = new Lineup(LocalDateTime.of(2015, 4, 17, 23, 47, 5));
        Festival festival = new Festival( "test", "Dresden", "2030-01-01", "2030-01-01", 100, 100, 50, 100, 1000, true);

        lineup.setDate(LocalDateTime.of(2020, 12, 17, 23, 47, 5));
        lineup.setStageId(1234567890);
        lineup.setId(1111);
        lineup.festival = festival;

        assertEquals(LocalDateTime.of(2020, 12, 17, 23, 47, 5), lineup.getDate());
        assertEquals(1234567890, lineup.getStageId());
        assertEquals(1111, lineup.getId());
        assertEquals(festival, lineup.getFestival());
    }

    @Test
    public void shouldDealWithContracts(){
        Lineup lineup = new Lineup(LocalDateTime.of(2020, 12, 17, 23, 47, 5));
        Contract contract = new Contract("contract", "artist", 100, false, 10, 13, 22);

        lineup.setArtist(contract);

        assertEquals(contract, lineup.getArtist());
    }
}