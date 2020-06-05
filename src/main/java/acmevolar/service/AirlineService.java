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

package acmevolar.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.projections.AirlineListAttributes;
import acmevolar.repository.AirlineRepository;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class AirlineService {

	private AirlineRepository	airlineRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	@Autowired
	public AirlineService(final AirlineRepository airlineRepository) {
		this.airlineRepository = airlineRepository;
	}

	@Transactional(readOnly = true)
	@Cacheable("airlines")
	public Collection<Airline> findAirlines() throws DataAccessException {
		return this.airlineRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	@Cacheable("listAirlines")
	public Collection<AirlineListAttributes> findAirlinesListAttributes() throws DataAccessException {
		return this.airlineRepository.findAllAirlinesAttributes();
	}

	@Transactional(readOnly = true)
	public Airline findAirlineById(final int id) throws DataAccessException {
		return this.airlineRepository.findById(id);
	}

	@Transactional
	@CacheEvict(cacheNames = "listAirlines", allEntries = true)
	public void saveAirline(final Airline airline) throws DataAccessException {

		airline.setCreationDate(LocalDate.now());
		//creating owner
		this.airlineRepository.save(airline);
		//creating user
		this.userService.saveUser(airline.getUser());
		//creating authorities
		this.authoritiesService.saveAuthorities(airline.getUser().getUsername(), "airline");
	}

}
