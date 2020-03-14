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

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
	private Runway				departes;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "lands_id")
	private Runway				lands;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "airline_id")
	private Airline				airline;

	@NotNull
	@Column(name = "land_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime landDate;
	
	@NotNull
	@Column(name = "depart_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime departDate;

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

	public Runway getDepartes() {
		return departes;
	}

	public void setDepartes(Runway departes) {
		this.departes = departes;
	}

	public Runway getLands() {
		return lands;
	}

	public void setLands(Runway lands) {
		this.lands = lands;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public LocalDateTime getLandDate() {
		return landDate;
	}

	public void setLandDate(LocalDateTime landDate) {
		this.landDate = landDate;
	}

	public LocalDateTime getDepartDate() {
		return departDate;
	}

	public void setDepartDate(LocalDateTime departDate) {
		this.departDate = departDate;
	}

	@Override
	public String toString() {
		return "Flight [reference=" + reference + ", seats=" + seats + ", price=" + price + ", flightStatus="
				+ flightStatus + ", plane=" + plane + ", published=" + published + ", departes=" + departes + ", lands="
				+ lands + ", airline=" + airline + ", landDate=" + landDate + ", departDate=" + departDate + "]";
	}

}
