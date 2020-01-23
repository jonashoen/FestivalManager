package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StageFormTest {

	private StageForm stage = new StageForm("Bühne", "img");

	@Test
	public void checkAllTheGetters(){
		assertEquals("Bühne", stage.getName());
		assertEquals("img", stage.getPoster());
	}

	@Test
	public void checkAllTheSetters(){
		stage.setAreaId(121212);
		stage.setName("NAME");
		stage.setPoster("p o s t e r");

		assertEquals(121212, stage.getAreaId());
		assertEquals("NAME", stage.getName());
		assertEquals("p o s t e r", stage.getPoster());
		assertEquals(13, stage.setId(13));
	}

	@Test
	public void convertStageFormToStage(){
		assertNotNull(stage.toStage());
	}
}