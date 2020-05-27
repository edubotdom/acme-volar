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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.repository.AirportRepository;
import acmevolar.repository.RunwayRepository;

@Service
public class RunwayService {

	private RunwayRepository	runwayRepository;
	private AirportRepository	airportRepository;


	@Autowired
	public RunwayService(final RunwayRepository runwayRepository, final AirportRepository airportRepository) {
		this.runwayRepository = runwayRepository;
		this.airportRepository = airportRepository;
	}

	@Transactional(readOnly = true)
	public Runway findRunwayById(final int id) throws DataAccessException {
		return this.runwayRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public RunwayType findRunwayTypeById(final Integer runwayTypeId) throws DataAccessException {
		return this.runwayRepository.findRunwayTypeById(runwayTypeId);
	}

	@Transactional(readOnly = true)
	public List<Runway> findAllRunway() throws DataAccessException {
		return this.runwayRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Cacheable("listRunwaysByAirpotId")
	public List<Runway> findRunwaysByAirportId(final Integer airportId) throws DataAccessException {
		return this.runwayRepository.findRunwaysByAirportId(airportId);
	}

	//FlightStatusType
	@Transactional(readOnly = true)
	public List<RunwayType> findRunwaysTypes() throws DataAccessException {
		return this.runwayRepository.findRunwaysTypes();
	}

	@Transactional
	@CacheEvict(cacheNames = "listRunwaysByAirpotId", allEntries = true)
	public void saveRunway(final Runway runway) throws DataAccessException {
		runway.setName(runway.getName().replace(",", ""));
		this.runwayRepository.save(runway);
	}

	@Transactional(readOnly = true)
	public Airport findAirportById(final Integer airportId) throws DataAccessException {
		Airport airport = this.runwayRepository.findAirportById(airportId);
		return airport;
	}

	@CacheEvict(cacheNames = "listRunwaysByAirpotId", allEntries = true)
	public void deleteRunwayById(final Integer runwayId) throws DataAccessException {
		this.runwayRepository.deleteById(runwayId);
	}

	@Transactional(readOnly = true)
	public List<Runway> findRunwaysByName(final String runwayName) {
		return this.runwayRepository.findRunwaysByName(runwayName);
	}

}
