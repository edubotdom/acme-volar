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
package acmevolar.service;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Client;
import acmevolar.repository.ClientRepository;
import acmevolar.service.exceptions.BirthDateIsAfterCreationDateException;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClientService {

	private ClientRepository clientRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}	

	@Transactional(readOnly = true)	
	public Collection<Client> findClients() throws DataAccessException {
		return clientRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Client findClientById(int id) throws DataAccessException {
		return clientRepository.findById(id);
	}

	@Transactional
	public void saveClient(Client client) throws DataAccessException,ConstraintViolationException,BirthDateIsAfterCreationDateException {
		
		if(client.getBirthDate().isAfter(LocalDate.now())) {
			throw new BirthDateIsAfterCreationDateException();
		}
		client.setCreationDate(LocalDate.now());
		//creating owner
		clientRepository.save(client);		
		//creating user
		userService.saveUser(client.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(client.getUser().getUsername(), "client");
	}		

}
