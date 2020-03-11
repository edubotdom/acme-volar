
package acmevolar.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airport;
import acmevolar.service.AirportService;

@Controller
public class AirportController {

	private final AirportService airportService;


	@Autowired
	public AirportController(final AirportService airportService) {
		this.airportService = airportService;
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
		mav.addObject(this.airportService.findAirportById(airportId));
		return mav;
	}

}
