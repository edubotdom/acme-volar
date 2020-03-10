/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.model.Vets;
import acmevolar.service.AuthoritiesService;
import acmevolar.service.ClientService;
import acmevolar.service.UserService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class ClientController {

	private static final String VIEWS_CLIENT_CREATE_FORM = "clients/createClientForm";

	private final ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService, UserService userService, AuthoritiesService authoritiesService) {
		this.clientService = clientService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/clients/new")
	public String initCreationForm(Map<String, Object> model) {
		Client client = new Client();
		model.put("client", client);
		return VIEWS_CLIENT_CREATE_FORM;
	}

	@PostMapping(value = "/clients/new")
	public String processCreationForm(@Valid Client client, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENT_CREATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.clientService.saveClient(client);
			
			return "redirect:/clients/" + client.getId();
		}
	}
	
	@GetMapping(value = { "/clients" })
	public String showFlightList(Map<String, Object> model) {

		Collection<Client> clients = new ArrayList<Client>();
		clients.addAll(this.clientService.findClients());
		model.put("clients", clients);
		return "clients/clientsList";
	}
	
	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/clients/{clientId}")
	public ModelAndView showClient(@PathVariable("clientId") int clientId) {
		ModelAndView mav = new ModelAndView("clients/clientDetails");
		mav.addObject(this.clientService.findClientById(clientId));
		return mav;
	}

}
