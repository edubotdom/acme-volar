
package acmevolar.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airport;
import acmevolar.model.api.Forecast;
import acmevolar.service.AirportService;
import acmevolar.service.ForecastService;
import acmevolar.service.exceptions.DuplicatedAirportNameException;
import acmevolar.service.exceptions.IncorrectCartesianCoordinatesException;

@Controller
public class AirportController {

	private final AirportService	airportService;
	private final ForecastService	forecastService;
	

	private static final String		VIEWS_AIRPORT_CREATE_FORM	= "airports/createAirportForm";


	@Autowired
	public AirportController(final AirportService airportService, final ForecastService forecastService) {
		this.airportService = airportService;
		this.forecastService = forecastService;
	}

	@GetMapping(value = {
		"/airports"
	})
	@PreAuthorize("hasAuthority('airline') || hasAuthority('client')")
	public String showAirportsList(final Map<String, Object> model) {

		Collection<Airport> airports = new ArrayList<Airport>();
		airports.addAll(this.airportService.findAirports());
		model.put("airports", airports);
		return "airports/airportList";
	}

	@PreAuthorize("hasAuthority('airline') || hasAuthority('client')")
	@GetMapping("/airports/{airportId}")
	public ModelAndView showAirport(@PathVariable("airportId") final int airportId) {
		ModelAndView mav = new ModelAndView("airports/airportDetails");
		Airport airport = airportService.findAirportById(airportId);
		Forecast forecast = forecastService.searchForecastByCity(airport.getCity()).block();
		mav.addObject(forecast);
		mav.addObject(airport);
		return mav;
	}

	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = "/airports/new")
	public String initCreationForm(final Map<String, Object> model) {
		Airport airport = new Airport();

		model.put("airport", airport);

		return AirportController.VIEWS_AIRPORT_CREATE_FORM;
	}

	@PreAuthorize("hasAuthority('airline')")
	@PostMapping(value = "/airports/new")
	public String processCreationForm(@Valid final Airport airport, final BindingResult result) {

		if (result.hasErrors()) {
			return AirportController.VIEWS_AIRPORT_CREATE_FORM;
		} else if(this.airportService.findAirportsByName(airport.getName()).size()!=0){
			result.rejectValue("name", "duplicate", "Already exists");
			return AirportController.VIEWS_AIRPORT_CREATE_FORM;
		} else {
		
			try {
				this.airportService.saveAirport(airport);
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (IncorrectCartesianCoordinatesException e) {
				e.printStackTrace();
			} catch (DuplicatedAirportNameException e) {
				result.rejectValue("name", "duplicate", "Already exists");
				return AirportController.VIEWS_AIRPORT_CREATE_FORM;
			}


			return "redirect:/airports/" + airport.getId();
		}
	}

	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = "/airports/{airportId}/edit")
	public String initUpdateForm(@PathVariable("airportId") final int airportId, final ModelMap model) {
		Airport airport = this.airportService.findAirportById(airportId);

		model.put("airport", airport);

		return AirportController.VIEWS_AIRPORT_CREATE_FORM;
	}

	@PreAuthorize("hasAuthority('airline')")
	@PostMapping(value = "/airports/{airportId}/edit")
	public String processUpdateForm(@Valid final Airport airport, final BindingResult result, @PathVariable("airportId") final int airportId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("airport", airport);
			return AirportController.VIEWS_AIRPORT_CREATE_FORM;
		} else {
			Airport airportToUpdate = this.airportService.findAirportById(airportId);
			BeanUtils.copyProperties(airportToUpdate, airport, "name", "maxNumberOfPlanes", "maxNumberOfClients", "latitude", "longitude", "code", "city");

			try {
				this.airportService.saveAirport(airport);
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (IncorrectCartesianCoordinatesException e) {
				e.printStackTrace();
			} catch (DuplicatedAirportNameException e) {
				e.printStackTrace();
			}
			return "redirect:/airports/{airportId}";
		}
	}

	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = "/airports/{airportId}/delete")
	public String deleteAirport(@PathVariable("airportId") final int airportId) {
		Optional<Airport> airport = this.airportService.findById(airportId);
		if (airport.isPresent()) {
			this.airportService.deleteAirport(airport.get());
		}
		return "redirect:/airports/";

	}

}
