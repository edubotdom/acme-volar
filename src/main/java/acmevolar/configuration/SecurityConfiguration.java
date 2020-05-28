
package acmevolar.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource		dataSource;

	private String	airlineString	= "airline";
	private String	clientString	= "client";


	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()

			.antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll().antMatchers(HttpMethod.GET, "/", "/oups").permitAll().antMatchers("/users/new").permitAll()
			/* Clients */
			.antMatchers("/clients").hasAuthority(this.airlineString).antMatchers("/clients/new").anonymous().antMatchers("/clients/{^[\\d]$}").hasAnyAuthority(this.airlineString)
			/* Airlines */
			.antMatchers("/airlines").permitAll().antMatchers("/airlines/{^[\\d]$}").permitAll().antMatchers("/airlines/new/").anonymous()
			/* Airports */
			.antMatchers("/airports").hasAnyAuthority(this.airlineString, this.clientString).antMatchers("/airports/{^[\\d]$}").hasAnyAuthority(this.airlineString, this.clientString).antMatchers("/airports/new").hasAuthority(this.airlineString)
			.antMatchers("/airports/{^[\\d]$}/edit").hasAuthority(this.airlineString).antMatchers("/airports/{^[\\d]$}/delete").hasAuthority(this.airlineString)
			/* Runways */
			.antMatchers("/airports/{^[\\d]$}/runways").hasAnyAuthority(this.airlineString, this.clientString).antMatchers("/airports/{^[\\d]$}/runways/new").hasAuthority(this.airlineString).antMatchers("/airports/{^[\\d]$}/runways/{^[\\d]$}/edit")
			.hasAuthority(this.airlineString).antMatchers("/airports/{^[\\d]$}/runways/{^[\\d]$}/delete").hasAuthority(this.airlineString)
			/* Flights */
			.antMatchers("/flights").permitAll().antMatchers("/my_flights").hasAuthority(this.airlineString).antMatchers("/flights/{^[\\d]$}").permitAll().antMatchers("/flights/new").hasAuthority(this.airlineString).antMatchers("/flights/{^[\\d]$}/edit")
			.hasAuthority(this.airlineString).antMatchers("/flights/{^[\\d]$}/delete").hasAuthority(this.airlineString)
			/* Planes */
			.antMatchers("/my_planes").hasAuthority(this.airlineString).antMatchers("/planes/new").hasAuthority(this.airlineString).antMatchers("/planes/{^[\\d]$}").hasAnyAuthority(this.clientString, this.airlineString)
			.antMatchers("/planes/{^[\\d]$}/edit").hasAuthority(this.airlineString)
			/* Books */
			.antMatchers("/books/{^[\\\\d]$}/new").hasAuthority(this.clientString).antMatchers("/books/{^[\\\\d]$}").hasAnyAuthority(this.clientString, this.airlineString).antMatchers("/books/{^[\\\\d]$}/edit").hasAnyAuthority(this.airlineString)

			.antMatchers("/admin/**").hasAnyAuthority("admin").antMatchers("/owners/**").hasAnyAuthority("owner", "admin").antMatchers("/vets/**").authenticated().anyRequest().denyAll().and().formLogin()
			/* .loginPage("/login") */
			.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();

	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource).usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
			.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?").passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		return encoder;
	}

}
