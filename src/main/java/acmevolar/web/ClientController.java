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

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Client;
import acmevolar.projections.ClientListAttributes;
import acmevolar.service.AuthoritiesService;
import acmevolar.service.ClientService;
import acmevolar.service.UserService;
import acmevolar.service.exceptions.BirthDateIsAfterCreationDateException;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class ClientController {

	private static final String	VIEWS_CLIENT_CREATE_FORM	= "clients/createClientForm";

	private final ClientService	clientService;


	@Autowired
	public ClientController(final ClientService clientService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.clientService = clientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	@PreAuthorize("!hasAuthority('airline') && !hasAuthority('client')")	@GetMapping(value = "/clients/new")
	public String initCreationForm(final Map<String, Object> model) {
		Client client = new Client();
		model.put("client", client);
		return ClientController.VIEWS_CLIENT_CREATE_FORM;
	}
	@PreAuthorize("!hasAuthority('airline') && !hasAuthority('client')")
	@PostMapping(value = "/clients/new")
	public String processCreationForm(@Valid final Client client, final BindingResult result) {
		if (result.hasErrors()) {
			return ClientController.VIEWS_CLIENT_CREATE_FORM;
		} else {
			//creating owner, user and authorities
			try {
				this.clientService.saveClient(client);
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (ConstraintViolationException e) {
				e.printStackTrace();
				return ClientController.VIEWS_CLIENT_CREATE_FORM;
			} catch (BirthDateIsAfterCreationDateException e) {
				result.rejectValue("birthDate", "BirthDateMustBePast", "Birthdate must be a past date");
				return ClientController.VIEWS_CLIENT_CREATE_FORM;
			}

			//return "redirect:/clients/" + client.getId();
			return "redirect:/";
		}
	}
	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = {
		"/clients"
	})
	public String showClientList(final Map<String, Object> model) {

		Collection<ClientListAttributes> clients = new ArrayList<>();
		clients.addAll(this.clientService.findClientsListAttributes());
		model.put("clients", clients);
		return "clients/clientsList";
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@PreAuthorize("hasAuthority('airline')")
	@GetMapping("/clients/{clientId}")
	public ModelAndView showClient(@PathVariable("clientId") final int clientId) {
		ModelAndView mav = new ModelAndView("clients/clientDetails");
		mav.addObject(this.clientService.findClientById(clientId));
		return mav;
	}

}
