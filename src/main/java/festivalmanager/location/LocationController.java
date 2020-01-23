package festivalmanager.location;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.google.common.collect.Lists;

import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import festivalmanager.contract.Contract;
import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalManager;

@Controller
public class LocationController {
	private final LocationManager locationManager;
	private static FestivalManager festivalManager;
	boolean hasEntity, hasFestival = false;
	
	/**
	 * Constructor to create LocationController instances.
	 * @param locationManager
	 * @param festivalManager
	 */
	@SuppressWarnings("static-access")
	public LocationController(LocationManager locationManager, FestivalManager festivalManager){
		Assert.notNull(locationManager, "Location must not be null!");
		this.locationManager = locationManager;
		this.festivalManager = festivalManager;
	}
	
	public static FestivalManager getFestivalManager() {
		return festivalManager;
	}

	//---------------------------------------Location------------------------------------------
	@GetMapping("/location")
	public String locationManagement(Model model) {
		List<Location> locations = locationManager.findAllLocations();

		model.addAttribute("locationList", locations);

		return "location";
	}


	@PostMapping("/createLocation")
	public String addLocation(@Valid Location location) {
		locationManager.save(location);

		return "redirect:location";
	}

	//@PreAuthorize("hasAuthority('MANAGER')")
	@GetMapping("/createLocation")
	public String addLocation(Model model, Location location) {
		model.addAttribute("location", location);

		return "createLocation";
	}

	@PostMapping("/location/{location}/editLocation")
	public String editLocation(	@ModelAttribute("location") @Valid LocationForm locationForm,
								@PathVariable("location") String locationIdAsString) {
		
		locationManager.update(locationForm, Long.parseLong(locationIdAsString));
		
		return "redirect:/location";
	}

	//@PreAuthorize("hasAuthority('MANAGER')")
	@GetMapping("/location/{location}/editLocation")
	public String editLocation(Model model, @PathVariable("location") String locationIdAsString) {
		long locationId = Long.parseLong(locationIdAsString);
		Location location = locationManager.findById(locationId);
		List<Area> areas = locationManager.findAllAreas(location);
		
		for(Area area : areas) {
			if(area.getLocationId() == location.getId()) {
				hasEntity = !hasEntity;
				break;
			}
		}
		for(Festival festival : festivalManager.findAll()) {
			if(festival.getLocation() == location.getName()) {
				hasFestival = !hasFestival;
				break;
			}
		}
		
		model.addAttribute("location", location);

		return "editLocation";
	}
	
	@GetMapping("/location/{location}/editLocation/delete")
	public String deleteLocation(	@PathVariable("location") String locationIdAsString,
									HttpServletResponse response) throws Exception {
		
		Location location = locationManager.findById(Long.parseLong(locationIdAsString));
		String goTo = "/location";
		
		if(!hasFestival && !hasEntity) {
			locationManager.deleteLocationById(location.getId());
		} else if(hasFestival) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			hasFestival = false;
			out.println("<script>alert('There is a Festival planned on this location'); location.href=' " + goTo + " ';</script>");
			out.flush();
		} else if(hasEntity) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			hasEntity = false;
			out.println("<script>alert('There is an Area created on this location'); location.href=' " + goTo + " ';</script>");
			out.flush();
		}
		
		
		return "redirect:/location";
	}

	@GetMapping("/location/{location}")
	public String detailLocation(Model model, @PathVariable("location") String locationName){
		Location location = locationManager.findByName(locationName);
		List<Contract> contracts = locationManager.findByName().toList();
		List<Festival> festivals = (List<Festival>) festivalManager.findAll();

		model.addAttribute("location", location);
		model.addAttribute("contractList", contracts);
		model.addAttribute("festivalList", festivals);

		return "detailLocation";
	}
	//-----------------------------------------------------------------------------------------
	
	//-----------------------------------------Area--------------------------------------------
	@GetMapping("/location/{location}/area")
	public String areaManagement(Model model, @PathVariable("location") String locationName) {
		Location location = locationManager.findByName(locationName);
		List<Area> areas = locationManager.findAllAreas(location);
		
		model.addAttribute("location", location);
		model.addAttribute("areaList", areas);

		return "area";
	}

	//@PreAuthorize("hasAuthority('MANAGER')")
	@GetMapping("/location/{location}/area/changeStatus")
	public String changeAreaStatus(	@PathVariable("location") String locationName,
									@RequestParam("area") String areaName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);

		area.toggleLock();
		locationManager.save(area);

		return "redirect:";
	}

	@PostMapping("/location/{location}/createArea")
	public String addArea(@Valid Area area, @PathVariable("location") String locationName) {
		Location location = locationManager.findByName(locationName);

		area.setLocationId(location.getId());
		locationManager.save(area);

		return "redirect:area";
	}


	@GetMapping("/location/{location}/createArea")
	public String addArea(Model model, Area area, @PathVariable("location") String locationName) {
		Location location = locationManager.findByName(locationName);

		model.addAttribute("location", location);
		model.addAttribute("area", area);

		return "createArea";
	}

	@PostMapping("/location/{location}/area/{area}")
	String editArea(@ModelAttribute("area") @Valid AreaForm areaForm,
					@PathVariable("location") String locationName,
					@PathVariable("area") String areaIdAsString) {
		
		Location location = locationManager.findByName(locationName);
		locationManager.update(location, areaForm, Long.parseLong(areaIdAsString));

		return "redirect:";
	}

	//@PreAuthorize("hasAuthority('MANAGER')")
	@GetMapping("/location/{location}/area/{area}")
	String editArea(Model model,
					@PathVariable("location") String locationName,
					@PathVariable("area") String areaIdAsString) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findById(location, Long.parseLong(areaIdAsString));
		List<Stage> stages = locationManager.findAllStages(area);
		
		for(Stage stage : stages) {
			if(stage.getAreaId() == area.getId()) {
				hasEntity = !hasEntity;
				break;
			}
		}
		
		model.addAttribute("location", location);
		model.addAttribute("area", area);

		return "editArea";
	}
	
	@GetMapping("/location/{location}/area/{area}/delete")
	public String deleteArea(	@PathVariable("location") String locationName,
								@PathVariable("area") String areaIdAsString,
								HttpServletResponse response) throws Exception {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findById(location, Long.parseLong(areaIdAsString));
		
		String goTo = "/location/" + location.getName() + "/area";
		
		if(!hasEntity) {
			locationManager.deleteAreaById(area.getId());
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			hasEntity = false;
			out.println("<script>alert('>There is a Stage on this Area!'); location.href=' " + goTo + " ';</script>");
			out.flush();
		}
		

		return "redirect:/location/" + location.getName() + "/area";
	}
	//-----------------------------------------------------------------------------------------
	
	//-----------------------------------------Stage-------------------------------------------
	@GetMapping("/location/{location}/area/{area}/stage")
	public String stageManagement(	Model model,
									@PathVariable("location") String locationName,
									@PathVariable("area") String areaName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		List<Stage> stages = locationManager.findAllStages(area);

		model.addAttribute("location", location);
		model.addAttribute("area", area);
		model.addAttribute("stageList", stages);

		return "stage";
	}

	@PostMapping("/location/{location}/area/{area}/createStage")
	public String addStage(	@Valid Stage stage,
							@PathVariable("location") String locationName,
							@PathVariable("area") String areaName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);

		stage.setAreaId(area.getId());
		locationManager.save(stage);

		return "redirect:stage";
	}

	@GetMapping("/location/{location}/area/{area}/createStage")
	public String addStage(	Model model, Stage stage,
							@PathVariable("location") String locationName,
							@PathVariable("area") String areaName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);

		model.addAttribute("location", location);
		model.addAttribute("area", area);
		model.addAttribute("stage", stage);

		return "createStage";
	}

	@PostMapping("/location/{location}/area/{area}/stage/{stage}")
	public String editStage(@ModelAttribute("stage") @Valid StageForm stageForm,
							@PathVariable("location") String locationName,
							@PathVariable("area") String areaName,
							@PathVariable("stage") String stageIdAsString) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		locationManager.update(area, stageForm, Long.parseLong(stageIdAsString));
		
		return "redirect:";
	}

	@GetMapping("/location/{location}/area/{area}/stage/{stage}")
	public String editStage(Model model,
							@PathVariable("location") String locationName,
							@PathVariable("area") String areaName,
							@PathVariable("stage") String stageIdAsString) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findById(area, Long.parseLong(stageIdAsString));
		List<Lineup> lineups = locationManager.findAllLineups(stage);
		
		for(Lineup lineup : lineups) {
			if(lineup.getStageId() == stage.getId()) {
				hasEntity = !hasEntity;
				break;
			}
		}
		
		model.addAttribute("location", location);
		model.addAttribute("area", area);
		model.addAttribute("stage", stage);

		return "editStage";
	}
	
	@GetMapping("/location/{location}/area/{area}/stage/{stage}/delete")
	public String deleteStage(	@PathVariable("location") String locationName,
								@PathVariable("area") String areaName,
								@PathVariable("stage") String stageIdAsString,
								HttpServletResponse response) throws Exception {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findById(area, Long.parseLong(stageIdAsString));
		String goTo = "/location/" + location.getName() + "/area/" + area.getZone() + "/stage";
		
		if(!hasEntity) {
			locationManager.deleteStageById(stage.getId());
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			hasEntity = false;
			out.println("<script>alert('There is a Lineup organized on this Stage!'); location.href=' " + goTo + " ';</script>");
			out.flush();
		}

		return "redirect:/location/" + location.getName() + "/area/" + area.getZone() + "/stage";
	}
	//-----------------------------------------------------------------------------------------
	
	//----------------------------------------Lineup-------------------------------------------
	@GetMapping("/location/{location}/area/{area}/stage/{stage}/lineup")
	public String detailLineup(	Model model,
								@PathVariable("location") String locationName,
								@PathVariable("area") String areaName,
								@PathVariable("stage") String stageName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findByName(area, stageName);
		List<Lineup> lineups = locationManager.findAllLineups(stage);

		model.addAttribute("location", location);
		model.addAttribute("area", area);
		model.addAttribute("stage", stage);
		model.addAttribute("lineupList", lineups);
		
		return "lineup";
	}

	@PostMapping("/location/{location}/area/{area}/stage/{stage}/createLineup")
	public String addLineup(@RequestParam("festival") String festival,
							@RequestParam("contract") String contract,
							@Valid Lineup lineup,
							@PathVariable("location") String locationName,
							@PathVariable("area") String areaName,
							@PathVariable("stage") String stageName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findByName(area, stageName);
		List<Festival> festivals = Lists.newArrayList(locationManager.findFestivals().iterator());
		festival = festival.substring(0, festival.length()-1);
		List<Contract> contracts = locationManager.findByName().toList();
		contract = contract.substring(0, contract.length()-1);
		
		for(Festival fest : festivals) {
			if(fest.getName().equals(festival)) {
				lineup.festival = fest;
				break;
			}
				
		}
		for(Contract cont : contracts) {
			if(cont.getArtist().equals(contract)) {
				lineup.setArtist(cont);
				break;
			}
		}
		lineup.setStageId(stage.getId());
		
		locationManager.save(lineup);

		return "redirect:lineup";
	}

	@GetMapping("/location/{location}/area/{area}/stage/{stage}/createLineup")
	public String addLineup(Model model,
							Lineup lineup,
							@PathVariable("location") String locationName, 
							@PathVariable("area") String areaName) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		List<Festival> festivals = Lists.newArrayList(locationManager.findFestivals().iterator());
		List<Contract> contracts = locationManager.findByName().toList();
		
		model.addAttribute("location", location);
		model.addAttribute("area", area);
		model.addAttribute("lineup", lineup);
		model.addAttribute("festivalList", festivals);
		model.addAttribute("contractList", contracts);

		return "createLineup";
	}
	
	@PostMapping("/location/{location}/area/{area}/stage/{stage}/lineup/{lineup}")
	public String editLineup(	@Valid LineupForm lineupForm,
								@RequestParam("festival") String festival,
								@RequestParam("contract") String contract,
								@PathVariable("location") String locationName,
								@PathVariable("area") String areaName,
								@PathVariable("stage") String stageName,
								@PathVariable("lineup") String lineupIdAsString) {
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findByName(area, stageName);
		
		locationManager.update(stage, lineupForm, Long.parseLong(lineupIdAsString), festival, contract);
		
		return "redirect:";
	}

	@GetMapping("/location/{location}/area/{area}/stage/{stage}/lineup/{lineup}")
	public String editLineup(	Model model,
								@PathVariable("location") String locationName,
								@PathVariable("area") String areaName,
								@PathVariable("stage") String stageName,
								@PathVariable("lineup") String lineupIdAsString) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findByName(area, stageName);
		Lineup lineup = locationManager.findById(stage, Long.parseLong(lineupIdAsString));
		List<Festival> festivals = Lists.newArrayList(locationManager.findFestivals().iterator());
		List<Contract> contracts = locationManager.findByName().toList();

		model.addAttribute("location", location);
		model.addAttribute("area", area);
		model.addAttribute("stage", stage);
		model.addAttribute("lineup", lineup);
		model.addAttribute("festivalList", festivals);
		model.addAttribute("contractList", contracts);

		return "editLineup";
	}

	@GetMapping("/location/{location}/area/{area}/stage/{stage}/lineup/{lineup}/delete")
	public String deleteLineup(	@PathVariable("location") String locationName,
								@PathVariable("area") String areaName,
								@PathVariable("stage") String stageName,
								@PathVariable("lineup") String lineupIdAsString) {
		
		Location location = locationManager.findByName(locationName);
		Area area = locationManager.findByName(location, areaName);
		Stage stage = locationManager.findByName(area, stageName);
		Lineup lineup = locationManager.findById(stage, Long.parseLong(lineupIdAsString));
		
		locationManager.deleteLineupById(lineup.getId());

		return "redirect:/location/" + location.getName() + "/area/" + area.getZone() + "/stage/" + stage.getName() + "/lineup";
	}
	//-----------------------------------------------------------------------------------------
}