
package acmevolar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.service.AirportService;

@Controller
public class AirportController {

	private final AirportService airportService;


	@Autowired
	public AirportController(final AirportService airportService) {
		this.airportService = airportService;
	}

	@GetMapping("/airports/{airportId}")
	public ModelAndView showAirport(@PathVariable("airportId") final int airportId) {
		ModelAndView mav = new ModelAndView("airports/airportDetails");
		mav.addObject(this.airportService.findAirportById(airportId));
		return mav;
	}

}
