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

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "planes")
public class Runaway extends BaseEntity {

	@NotEmpty
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "date")
	private Date date;

	@NotNull
	@Enumerated(value=EnumType.STRING)
	@Column(name = "type")
	private RunwayType type;
	
//	@ManyToOne
//	@NotNull
//	@Column(name = "airport")
//	private Airport airport;
	
}
