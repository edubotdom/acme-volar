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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Flight;
import acmevolar.service.FlightService;

@Controller
public class FlightController {

	private final FlightService flightService;


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
	
	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param flightId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/flights/{flightId}")
	public ModelAndView showFlight(@PathVariable("flightId") final int flightId) {
		ModelAndView mav = new ModelAndView("flights/flightDetails");
		mav.addObject(this.flightService.findFlightById(flightId));
		return mav;
	}

}
