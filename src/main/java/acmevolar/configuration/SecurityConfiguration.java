
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
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;


	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()

		.antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
		.antMatchers("/users/new").permitAll()
		
		.antMatchers("/clients").hasAuthority("airline")
		.antMatchers("/clients/new").anonymous()
		.antMatchers("/clients/**/").hasAnyAuthority("airline")
		
		.antMatchers("/airlines").permitAll()
		.antMatchers("/airlines/**/").permitAll()
		.antMatchers("/airlines/new/").hasAuthority("airline")
		
		.antMatchers("/airports/**/runways/").permitAll()
		.antMatchers("/airports").permitAll()
		.antMatchers("/airports/**/").permitAll()
		.antMatchers("/airports/new").hasAuthority("airline")
		.antMatchers("/airports/**/edit").hasAuthority("airline")
		.antMatchers("/airports/**/delete").hasAuthority("airline")
		
		.antMatchers("/flights").permitAll()
		.antMatchers("/my_flights").hasAuthority("airline")
		.antMatchers("/flights/**/").permitAll()
		.antMatchers("/flights/new").hasAuthority("airline")
		.antMatchers("/flights/**/edit").hasAuthority("airline")
		.antMatchers("/flights/**/delete").hasAuthority("airline")
		
		.antMatchers("/my_planes").hasAuthority("airline")
		.antMatchers("/planes/**/").hasAnyAuthority("client","airline")
		.antMatchers("/planes/new").hasAuthority("airline")
		.antMatchers("/planes/**/edit").hasAuthority("airline")
		.antMatchers("/planes/**/delete").hasAuthority("airline")
		
		.antMatchers("/admin/**").hasAnyAuthority("admin")
		.antMatchers("/owners/**").hasAnyAuthority("owner", "admin")
		.antMatchers("/vets/**").authenticated().anyRequest().denyAll().and().formLogin()
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
