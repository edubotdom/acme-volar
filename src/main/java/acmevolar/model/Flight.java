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

	@ManyToOne
	@JoinColumn(name = "plane_id")
	private Plane				plane;

	@NotNull
	@Column(name = "published")
	private Boolean				published;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "departes_id")
	private Runaway				departes;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "lands_id")
	private Runaway				lands;

	@ManyToOne
	@JoinColumn(name = "airline_id")
	private Airline				airline;


	public Airline getAirline() {
		return this.airline;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(final String reference) {
		this.reference = reference;
	}

	public Integer getSeats() {
		return this.seats;
	}

	public void setSeats(final Integer seats) {
		this.seats = seats;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public FlightStatusType getFlightStatus() {
		return this.flightStatus;
	}

	public void setFlightStatus(final FlightStatusType flightStatus) {
		this.flightStatus = flightStatus;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public void setPlane(final Plane plane) {
		this.plane = plane;
	}

	public Boolean getPublished() {
		return this.published;
	}

	public void setPublished(final Boolean published) {
		this.published = published;
	}

	public Runaway getDepartes() {
		return this.departes;
	}

	public void setDepartes(final Runaway departes) {
		this.departes = departes;
	}

	public Runaway getLands() {
		return this.lands;
	}

	public void setLands(final Runaway lands) {
		this.lands = lands;
	}

	public void setAirline(final Airline airline) {
		this.airline = airline;
	}

	@Override
	public String toString() {
		return "Flight [reference=" + this.reference + ", seats=" + this.seats + ", price=" + this.price + ", flightStatus=" + this.flightStatus + ", plane=" + this.plane + ", published=" + this.published + ", departes=" + this.departes + ", lands="
			+ this.lands + "]";
	}

	/*
	 * public void setBirthDate(LocalDate birthDate) {
	 * this.birthDate = birthDate;
	 * }
	 * protected void setOwner(Owner owner) {
	 * this.owner = owner;
	 * }
	 * protected Set<Visit> getVisitsInternal() {
	 * if (this.visits == null) {
	 * this.visits = new HashSet<>();
	 * }
	 * return this.visits;
	 * }
	 * protected void setVisitsInternal(Set<Visit> visits) {
	 * this.visits = visits;
	 * }
	 * public List<Visit> getVisits() {
	 * List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
	 * PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
	 * return Collections.unmodifiableList(sortedVisits);
	 * }
	 * public void addVisit(Visit visit) {
	 * getVisitsInternal().add(visit);
	 * visit.setPet(this);
	 * }
	 */
}
