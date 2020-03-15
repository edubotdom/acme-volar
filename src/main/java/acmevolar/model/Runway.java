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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "runway")
public class Runway extends BaseEntity {

	@NotEmpty
	@Column(name = "name")
	private String		name;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type")
	private RunwayType	type;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "airport_id")
	private Airport		airport;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public RunwayType getType() {
		return this.type;
	}

	public void setType(final RunwayType type) {
		this.type = type;
	}

	public Airport getAirport() {
		return this.airport;
	}

	public void setAirport(final Airport airport) {
		this.airport = airport;
	}

	@Override
	public String toString() {
		return "Runway [name=" + this.name + ", type=" + this.type + ", airport=" + this.airport + "]";
	}

}
