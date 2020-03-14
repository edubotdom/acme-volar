
package acmevolar.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airline;
import acmevolar.model.Flight;
import acmevolar.model.Plane;
import acmevolar.model.Visit;
import acmevolar.service.AirlineService;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;

@Controller
public class PlaneController {

	private final PlaneService planeService;
	private final AirlineService airlineService;
	private final FlightService flightService;
	
	private static final String VIEWS_PLANES_CREATE_OR_UPDATE_FORM = "planes/createPlaneForm";

	@Autowired
	public PlaneController(final PlaneService planeService, final AirlineService airlineService, final FlightService flightService) {
		this.planeService = planeService;
		this.airlineService = airlineService;
		this.flightService = flightService;
	}
	
	public List<Flight> getAllFlightsFromPlane(Plane plane) {
		List<Flight> res = flightService.findFlights().stream()
				.filter(x->x.getPlane().getId().equals(plane.getId()))
				.collect(Collectors.toList());
		return res;
	}
	
	public List<Plane> getAllPlanesFromAirline(Airline airline){
		List<Plane> res = flightService.findFlights().stream()
				.filter(x->x.getAirline().getId().equals(airline.getId()))
				.map(x->x.getPlane())
				.distinct()
				.collect(Collectors.toList());
		return res;
	}
	
	

	@GetMapping(value = {
		"/planes"
	})
	public String showPlaneList(final Map<String, Object> model) {
		Collection<Plane> planes = new ArrayList<Plane>();
		planes.addAll(this.planeService.findPlanes());
		model.put("planes", planes);
		return "planes/planesList";
	}
	
	@GetMapping(value = {
		"/my_planes"
	})
	public String showMyPlaneList(final Map<String, Object> model) {
		
		// first, we get the airline role
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Airline airline = airlineService.findAirlines().stream()
				.filter(x->x.getUser().getUsername().equals(username))
				.findFirst()
				.get();
		
		// now, we substract the planes created by the same airline
		Collection<Plane> planes = new ArrayList<Plane>();
		planes.addAll(getAllPlanesFromAirline(airline));
		model.put("planes", planes);
		return "planes/planesList";
	}
	
	

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param flightId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/planes/{planeId}")
	public ModelAndView showPlane(@PathVariable("planeId") final int planeId) {
		ModelAndView mav = new ModelAndView("planes/planeDetails");
		mav.addObject(this.planeService.findPlaneById(planeId));
		return mav;
	}
	
	
	@GetMapping(value = "/planes/new")
	public String initCreationForm(final Map<String, Object> model) {
		Plane plane = new Plane();

		model.put("plane", plane);

		return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/planes/new")
	public String processCreationForm(@Valid final Plane plane, final BindingResult result) {
		if (result.hasErrors()) {
			return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
		} else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Airline airline = this.flightService.findAirlineByUsername(username);
			plane.setAirline(airline);
			this.planeService.savePlane(plane);

			return "redirect:/planes/" + plane.getId();
		}
	}
	

}
