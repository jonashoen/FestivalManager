package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationFormTest {

	private LocationForm locationForm = new LocationForm("locationForm", "Portugal", 1000, 33, "img1", "img2");

	@Test
	public void checkAllTheGetters(){
		assertEquals("locationForm", locationForm.getName());
		assertEquals("Portugal", locationForm.getAddress());
		assertEquals(1000, locationForm.getPrice());
		assertEquals(33, locationForm.getMaxVisitors());
		assertEquals("img1", locationForm.getThumbnail());
		assertEquals("img2", locationForm.getGroundPlan());
		assertEquals(false, locationForm.getStatus());
		assertEquals(0, locationForm.getCurrVisitors());
	}

	@Test
	public void checkAllTheSetters(){
		locationForm.setName("name");
		locationForm.setAddress("Paris");
		locationForm.setPrice(2000);
		locationForm.toggleBook();
		locationForm.countVisitors(110);
		locationForm.setMaxVisitors(112);
		locationForm.setThumbnail("img3");
		locationForm.setGroundPlan("img4");

		assertEquals("name", locationForm.getName());
		assertEquals("Paris", locationForm.getAddress());
		assertEquals(2000, locationForm.getPrice());
		assertEquals(112, locationForm.getMaxVisitors());
		assertEquals("img3", locationForm.getThumbnail());
		assertEquals("img4", locationForm.getGroundPlan());
		assertEquals(true, locationForm.getStatus());
		assertEquals(110, locationForm.getCurrVisitors());
		assertEquals(1111, locationForm.setId(1111));
		assertEquals(true, locationForm.setStatus(true));
		assertEquals(1, locationForm.setCurrVisitors(1));
	}

	@Test
	public void shouldToggleLocked(){
		boolean locked = locationForm.toggleBook();
		locationForm.toggleBook();
		assertEquals(locked, locationForm.toggleBook());
	}

	@Test
	public void convertLocationFormToLocation(){
		assertNotNull(locationForm.toLocation());
	}
}
