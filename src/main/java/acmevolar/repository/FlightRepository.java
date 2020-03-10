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
package acmevolar.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Vet;

public interface FlightRepository {

	/**
	 * Retrieve all <code>PetType</code>s from the data store.
	 * @return a <code>Collection</code> of <code>PetType</code>s
	 */
	List<FlightStatusType> findFlightStatusTypes() throws DataAccessException;

	/**
	 * Retrieve a <code>Pet</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Flight</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Flight findById(int id) throws DataAccessException;

	/**
	 * Save a <code>Flight</code> to the data store, either inserting or updating it.
	 * @param flight the <code>Flight</code> to save
	 */
	void save(Flight flight) throws DataAccessException;

	/**
	 * Retrieve all <code>Flight</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Flight</code>s
	 */
	Collection<Flight> findAll() throws DataAccessException;

	
}
