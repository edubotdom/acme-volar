
package acmevolar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "airports")
public class Airport extends NamedEntity {

	@NotEmpty
	@Column(name = "name")
	private String	name;

	@NotNull
	@Column(name = "max_number_of_planes")
	private Integer	maxNumberOfPlanes;

	@NotNull
	@Column(name = "max_number_of_clients")
	private Integer	maxNumberOfClients;

	@NotNull
	@Column(name = "latitude")
	private Double	latitude;

	@NotNull
	@Column(name = "longitude")
	private Double	longitude;

	@NotEmpty
	@Column(name = "code")
	private String	code;

	@NotEmpty
	@Column(name = "city")
	private String	city;


	@Override
	public String getName() {
		return this.name;
	}

	public Integer getMaxNumberOfPlanes() {
		return this.maxNumberOfPlanes;
	}

	public Integer getMaxNumberOfClients() {
		return this.maxNumberOfClients;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public String getCode() {
		return this.code;
	}

	public String getCity() {
		return this.city;
	}

}
