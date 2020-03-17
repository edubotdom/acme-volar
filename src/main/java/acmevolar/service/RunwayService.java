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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.repository.RunwayRepository;


@Service
public class RunwayService {

	private RunwayRepository runwayRepository;
	
	@Autowired
	public RunwayService(RunwayRepository runwayRepository) {
		this.runwayRepository = runwayRepository;
	}
	
	@Transactional(readOnly = true)
	public Runway findRunwayById(int id) throws DataAccessException {
		return runwayRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Runway> findAllRunway() throws DataAccessException {
		return runwayRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Runway> findRunwaysByAirportId(Integer airportId) throws DataAccessException {
		return runwayRepository.findRunwaysByAirportId(airportId);
	}
	
	//FlightStatusType
	@Transactional(readOnly = true)
	public List<RunwayType> findRunwaysTypes() throws DataAccessException {
		return this.runwayRepository.findRunwaysTypes();
	}
	
	@Transactional
	public void saveRunway(Runway runway) throws DataAccessException {
		runway.setName(runway.getName().replace(",", ""));
		runwayRepository.save(runway);                
	}

	@Transactional(readOnly = true)
	public Airport findAirportById(Integer airportId) throws DataAccessException {
		Airport airport = runwayRepository.findAirportById(airportId);
		return airport;
	}
	
	public void deleteRunwayById(Integer runwayId) throws DataAccessException {
		runwayRepository.deleteById(runwayId);
	}

	@Transactional(readOnly = true)
	public List<Runway> findRunwaysByName(String runwayName) {
		
		return this.runwayRepository.findRunwaysByName(runwayName);
	}

}
