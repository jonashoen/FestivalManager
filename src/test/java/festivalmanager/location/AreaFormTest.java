package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AreaFormTest {

	AreaForm areaForm = new AreaForm("zone", 12, 3, Type.PARK);

	@Test
	public void checkAllTheGetters(){
		assertEquals("zone", areaForm.getZone());
		assertEquals(12, areaForm.getMaxVisitors());
		assertEquals(3, areaForm.getMaxStages());
		assertEquals(Type.PARK, areaForm.getType());
		assertEquals(false, areaForm.getStatus());
		assertEquals(0, areaForm.getCurrVisitors());
	}

	@Test
	public void checkAllTheSetters(){
		areaForm.setLocationId(1111);
		areaForm.setZone("enoZ");
		areaForm.toggleLock();
		areaForm.countVisitors(123);
		areaForm.setMaxVisitors(5);
		areaForm.setMaxStages(1);
		areaForm.setType(Type.CATERING);

		assertEquals(1111, areaForm.getLocationId());
		assertEquals(123, areaForm.getCurrVisitors());
		assertEquals("enoZ", areaForm.getZone());
		assertEquals(5, areaForm.getMaxVisitors());
		assertEquals(1, areaForm.getMaxStages());
		assertEquals(Type.CATERING, areaForm.getType());
		assertEquals(true, areaForm.getStatus());
		assertEquals(12, areaForm.setId(12));
		assertEquals(12, areaForm.setCurrVisitors(12));
	}

	@Test
	public void shouldToggleLocked(){
		boolean locked = areaForm.toggleLock();
		areaForm.toggleLock();
		assertEquals(locked, areaForm.toggleLock());
	}

	@Test
	public void shouldReturnNullWhenVisitorsAreLessThanZero(){
		assertEquals(areaForm.countVisitors(-4), null);
	}

	@Test
	public void shouldGiveBackZone(){
		assertEquals(areaForm.getZone(), areaForm.toString());
    }
    
    @Test
    public void shouldConvertAreFormToArea(){
        assertNotNull(areaForm.toArea());
    }
}
