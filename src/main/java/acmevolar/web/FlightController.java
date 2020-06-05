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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import acmevolar.projections.FlightListAttributes;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;

@Controller
public class FlightController {

	private final FlightService	flightService;
	private final PlaneService	planeService;
	private static final String	VIEWS_FLIGHT_CREATE_FORM	= "flights/createFlightForm";
	private static final String	FLIGHTS_WORD				= "flights";
	private static final String	FLIGHT_LIST_URI				= "flights/flightList";
	private static final String	FLIGHT						= "flight";
	private static final String	REFERENCE					= "reference";
	private static final String	DEPARTES					= "departes";
	private static final String	LANDS						= "lands";
	private static final String	AIRPORTFULLOFPLANES			= "airportFullOfPlanes";
	private static final String	AIRPORTFULLOFPLANES_MESSAGE	= "This airport is full of planes this day";


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

		Collection<FlightListAttributes> flights = this.flightService.findAllClientFlightListAttributesPublishedFuture();
		model.put(FLIGHTS_WORD, flights);
		return FLIGHT_LIST_URI;
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
		List<Boolean> opcionesPublicao = new ArrayList<>();
		opcionesPublicao.add(true);
		opcionesPublicao.add(false);

		model.put("opciones_publicao", opcionesPublicao);
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
		model.put(FLIGHT, flight);

		return FlightController.VIEWS_FLIGHT_CREATE_FORM;
	}
	@PreAuthorize("hasAuthority('airline')")
	@PostMapping(value = "/flights/new")
	public String processCreationForm(final Map<String, Object> model, @Valid final Flight flight, final BindingResult result) {

		if (result.hasErrors()) {
			model.put(FLIGHT, flight);
			this.insertData(model, flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;

		} else {

			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Airline airline = this.flightService.findAirlineByUsername(username);
			flight.setAirline(airline);
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
				result.rejectValue(DEPARTES, AIRPORTFULLOFPLANES, AIRPORTFULLOFPLANES_MESSAGE);

			}
			if (this.flightService.findFlightByReference(flight.getReference()) != null) {
				result.rejectValue("reference", "referenceTaken", "Flight reference already taken.");

			}
			if (numPlanesInLandAirport + 1L >= flight.getLands().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue(LANDS, AIRPORTFULLOFPLANES, AIRPORTFULLOFPLANES_MESSAGE);

			}

			if (result.hasErrors()) {
				model.put(FLIGHT, flight);
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
		Collection<FlightListAttributes> flights = this.flightService.findAllAirlineFlightListAttributes(username);
		model.put(FLIGHTS_WORD, flights);
		return FLIGHT_LIST_URI;
	}

	@GetMapping("/flights/{flightId}")
	public ModelAndView showFlight(@PathVariable("flightId") final int flightId) {
		Flight flight = this.flightService.findFlightById(flightId);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Airline airline = this.flightService.findAirlineByUsername(username);

		if (flight.getDepartDate().before(Date.from(Instant.now()))) {
			ModelAndView mav2 = new ModelAndView(FLIGHT_LIST_URI);
			Collection<Flight> flights = new ArrayList<>();
			flights.addAll(this.flightService.findPublishedFutureFlight());
			mav2.addObject(FLIGHTS_WORD, flights);

			return mav2;
		}

		if (airline == null && flight.getPublished()) {
			ModelAndView mav = new ModelAndView("flights/flightDetails");
			mav.addObject(flight);
			return mav;
		} else if (airline == null && !flight.getPublished()) {

			ModelAndView mav2 = new ModelAndView(FLIGHT_LIST_URI);
			Collection<Flight> flights = new ArrayList<>();
			flights.addAll(this.flightService.findPublishedFutureFlight());
			mav2.addObject(FLIGHTS_WORD, flights);

			return mav2;
		}

		if (flight.getPublished() || airline != null && flight.getAirline().getName().equals(airline.getName())) {

			ModelAndView mav = new ModelAndView("flights/flightDetails");
			mav.addObject(flight);
			return mav;
		} else {
			ModelAndView mav2 = new ModelAndView(FLIGHT_LIST_URI);
			Collection<Flight> flights = new ArrayList<>();
			flights.addAll(this.flightService.findPublishedFutureFlight());
			mav2.addObject(FLIGHTS_WORD, flights);

			return mav2;
		}

	}

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
			model.put(FLIGHT, flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;
		}
	}

	@PostMapping(value = "/flights/{flightId}/edit")
	@PreAuthorize("hasAuthority('airline')")
	public String processUpdateForm(@Valid final Flight flight, final BindingResult result, @PathVariable("flightId") final int flightId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put(FLIGHT, flight);
			this.insertData(model, flight);
			return FlightController.VIEWS_FLIGHT_CREATE_FORM;
		} else {
			Flight flightToUpdate = this.flightService.findFlightById(flightId);
			if (this.flightService.findFlightByReference(flight.getReference()) != null && !flight.getReference().equalsIgnoreCase(flightToUpdate.getReference())) {
				result.rejectValue(REFERENCE, "referenceTaken", "Flight reference already taken.");
			}

			BeanUtils.copyProperties(flightToUpdate, flight, REFERENCE, "seats", "price", "flightStatus", "published", "landDate", "departDate", LANDS, DEPARTES, "plane");

			// we get the flight (one per plane) in the same day that depart airport
			Long numPlanesInDepartAirport = this.flightService.findFlights().stream().filter(x -> x.getDepartDate().equals(flight.getDepartDate())).count();

			// we get the flight (one per plane) in the same day that depart airport
			Long numPlanesInLandAirport = this.flightService.findFlights().stream().filter(x -> x.getLandDate().equals(flight.getLandDate())).count();

			if (numPlanesInDepartAirport + 1L >= flight.getDepartes().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue(DEPARTES, AIRPORTFULLOFPLANES, AIRPORTFULLOFPLANES_MESSAGE);

			}
			if (numPlanesInLandAirport + 1L >= flight.getLands().getAirport().getMaxNumberOfPlanes()) {
				// this is caused becaused an airport only can deals with a limit of planes per
				// day
				result.rejectValue(LANDS, AIRPORTFULLOFPLANES, AIRPORTFULLOFPLANES_MESSAGE);

			}

			if (result.hasErrors()) {
				model.put(FLIGHT, flight);
				this.insertData(model, flight);
				return FlightController.VIEWS_FLIGHT_CREATE_FORM;
			} else {
				this.flightService.saveFlight(flight);
				return "redirect:/flights/{flightId}";
			}
		}
	}

	@InitBinder(FLIGHT)
	public void initFlightBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FlightValidator());
	}

}
