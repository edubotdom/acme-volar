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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Airline;
import acmevolar.service.AirlineService;
import acmevolar.service.AuthoritiesService;
import acmevolar.service.UserService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class AirlineController {

	private static final String		VIEWS_AIRLINE_CREATE_FORM	= "airlines/createAirlineForm";

	private final AirlineService	airlineService;


	@Autowired
	public AirlineController(final AirlineService airlineService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.airlineService = airlineService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/airlines/new")
	public String initCreationForm(final Map<String, Object> model) {
		Airline airline = new Airline();
		model.put("airline", airline);
		return AirlineController.VIEWS_AIRLINE_CREATE_FORM;
	}

	@PostMapping(value = "/airlines/new")
	public String processCreationForm(@Valid final Airline airline, final BindingResult result) {
		if (result.hasErrors()) {
			return AirlineController.VIEWS_AIRLINE_CREATE_FORM;
		} else {
			//creating owner, user and authorities
			this.airlineService.saveAirline(airline);

			return "redirect:/airlines/" + airline.getId();
		}
	}

	@GetMapping(value = {
		"/airlines"
	})
	public String showAirlineList(final Map<String, Object> model) {

		Collection<Airline> airlines = new ArrayList<Airline>();
		airlines.addAll(this.airlineService.findAirlines());
		model.put("airlines", airlines);
		return "airlines/airlinesList";
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/airlines/{airlineId}")
	public ModelAndView showAirline(@PathVariable("airlineId") final int airlineId) {
		ModelAndView mav = new ModelAndView("airlines/airlineDetails");
		mav.addObject(this.airlineService.findAirlineById(airlineId));
		return mav;
	}

}
