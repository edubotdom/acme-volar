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

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

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
	private String identification;

	@Column(name = "birth_date")  
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;
	
	@Column(name = "phone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String phone;
	
	@Column(name = "email")
	@Email
	@NotEmpty
	private String email;
	
	@Column(name = "creation_date")  
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate creationDate;
	
	//
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	//

	public void setIdentification(String identification) {
		this.identification = identification;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	public String getIdentification() {
		return identification;
	}


	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}


	public String getPhone() {
		return phone;
	}


	public String getEmail() {
		return email;
	}


	public LocalDate getCreationDate() {
		return creationDate;
	}


	public User getUser() {
		return user;
	}
	

}
