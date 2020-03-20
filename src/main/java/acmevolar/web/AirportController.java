
package acmevolar.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airport;
import acmevolar.model.api.Forecast;
import acmevolar.service.AirportService;
import acmevolar.service.ForecastService;
import acmevolar.service.exceptions.DuplicatedPetNameException;

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
	public String showAirportsList(final Map<String, Object> model) {

		Collection<Airport> airports = new ArrayList<Airport>();
		airports.addAll(this.airportService.findAirports());
		model.put("airports", airports);
		return "airports/airportList";
	}

	@GetMapping("/airports/{airportId}")
	public ModelAndView showAirport(@PathVariable("airportId") final int airportId) {
		ModelAndView mav = new ModelAndView("airports/airportDetails");
		Airport airport = airportService.findAirportById(airportId);
		Forecast forecast = forecastService.searchForecastByCity(airport.getCity()).block();
		mav.addObject(airport);
		mav.addObject(forecast);
		return mav;
	}

	@GetMapping(value = "/airports/new")
	public String initCreationForm(final Map<String, Object> model) {
		Airport airport = new Airport();

		model.put("airport", airport);

		return AirportController.VIEWS_AIRPORT_CREATE_FORM;
	}

	@PostMapping(value = "/airports/new")
	public String processCreationForm(@Valid final Airport airport, final BindingResult result) {

		if (result.hasErrors()) {
			return AirportController.VIEWS_AIRPORT_CREATE_FORM;
		} else {
			try {
				this.airportService.saveAirport(airport);
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (DuplicatedPetNameException e) {
				e.printStackTrace();
			}

			return "redirect:/airports/" + airport.getId();
		}
	}

	@GetMapping(value = "/airports/{airportId}/edit")
	public String initUpdateForm(@PathVariable("airportId") final int airportId, final ModelMap model) {
		Airport airport = this.airportService.findAirportById(airportId);

		model.put("airport", airport);

		return AirportController.VIEWS_AIRPORT_CREATE_FORM;
	}

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
			} catch (DuplicatedPetNameException e) {
				e.printStackTrace();
			}
			return "redirect:/airports/" + airport.getId();
		}
	}

	@GetMapping(value = "/airports/{airportId}/delete")
	public String deleteAirport(@PathVariable("airportId") final int airportId) {
		Optional<Airport> airport = this.airportService.findById(airportId);
		if (airport.isPresent()) {
			this.airportService.deleteAirport(airport.get());
		}
		return "redirect:/airports/";

	}

}
