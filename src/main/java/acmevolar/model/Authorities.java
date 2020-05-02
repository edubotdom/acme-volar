
package acmevolar.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {

	@Id
	@Length(max = 100)
	String	username;
	String	authority;


	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}
	public String getAuthority() {
		return this.authority;
	}
	public void setAuthority(final String authority) {
		this.authority = authority;
	}

}
