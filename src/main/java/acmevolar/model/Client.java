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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author edubotdom
 * @author juanogtir
 */
@Entity
@Table(name = "clients")
public class Client extends NamedEntity {

	@Column(name = "identification")
	@NotEmpty
	private String		identification;

	@Column(name = "birth_date")
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	birthDate;

	@Column(name = "phone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String		phone;

	@Column(name = "email")
	@Email
	@NotEmpty
	private String		email;

	@Column(name = "creation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	creationDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User		user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
	private Set<Book>	books;


	public Set<Book> getBooksInternal() {
		if (this.books == null) {
			this.books = new HashSet<>();
		}
		return this.books;
	}

	public void setBooksInternal(final Set<Book> books) {
		this.books = books;
	}

	public List<Book> getBooks() {
		List<Book> sortedBooks = new ArrayList<>(this.getBooksInternal());
		PropertyComparator.sort(sortedBooks, new MutableSortDefinition("id", true, true));
		return Collections.unmodifiableList(sortedBooks);
	}

	public void addBook(final Book book) {
		this.getBooksInternal().add(book);
		book.setClient(this);
	}

	public void setIdentification(final String identification) {
		this.identification = identification;
	}

	public void setBirthDate(final LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public String getIdentification() {
		return this.identification;
	}

	public void setCreationDate(final LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public User getUser() {
		return this.user;
	}

}
