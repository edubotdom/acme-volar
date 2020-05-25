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
import java.util.Date;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "aeroplanes")
public class Plane extends BaseEntity {

	@NotEmpty
	@Column(name = "reference", unique = true)
	private String		reference;

	@Min(value = 0)
	@NotNull
	@Column(name = "max_seats")
	private Integer		maxSeats;

	@NotEmpty
	@Column(name = "description")
	private String		description;

	@NotEmpty
	@Column(name = "manufacter")
	private String		manufacter;

	@NotEmpty
	@Column(name = "model")
	private String		model;

	@Min(value = 0)
	@NotNull
	@Column(name = "number_of_km")
	private Double		numberOfKm;

	@Min(value = 0)
	@NotNull
	@Column(name = "max_distance")
	private Double		maxDistance;

	@Past
	@Column(name = "last_maintenance", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date		lastMaintenance;

	@ManyToOne
	@JoinColumn(name = "airline_id")
	private Airline		airline;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "plane")
	private Set<Flight>	flights;


	public String getReference() {
		return this.reference;
	}

	public Set<Flight> getFlightsInternal() {
		if (this.flights == null) {
			this.flights = new HashSet<>();
		}
		return this.flights;
	}

	public void setFlightsInternal(final Set<Flight> flights) {
		this.flights = flights;
	}

	public List<Flight> getFlights() {
		List<Flight> sortedFlights = new ArrayList<>(this.getFlightsInternal());
		PropertyComparator.sort(sortedFlights, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedFlights);
	}

	public void addFlight(final Flight flight) {
		this.getFlightsInternal().add(flight);
		flight.setPlane(this);
	}

	public void setReference(final String reference) {
		this.reference = reference;
	}

	public Integer getMaxSeats() {
		return this.maxSeats;
	}

	public void setMaxSeats(final Integer maxSeats) {
		this.maxSeats = maxSeats;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getManufacter() {
		return this.manufacter;
	}

	public void setManufacter(final String manufacter) {
		this.manufacter = manufacter;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public Double getNumberOfKm() {
		return this.numberOfKm;
	}

	public void setNumberOfKm(final Double numberOfKm) {
		this.numberOfKm = numberOfKm;
	}

	public Double getMaxDistance() {
		return this.maxDistance;
	}

	public void setMaxDistance(final Double maxDistance) {
		this.maxDistance = maxDistance;
	}

	public Date getLastMaintenance() {
		return this.lastMaintenance;
	}

	public void setLastMaintenance(final Date lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}

	public Airline getAirline() {
		return this.airline;
	}

	public void setAirline(final Airline airline) {
		this.airline = airline;
	}
	
	

}
