package festivalmanager.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;

import festivalmanager.AbstractIntegrationTests;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalManager;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest<FestivalRepository> extends AbstractIntegrationTests {

    @Autowired
    LocationManager locationManager;
    @Autowired
    FestivalManager festivalManager;
    @Autowired
    LocationController locationController;
    @Autowired
    LocationRepository locationRepository;
    @Mock
    HttpServletResponse response;

    @Test
    public void shouldHaveFestivalManager() {
        assertNotNull(LocationController.getFestivalManager());
    }

    @Test
    public void shouldOpenLocationManagement() {
        ExtendedModelMap model = new ExtendedModelMap();

        locationController.locationManagement(model);
        assertNotNull(model.get("locationList"));
    }

    @Test
    public void shouldCreateLocation() {
        Location location = new Location("location", "Portugal", 1000, 33, "img1", "img2");
        assertEquals(locationController.addLocation(location), "redirect:location");
    }

    @Test
    public void shouldCreateLocationDefinitely() {
        Location location = new Location("location", "Portugal", 1000, 33, "img1", "img2");
        ExtendedModelMap model = new ExtendedModelMap();

        locationController.addLocation(model, location);

        assertNotNull(model.get("location"));
    }

    @Test
    public void shouldEditLocationPost() {
        LocationForm locationForm = new LocationForm("test", "Straße 12", 100, 3, "thumbnail", "groundPlan");
        Location location = new Location("location", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        Long id = location.getId();

        assertEquals(locationController.editLocation(locationForm, id.toString()), "redirect:/location");
    }

    @Test
    public void shouldEditLocationGet() {
        Location location = new Location("Dresden", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        Festival festival = new Festival("test", location.getName(), "2030-01-01", "2030-01-01", 100, 100, 50, 100,
                1000, true);
        festivalManager.save(festival);
        Area area1 = new Area("zone", 12, 3, Type.PARK);
        area1.setLocationId(location.getId());
        Area area2 = new Area("zone", 12, 3, Type.PARK);
        area2.setLocationId(location.getId() + 1);
        locationManager.save(area1);
        locationManager.save(area2);
        ExtendedModelMap model = new ExtendedModelMap();
        String id = ((Long) location.getId()).toString();

        locationController.editLocation(model, id);

        assertNotNull(model.get("location"));
    }

    @Test
    public void shouldDeleteLocationWithoutFestivalAndEntityGet() {
        Location location = new Location("Dresden", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        String id = ((Long) location.getId()).toString();

        try {
            locationController.deleteLocation(id, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteLocationWithFestivalGet() {
        Location location = new Location("Dresden", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        String id = ((Long) location.getId()).toString();
        Festival festival = new Festival("test", location.getName(), "2030-01-01", "2030-01-01", 100, 100, 50, 100,
                1000, true);
        festivalManager.save(festival);

        try {
            locationController.deleteLocation(id, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteLocationWithEntityGet() {
        Location location = new Location("Dresden", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        String id = ((Long) location.getId()).toString();
        Area area1 = new Area("zone", 12, 3, Type.PARK);
        area1.setLocationId(location.getId());
        Area area2 = new Area("zone", 12, 3, Type.PARK);
        area2.setLocationId(location.getId() + 1);
        locationManager.save(area1);
        locationManager.save(area2);

        try {
            locationController.deleteLocation(id, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetDetailLocation(){
        Location location = new Location("Dresden", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        ExtendedModelMap model = new ExtendedModelMap();

        locationController.detailLocation(model, location.getName());

        assertNotNull(model.get("location"));
        assertNotNull(model.get("contractList"));
        assertNotNull(model.get("festivalList"));
    }

    @Test
    public void shouldManageAreasGet(){
        Location location = new Location("Dresden", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        Area area1 = new Area("zone", 12, 3, Type.PARK);
        area1.setLocationId(location.getId());
        Area area2 = new Area("zone", 12, 3, Type.PARK);
        area2.setLocationId(location.getId() + 1);
        locationManager.save(area1);
        locationManager.save(area2);
        ExtendedModelMap model = new ExtendedModelMap();

        locationController.areaManagement(model, location.getName());

        assertNotNull(model.get("location"));
        assertNotNull(model.get("areaList"));
    }

    @Test
    public void shouldToggleStatus(){
        Location location = new Location("test", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        Area area11 = new Area("zone", 12, 3, Type.PARK);
        area11.setLocationId(location.getId());
        locationManager.save(area11);

        Boolean state = area11.getStatus();
        locationController.changeAreaStatus(location.getName(), area11.getZone());
        assertEquals(state, !area11.getStatus());
    }

    @Test
    public void shouldAddArea(){
        Location location = new Location("test", "Portugal", 1000, 33, "img1", "img2");
        locationManager.save(location);
        Area area = new Area("zone", 12, 3, Type.PARK);
        locationManager.save(area);
        ExtendedModelMap model = new ExtendedModelMap();

        locationController.addArea(model, area, location.getName());

        assertNotNull(model.get("location"));
        assertNotNull(model.get("area"));
    }

    
    @Test
    public void shouldEditAreaPost(){
        Location location = new Location("test1", "Portugal", 1000, 33, "img1", "img2");
        locationController.addLocation(location);
        Area area = new Area("zone", 12, 3, Type.PARK);
        locationController.addArea(area, location.getName());
        AreaForm areaForm = new AreaForm("zone", 12, 3, Type.STAGE);
        String id = ((Long) area.getId()).toString();

        assertNotNull(locationController.editArea(areaForm, location.getName(), id));
    } 
    
    @Test
    public void shouldEditAreaGet(){
        Location location = new Location("BLA", "Portugal", 1000, 33, "img1", "img2");
        locationController.addLocation(location);
        Area area = new Area("zone", 12, 3, Type.PARK);
        locationController.addArea(area, location.getName());
        Stage stage = new Stage("Bühne", "img");
        locationController.addStage(stage, location.getName(), area.getZone());
        String id = ((Long) area.getId()).toString();
        ExtendedModelMap model = new ExtendedModelMap();

        locationController.editArea(model, location.getName(), id);

        assertNotNull(model.get("location"));
        assertNotNull(model.get("area"));
    } 
    
    @Test
    public void shouldGetStageManagement(){
        ExtendedModelMap model = new ExtendedModelMap();
        Location location = new Location("1111", "1111", 1000, 33, "img1", "img2");
        locationController.addLocation(location);
        Area area = new Area("1222", 12, 3, Type.PARK);
        locationController.addArea(area, location.getName());

        locationController.stageManagement(model, location.getName(), area.getZone());
        
        assertNotNull(model.get("location"));
        assertNotNull(model.get("area"));
        assertNotNull(model.get("stageList"));
    }
}