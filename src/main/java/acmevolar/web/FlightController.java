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
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airline;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
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

		List<String> estados = new ArrayList<String>();
		estados.add("cancelled");
		estados.add("delayed");
		estados.add("on_time");

		//List<String> estados = this.flightService.findFlightStatusTypes().stream().map(s -> s.getName()).collect(Collectors.toList());

		model.put("estados", estados);
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

	@GetMapping(value = {
		"/flights/my_flights"
	})
	public String showAirlineFlightList(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Flight> flights = new ArrayList<Flight>();
		flights.addAll(this.flightService.findAirlineFlight(username));
		model.put("flights", flights);
		return "flights/flightList";
	}

	@GetMapping("/flights/{flightId}")
	public ModelAndView showFlight(@PathVariable("flightId") final int flightId) {
		ModelAndView mav = new ModelAndView("flights/flightDetails");
		mav.addObject(this.flightService.findFlightById(flightId));
		return mav;
	}

	//@Secured("hasRole('airline')")
	@GetMapping(value = "/flights/{flightId}/edit")
	public String initUpdateForm(@PathVariable("flightId") final int flightId, final ModelMap model) {
		Flight flight = this.flightService.findFlightById(flightId);

		/*
		 * List<String> estados = new ArrayList<String>();
		 * estados.add("cancelled");
		 * estados.add("delayed");
		 * estados.add("on_time");
		 */

		List<FlightStatusType> estados = this.flightService.findFlightStatusTypes();

		model.put("estados", estados);

		model.put("flight", flight);
		return FlightController.VIEWS_FLIGHT_CREATE_FORM;
	}

	//@Secured("hasRole('airline')")
	@PostMapping(value = "/flights/{flightId}/edit")
	public String processUpdateForm(@Valid final Flight flight, final BindingResult result, @PathVariable("flightId") final int flightId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("flight", flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;
		} else {

			Flight flightToUpdate = this.flightService.findFlightById(flightId);
			BeanUtils.copyProperties(flightToUpdate, flight, "reference", "seats", "price", "flightStatus", "published");

			this.flightService.saveFlight(flight);
			return "redirect:/flights/" + flight.getId();
		}
	}
}
