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
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;

@Controller
public class FlightController {

	private final FlightService	flightService;
	private final PlaneService	planeService;
	private static final String	VIEWS_FLIGHT_CREATE_FORM	= "flights/createFlightForm";


	@Autowired
	public FlightController(final FlightService flightService, final PlaneService planeService) {
		this.flightService = flightService;
		this.planeService = planeService;
	}
	//@PreAuthorize("hasAuthority('client') || isAnonymous()")
	@GetMapping(value = {
		"/flights"
	})
	public String showFlightList(final Map<String, Object> model) {

		Collection<Flight> flights = new ArrayList<Flight>();
		flights.addAll(this.flightService.findPublishedFutureFlight());
		model.put("flights", flights);
		return "flights/flightList";
	}

	@InitBinder("plane")
	public void initPlaneBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("flightStatus")
	public void initFlightStatusBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("name");
	}

	public void insertData(final Map<String, Object> model, final Flight flight) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Plane> planes = this.flightService.findPlanesbyAirline(username);
		List<Runway> departuresList = this.flightService.findDepartingRunways();
		List<Runway> landsList = this.flightService.findLandingRunways();
		List<FlightStatusType> estados = this.flightService.findFlightStatusTypes();
		List<Boolean> opciones_publicao = new ArrayList<>();
		opciones_publicao.add(true);
		opciones_publicao.add(false);

		model.put("opciones_publicao", opciones_publicao);
		model.put("planes", planes);
		model.put("departuresList", departuresList);
		model.put("landsList", landsList);
		model.put("estados", estados);

	}
	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = "/flights/new")
	public String initCreationForm(final Map<String, Object> model) {
		Flight flight = new Flight();
		flight.setPublished(false);
		this.insertData(model, flight);
		model.put("flight", flight);

		return FlightController.VIEWS_FLIGHT_CREATE_FORM;
	}
	@PreAuthorize("hasAuthority('airline')")
	@PostMapping(value = "/flights/new")
	public String processCreationForm(final Map<String, Object> model, @Valid final Flight flight, final BindingResult result) {

		if (result.hasErrors()) {
			model.put("flight", flight);
			this.insertData(model, flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;

		} else {

			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Airline airline = this.flightService.findAirlineByUsername(username);
			flight.setAirline(airline);
			// flight.getPlane().addFlight(flight);
			airline.addFlight(flight);

			Integer idPlane = flight.getPlane().getId();
			Plane planeFlight = this.planeService.findPlaneById(idPlane);
			Set<Flight> flightsFromPlane = planeFlight.getFlightsInternal();
			flightsFromPlane.add(flight);
			planeFlight.setFlightsInternal(flightsFromPlane);

			// we get the flight (one per plane) in the same day that depart airport
			Long numPlanesInDepartAirport = this.flightService.findFlights().stream().filter(x -> x.getDepartDate().equals(flight.getDepartDate())).count();

			// we get the flight (one per plane) in the same day that depart airport
			Long numPlanesInLandAirport = this.flightService.findFlights().stream().filter(x -> x.getLandDate().equals(flight.getLandDate())).count();

			if (numPlanesInDepartAirport + 1L >= flight.getDepartes().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue("departes", "AirportFullOfPlanes", "This airport is full of planes this day");

			}
			if (this.flightService.findFlightByReference(flight.getReference()) != null) {
				result.rejectValue("reference", "referenceTaken", "Flight reference already taken.");

			}
			if (numPlanesInLandAirport + 1L >= flight.getLands().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue("lands", "AirportFullOfPlanes", "This airport is full of planes this day");

			} /*
				 * if (flight.getDepartes().getAirport().getName().equals(flight.getLands().getAirport().getName())) {
				 * result.rejectValue("lands", "PathClosed", "This path is close, choose another airport(runway)");
				 *
				 *
				 * } if (flight.getDepartDate().after(flight.getLandDate())) {
				 * result.rejectValue("landDate", "LandingBeforeDepartDate",
				 * "Landing date can't be programmed before departing date");
				 *
				 *
				 * }if (flight.getDepartDate().before(Calendar.getInstance().getTime())) {
				 * result.rejectValue("departDate", "DepartBeforePresentDate",
				 * "Depart date can't be programmed before the present");
				 *
				 *
				 * }
				 */

			if (result.hasErrors()) {
				model.put("flight", flight);
				this.insertData(model, flight);
				return FlightController.VIEWS_FLIGHT_CREATE_FORM;
			} else {

				this.flightService.saveFlight(flight);

				return "redirect:/flights/" + flight.getId();
			}
		}
	}
	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = {
		"/my_flights"
	})
	public String showAirlineFlightList(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Flight> flights = this.flightService.findAirlineFlight(username);
		model.put("flights", flights);
		return "flights/flightList";
	}

	@GetMapping("/flights/{flightId}")
	public ModelAndView showFlight(@PathVariable("flightId") final int flightId) {
		Flight flight = this.flightService.findFlightById(flightId);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Airline airline = this.flightService.findAirlineByUsername(username);
		
		if(airline==null&&flight.getPublished()) {
			ModelAndView mav = new ModelAndView("flights/flightDetails");
			mav.addObject(flight);
			return mav;
		} else if (airline==null&&!flight.getPublished()) {
			
			ModelAndView mav2 = new ModelAndView("flights/flightList");
			Collection<Flight> flights = new ArrayList<Flight>();
			flights.addAll(this.flightService.findPublishedFutureFlight());
			mav2.addObject("flights", flights);
			
			return mav2;
		}
		
		if (flight.getPublished() || flight.getAirline().getName().equals(airline.getName())) {

			ModelAndView mav = new ModelAndView("flights/flightDetails");
			mav.addObject(flight);
			return mav;
		} else {
			ModelAndView mav2 = new ModelAndView("flights/flightList");
			Collection<Flight> flights = new ArrayList<Flight>();
			flights.addAll(this.flightService.findPublishedFutureFlight());
			mav2.addObject("flights", flights);
			
			return mav2;
		}
		
	}

	// @Secured("hasRole('airline')")
	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = "/flights/{flightId}/edit")
	public String initUpdateForm(@PathVariable("flightId") final int flightId, final ModelMap model) {
		Flight flight = this.flightService.findFlightById(flightId);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Airline airline = this.flightService.findAirlineByUsername(username);
		if (flight.getPublished() || !flight.getAirline().getName().equals(airline.getName())) {
			return "redirect:/flights/{flightId}";
		} else {
			this.insertData(model, flight);
			model.put("flight", flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;
		}
	}

	@PostMapping(value = "/flights/{flightId}/edit")
	@PreAuthorize("hasAuthority('airline')")
	public String processUpdateForm(@Valid final Flight flight, final BindingResult result, @PathVariable("flightId") final int flightId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("flight", flight);
			this.insertData(model, flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;
		} else {
			Flight flightToUpdate = this.flightService.findFlightById(flightId);
			BeanUtils.copyProperties(flightToUpdate, flight, "reference", "seats", "price", "flightStatus", "published", "landDate", "departDate", "lands", "departes", "plane");

			// we get the flight (one per plane) in the same day that depart airport
			Long numPlanesInDepartAirport = this.flightService.findFlights().stream().filter(x -> x.getDepartDate().equals(flight.getDepartDate())).count();

			// we get the flight (one per plane) in the same day that depart airport
			Long numPlanesInLandAirport = this.flightService.findFlights().stream().filter(x -> x.getLandDate().equals(flight.getLandDate())).count();

			if (numPlanesInDepartAirport + 1L >= flight.getDepartes().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue("departes", "AirportFullOfPlanes", "This airport is full of planes this day");

			}
			if (numPlanesInLandAirport + 1L >= flight.getLands().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue("lands", "AirportFullOfPlanes", "This airport is full of planes this day");

			} /*
				 * if (flight.getDepartes().getAirport().getName().equals(flight.getLands().getAirport().getName())) {
				 * result.rejectValue("lands", "PathClosed", "This path is close, choose another airport(runway)");
				 *
				 * } if (flight.getDepartDate().after(flight.getLandDate())) {
				 * result.rejectValue("landDate", "LandingBeforeDepartDate",
				 * "Landing date can't be programmed before departing date");
				 *
				 * } if (flight.getDepartDate().before(Calendar.getInstance().getTime())) {
				 * result.rejectValue("departDate", "DepartBeforePresentDate",
				 * "Depart date can't be programmed before the present");
				 * }
				 */

			if (result.hasErrors()) {
				model.put("flight", flight);
				this.insertData(model, flight);
				return FlightController.VIEWS_FLIGHT_CREATE_FORM;
			} else {
				this.flightService.saveFlight(flight);
				return "redirect:/flights/{flightId}";
			}
		}
	}

	@InitBinder("flight")
	public void initFlightBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FlightValidator());
	}

}
