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

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airline;
import acmevolar.model.Airport;
import acmevolar.model.Flight;
import acmevolar.model.Owner;
import acmevolar.model.Pet;
import acmevolar.model.Runway;
import acmevolar.service.FlightService;
import acmevolar.service.RunwayService;
import acmevolar.service.exceptions.DuplicatedPetNameException;

@Controller
public class RunwayController {

	private final RunwayService	runwayService;

	private static final String	VIEWS_RUNWAYS_CREATE_OR_UPDATE_FORM	= "runways/createOrUpdateRunwaysForm";


	@Autowired
	public RunwayController(final RunwayService runwayService) {
		this.runwayService = runwayService;
	}

	@GetMapping(value = {"/runways"})
	public String showRunwayList(final Map<String, Object> model) {

		Collection<Runway> runways = new ArrayList<Runway>();
		runways.addAll(this.runwayService.findAllRunway());
		model.put("runways", runways);
		return "runways/runwayList";
	}

	@GetMapping(value = "/runways/new")
	public String initCreationForm(final Map<String, Object> model, Integer airportId) {
		Runway runway = new Runway();
		Airport airport = this.runwayService.findAirportById(airportId);
		model.put("airport", airport);
		model.put("runway", runway);
		
		return RunwayController.VIEWS_RUNWAYS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/runways/new")
	public String processCreationForm(@Valid final Runway runway, final BindingResult result) {
		if (result.hasErrors()) {
			return RunwayController.VIEWS_RUNWAYS_CREATE_OR_UPDATE_FORM;
		} else {
		
			this.runwayService.saveRunway(runway);
			return "redirect:/runways/" + runway.getId();
		}
	}
	

	@GetMapping("/runways/{runwayId}")
	public ModelAndView showFlight(@PathVariable("runwayId") final int runwayId) {
		ModelAndView mav = new ModelAndView("flights/flightDetails");
		mav.addObject(this.runwayService.findRunwayById(runwayId));
		return mav;
	}

	@GetMapping(value = "/runways/{runwayId}/edit")
	public String initUpdateForm(@PathVariable("runwayId") int runwayId, Integer airportId, ModelMap model) {
		Runway runway = this.runwayService.findRunwayById(runwayId);
		Airport airport = this.runwayService.findAirportById(airportId);
		model.put("airport", airport);
		model.put("runway", runway);
		return VIEWS_RUNWAYS_CREATE_OR_UPDATE_FORM;
	}

        @PostMapping(value = "/runways/{runwayId}/edit")
	public String processUpdateForm(@Valid Runway runway, BindingResult result, @PathVariable("runwayId") int runwayId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("runway", runway);
			return VIEWS_RUNWAYS_CREATE_OR_UPDATE_FORM;
		}
		else {
			Runway runwayToUpdate=this.runwayService.findRunwayById(runwayId);
			BeanUtils.copyProperties(runwayToUpdate, runway, "name", "type", "date", "airport");                                                                                  
                             
			this.runwayService.saveRunway(runway);                   
			return "redirect:/runways/" + runway.getId();
                    }
		}
	}
	


