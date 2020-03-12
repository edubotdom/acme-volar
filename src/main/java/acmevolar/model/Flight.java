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
	private String reference;
	
	@NotNull
	@Column(name = "seats")
	private Integer seats;
	
	@NotNull
	@Column(name = "price")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "flight_status_id")
	private FlightStatusType flightStatus;
	
	@ManyToOne
	@JoinColumn(name = "plane")
	private Plane plane;

	@NotNull
	@Column(name = "published")
	private Boolean published;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "departes")
	private Runaway departes;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "lands")
	private Runaway lands;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public FlightStatusType getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(FlightStatusType flightStatus) {
		this.flightStatus = flightStatus;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Runaway getDepartes() {
		return departes;
	}

	public void setDepartes(Runaway departes) {
		this.departes = departes;
	}

	public Runaway getLands() {
		return lands;
	}

	public void setLands(Runaway lands) {
		this.lands = lands;
	}

	@Override
	public String toString() {
		return "Flight [reference=" + reference + ", seats=" + seats + ", price=" + price + ", flightStatus="
				+ flightStatus + ", plane=" + plane + ", published=" + published + ", departes=" + departes + ", lands="
				+ lands + "]";
	}	
	
	
/*	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	protected void setOwner(Owner owner) {
		this.owner = owner;
	}
	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}
	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}
	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}
	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);
		visit.setPet(this);
	}
*/
}