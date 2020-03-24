
package acmevolar.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airline;
import acmevolar.model.Plane;
import acmevolar.service.FlightService;

import acmevolar.service.PlaneService;

@Controller
public class PlaneController {


	private final PlaneService planeService;
	private final FlightService flightService;
	
	private static final String VIEWS_PLANES_CREATE_OR_UPDATE_FORM = "planes/createPlaneForm";

	@Autowired
	public PlaneController(final PlaneService planeService, final FlightService flightService) {
		this.planeService = planeService;
		this.flightService = flightService;
	}

	@GetMapping(value = {
		"/planes"
	})
	public String showPlaneList(final Map<String, Object> model) {
		Collection<Plane> planes = this.planeService.findPlanes();
		model.put("planes", planes);
		return "planes/planesList";
	}

	@GetMapping(value = {
		"/my_planes"
	})
	public String showMyPlaneList(final Map<String, Object> model) {

		// first, we get the airline role
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// now, we substract the planes created by the same airline
		Collection<Plane> planes = this.planeService.getAllPlanesFromAirline(username);
    
		model.put("planes", planes);
		return "planes/planesList";
	}

	@GetMapping("/planes/{planeId}")
	public ModelAndView showPlane(@PathVariable("planeId") final int planeId) {
		ModelAndView mav = new ModelAndView("planes/planeDetails");
		mav.addObject(this.planeService.findPlaneById(planeId));
		return mav;
	}

	@InitBinder("airline")
	public void initAirlineBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/planes/new")
	public String initCreationForm(final Map<String, Object> model) {
		Plane plane = new Plane();
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Airline airline = this.flightService.findAirlineByUsername(username);
		plane.setAirline(airline);
		airline.addPlane(plane);

		model.put("plane", plane);

		return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/planes/new")
	public String processCreationForm(@Valid final Plane plane, final BindingResult result) {

		if (result.hasErrors()) {
			return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
		} else {
			
			if(planeService.findPlaneByReference(plane.getReference())!=null) {
				result.rejectValue("reference", "RepeatedReference", "You must introduce a reference that was not introduced in other plane.");	
				return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
			}
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();

			Airline airline = this.flightService.findAirlineByUsername(username);
			plane.setAirline(airline);
			try {
				airline.addPlane(plane);
				this.planeService.savePlane(plane);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}

			return "redirect:/planes/" + plane.getId();
		}
	}
	
	@GetMapping(value = "/planes/{planeId}/edit")
	public String initUpdateForm(@PathVariable("planeId") final int planeId, final ModelMap model) {
		Plane plane = this.planeService.findPlaneById(planeId);

		model.put("plane", plane);

		return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/planes/{planeId}/edit")
	public String processUpdateForm(@Valid final Plane plane, final BindingResult result, @PathVariable("planeId") final int planeId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("plane", plane);
			return PlaneController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
		} else {
			Plane planeToUpdate = this.planeService.findPlaneById(planeId);
			BeanUtils.copyProperties(planeToUpdate, plane, "reference", "maxSeats", "description",
					"manufacter", "model", "numberOfKm", "maxDistance", "lastMaintenance");

			try {
				this.planeService.savePlane(plane);;
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			
			return "redirect:/planes/{planeId}";// + plane.getId();
		}
	}
	
	@InitBinder("plane")
	public void initFlightBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PlaneValidator());
	}

}
