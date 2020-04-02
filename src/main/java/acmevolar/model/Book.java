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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author jossanrui5
 * @author josmarsan24
 */
@Entity
@Table(name = "books")
public class Book extends NamedEntity {

	@Column(name = "quantity")
	@Min(value = 0)
	@NotNull
	private Integer			quantity;

	@Column(name = "price")
	@Min(value = 0)
	@NotNull
	private Double			price;

	@Past
	@Column(name = "moment", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate		moment;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "book_status_type_id")
	private BookStatusType	bookStatusType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "flight_id")
	private Flight			flight;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client			client;


	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(final Flight flight) {
		this.flight = flight;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public LocalDate getMoment() {
		return this.moment;
	}

	public void setMoment(final LocalDate moment) {
		this.moment = moment;
	}

	public BookStatusType getBookStatusType() {
		return this.bookStatusType;
	}

	public void setBookStatusType(final BookStatusType bookStatusType) {
		this.bookStatusType = bookStatusType;
	}

}
