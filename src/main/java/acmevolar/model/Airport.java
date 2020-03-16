
package acmevolar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

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
	@Size(min = 3, max = 3)
	@Column(name = "code", unique = true)
	private String	code;

	@NotEmpty
	@Column(name = "city")
	private String	city;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "airport")
	private Set<Runway> runways;
	
	public Set<Runway> getRunwaysInternal() {
		if(this.runways==null) {
			this.runways = new HashSet<Runway>();
		}
		return this.runways;
	}

	public void setRunwaysInternal(Set<Runway> runways) {
		this.runways = runways;
	}
	
	public List<Runway> getRunways() {
		List<Runway> sortedRunways = new ArrayList<>(getRunwaysInternal());
		PropertyComparator.sort(sortedRunways, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedRunways);
	}
	
	public void addRunway(Runway runway) {
		getRunwaysInternal().add(runway);
		runway.setAirport(this);
	}


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

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public void setMaxNumberOfPlanes(final Integer maxNumberOfPlanes) {
		this.maxNumberOfPlanes = maxNumberOfPlanes;
	}

	public void setMaxNumberOfClients(final Integer maxNumberOfClients) {
		this.maxNumberOfClients = maxNumberOfClients;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setCity(final String city) {
		this.city = city;
	}

}
