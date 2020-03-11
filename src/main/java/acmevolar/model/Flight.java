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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

	@NotEmpty
	@Column(name = "reference")
	private String				reference;

	@NotNull
	@Column(name = "seats")
	private Integer				seats;

	@NotNull
	@Column(name = "price")
	private Double				price;

	@ManyToOne
	@JoinColumn(name = "flight_status_id")
	private FlightStatusType	flightStatus;

	@NotNull
	@Column(name = "published")
	private Boolean				published;

	@ManyToOne
	@JoinColumn(name = "airline_id")
	private Airline				airline;


	public Airline getAirline() {
		return this.airline;
	}

	public void setAirline(final Airline airline) {
		this.airline = airline;
	}

	public String getReference() {
		return this.reference;
	}

	public Integer getSeats() {
		return this.seats;
	}

	public Double getPrice() {
		return this.price;
	}

	public FlightStatusType getFlightStatus() {
		return this.flightStatus;
	}

	public Boolean getPublished() {
		return this.published;
	}

	/*
	 * 
	 * public void setBirthDate(LocalDate birthDate) {
	 * this.birthDate = birthDate;
	 * }
	 * 
	 * protected void setOwner(Owner owner) {
	 * this.owner = owner;
	 * }
	 * 
	 * protected Set<Visit> getVisitsInternal() {
	 * if (this.visits == null) {
	 * this.visits = new HashSet<>();
	 * }
	 * return this.visits;
	 * }
	 * 
	 * protected void setVisitsInternal(Set<Visit> visits) {
	 * this.visits = visits;
	 * }
	 * 
	 * public List<Visit> getVisits() {
	 * List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
	 * PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
	 * return Collections.unmodifiableList(sortedVisits);
	 * }
	 * 
	 * public void addVisit(Visit visit) {
	 * getVisitsInternal().add(visit);
	 * visit.setPet(this);
	 * }
	 */
}
