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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author edubotdom
 * @author juanogtir
 */
@Entity
@Table(name = "airlines")
public class Airline extends NamedEntity {

	@Column(name = "identification")
	@NotEmpty
	private String		identification;

	@Column(name = "country")
	@NotEmpty
	private String		country;

	@Column(name = "phone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String		phone;

	@Column(name = "email")
	@NotEmpty
	private String		email;

	@Column(name = "creation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	creationDate;

	@Column(name = "reference")
	@NotEmpty
	private String		reference;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "airline")
	private Set<Plane> planes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "airline")
	private Set<Flight> flights;

	public Set<Plane> getPlanesInternal() {
		if(this.planes==null) {
			this.planes = new HashSet<Plane>();
		}
		return this.planes;
	}

	public void setPlanesInternal(Set<Plane> planes) {
		this.planes = planes;
	}
	
	public List<Plane> getPlanes() {
		List<Plane> sortedPlanes = new ArrayList<>(getPlanesInternal());
		PropertyComparator.sort(sortedPlanes, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPlanes);
	}
	
	public void addPlane(Plane plane) {
		getPlanesInternal().add(plane);
		plane.setAirline(this);
	}
	
	public Set<Flight> getFlightsInternal() {
		if(this.flights==null) {
			this.flights = new HashSet<>();
		}
		return this.flights;
	}

	public void setFlightsInternal(Set<Flight> flights) {
		this.flights = flights;
	}
	
	public List<Flight> getFlights() {
		List<Flight> sortedFlights = new ArrayList<>(getFlightsInternal());
		PropertyComparator.sort(sortedFlights, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedFlights);
	}
	
	public void addFlight(Flight flight) {
		getFlightsInternal().add(flight);
		flight.setAirline(this);
	}

	public String getIdentification() {
		return this.identification;
	}

	public void setIdentification(final String identification) {
		this.identification = identification;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(final String reference) {
		this.reference = reference;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}


	//
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	//

}
