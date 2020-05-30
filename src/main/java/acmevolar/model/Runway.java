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

package acmevolar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@Table(name = "runway")
public class Runway extends BaseEntity {

	@NotEmpty
	@Column(name = "name")
	private String		name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "runway_type_id")
	private RunwayType	runwayType;

	//@NotNull
	@ManyToOne
	@JoinColumn(name = "airport_id")
	private Airport		airport;

	// DEPARTES FROM FLIGHT

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departes")
	private Set<Flight>	flightsDepartes;


	protected Set<Flight> getFlightsDepartesInternal() {
		if (this.flightsDepartes == null) {
			this.flightsDepartes = new HashSet<>();
		}
		return this.flightsDepartes;
	}

	protected void setFlightDepartesInternal(final Set<Flight> flights) {
		this.flightsDepartes = flights;
	}

	public List<Flight> getFlightsDepartes() {
		List<Flight> sortedFlights = new ArrayList<>(this.getFlightsDepartesInternal());
		PropertyComparator.sort(sortedFlights, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedFlights);
	}

	public void addFlightDepartes(final Flight flight) {
		this.getFlightsDepartesInternal().add(flight);
		flight.setDepartes(this);
	}

	// DEPARTES FROM FLIGHT

	public void setFlightsDepartes(final Set<Flight> flightsDepartes) {
		this.flightsDepartes = flightsDepartes;
	}

	public void setFlightsLands(final Set<Flight> flightsLands) {
		this.flightsLands = flightsLands;
	}


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "lands")
	private Set<Flight> flightsLands;


	protected Set<Flight> getFlightsLandsInternal() {
		if (this.flightsLands == null) {
			this.flightsLands = new HashSet<>();
		}
		return this.flightsLands;
	}

	protected void setFlightLandsInternal(final Set<Flight> flights) {
		this.flightsLands = flights;
	}

	public List<Flight> getFlightsLands() {
		List<Flight> sortedFlights = new ArrayList<>(this.getFlightsLandsInternal());
		PropertyComparator.sort(sortedFlights, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedFlights);
	}

	public void addFlightLands(final Flight flight) {
		this.getFlightsLandsInternal().add(flight);
		flight.setDepartes(this);
	}

	// GETTERS & SETTERS

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public RunwayType getRunwayType() {
		return this.runwayType;
	}

	public void setRunwayType(final RunwayType runwayType) {
		this.runwayType = runwayType;
	}

	public Airport getAirport() {
		return this.airport;
	}

	public void setAirport(final Airport airport) {
		this.airport = airport;
	}

	@Override
	public String toString() {
		return "Runway [name=" + this.name + ", type=" + this.runwayType + ", airport=" + this.airport + "]";
	}

}
