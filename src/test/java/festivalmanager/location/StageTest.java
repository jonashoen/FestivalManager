package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StageTest {

	private Stage stage = new Stage("Bühne", "img");

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
	}
}