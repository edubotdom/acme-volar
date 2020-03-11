/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acmevolar.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
import acmevolar.service.FlightService;

@Controller
public class FlightController {

	private final FlightService	flightService;

	private static final String	VIEWS_FLIGHT_CREATE_FORM	= "flights/createFlightForm";


	@Autowired
	public FlightController(final FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping(value = {
		"/flights"
	})
	public String showFlightList(final Map<String, Object> model) {

		Collection<Flight> flights = new ArrayList<Flight>();
		flights.addAll(this.flightService.findPublishedFlight());
		model.put("flights", flights);
		return "flights/flightList";
	}

	@GetMapping(value = "/flights/new")
	public String initCreationForm(final Map<String, Object> model) {
		Flight flight = new Flight();
		model.put("flight", flight);
		return FlightController.VIEWS_FLIGHT_CREATE_FORM;
	}

	@PostMapping(value = "/flights/new")
	public String processCreationForm(@Valid final Flight flight, final BindingResult result) {
		if (result.hasErrors()) {
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;
		} else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Airline airline = this.flightService.findAirlineByUsername(username);
			flight.setAirline(airline);
			this.flightService.saveFlight(flight);

			return "redirect:/flights/" + flight.getId();
		}
	}

	@GetMapping("/flights/{flightId}")
	public ModelAndView showFlight(@PathVariable("flightId") final int flightId) {
		ModelAndView mav = new ModelAndView("flights/flightDetails");
		mav.addObject(this.flightService.findFlightById(flightId));
		return mav;
	}

	/*
	 * private final VetService vetService;
	 *
	 * @Autowired
	 * public FlightController(VetService clinicService) {
	 * this.vetService = clinicService;
	 * }
	 *
	 * @GetMapping(value = { "/vets" })
	 * public String showVetList(Map<String, Object> model) {
	 * // Here we are returning an object of type 'Vets' rather than a collection of Vet
	 * // objects
	 * // so it is simpler for Object-Xml mapping
	 * Vets vets = new Vets();
	 * vets.getVetList().addAll(this.vetService.findVets());
	 * model.put("vets", vets);
	 * return "vets/vetList";
	 * }
	 *
	 * @GetMapping(value = { "/vets.xml"})
	 * public @ResponseBody Vets showResourcesVetList() {
	 * // Here we are returning an object of type 'Vets' rather than a collection of Vet
	 * // objects
	 * // so it is simpler for JSon/Object mapping
	 * Vets vets = new Vets();
	 * vets.getVetList().addAll(this.vetService.findVets());
	 * return vets;
	 * }
	 */
}
