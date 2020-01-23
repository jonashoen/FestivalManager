package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationTest {

	private Location location = new Location("location", "Portugal", 1000, 33, "img1", "img2");


	@Test
	public void checkAllTheGetters(){
		assertEquals("location", location.getName());
		assertEquals("Portugal", location.getAddress());
		assertEquals(1000, location.getPrice());
		assertEquals(33, location.getMaxVisitors());
		assertEquals("img1", location.getThumbnail());
		assertEquals("img2", location.getGroundPlan());
		assertEquals(false, location.getStatus());
		assertEquals(0, location.getCurrVisitors());
	}

	@Test
	public void checkAllTheSetters(){
		location.setName("name");
		location.setAddress("Paris");
		location.setPrice(2000);
		location.toggleBook();
		location.countVisitors(110);
		location.setMaxVisitors(112);
		location.setThumbnail("img3");
		location.setGroundPlan("img4");

		assertEquals("name", location.getName());
		assertEquals("Paris", location.getAddress());
		assertEquals(2000, location.getPrice());
		assertEquals(112, location.getMaxVisitors());
		assertEquals("img3", location.getThumbnail());
		assertEquals("img4", location.getGroundPlan());
		assertEquals(true, location.getStatus());
		assertEquals(110, location.getCurrVisitors());
		assertEquals(1111, location.setId(1111));
		assertEquals(true, location.setStatus(true));
		assertEquals(1, location.setCurrVisitors(1));
	}

	@Test
	public void shouldToggleLocked(){
		boolean locked = location.toggleBook();
		location.toggleBook();
		assertEquals(locked, location.toggleBook());
	}

}
