package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Client;
import acmevolar.model.User;
import acmevolar.service.exceptions.BirthDateIsAfterCreationDateException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClientServiceTests {
	
	@Autowired
	protected ClientService clientService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected AuthoritiesService authoritiesService;
	
	@Test
	@Transactional
	void shouldFindClientWithCorrectId() {
		Client client1 = this.clientService.findClientById(1);
		assertThat(client1.getName()).isEqualTo("Sergio Pérez");
		assertThat(client1.getIdentification()).isEqualTo("53933261-P");
		assertThat(client1.getBirthDate()).isEqualTo(LocalDate.of(1994, 9, 7));
		assertThat(client1.getPhone()).isEqualTo("644584458");
		assertThat(client1.getEmail()).isEqualTo("checoperez@gmail.com");
		assertThat(client1.getCreationDate()).isEqualTo(LocalDate.of(2005, 9, 7));
		assertThat(client1.getUser().getUsername()).isEqualTo("client1");
	}
	
	@Test
	@Transactional
	public void shouldFindAllClients() {
		List<Client> clients = this.clientService.findClients().stream()
				.collect(Collectors.toList());
		assertThat(clients.size()).isGreaterThan(0);
	}
	
	@Test
	@Transactional
	public void shouldInsertClientIntoDatabaseAndGenerateId() throws DataAccessException, ConstraintViolationException, BirthDateIsAfterCreationDateException {
		User user = new User();
		user.setUsername("p1246");
		user.setPassword("pepitoERmeho");
		user.setEnabled(true);
		
		Client client = new Client();
		client.setName("Pepito Palotes");
		client.setBirthDate(LocalDate.of(1995, 10, 19));
		client.setCreationDate(LocalDate.of(2001, 10, 19));
		client.setEmail("pepitopalotes@gmail.com");
		client.setPhone("666333666");
		client.setUser(user);
		client.setIdentification("53933123X");
		this.clientService.saveClient(client);
		
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionCreatingClientWithIncorrectEmail() {
		
		User user = new User();
		user.setUsername("pepito1");
		user.setPassword("pepito1");
		user.setEnabled(true);
		
		Client client = new Client();
		client.setName("Pepito Palotes");
		client.setBirthDate(LocalDate.of(1995, 10, 19));
		client.setCreationDate(LocalDate.of(2019, 4, 3));
		client.setEmail("notaemail");
		client.setPhone("666333666");
		client.setUser(user);
		client.setIdentification("53933123X");
		
		assertThrows(ConstraintViolationException.class, () -> {
			this.clientService.saveClient(client);
		});		
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionCreatingClientWithIncorrectPhone() {
		
		User user = new User();
		user.setUsername("pepito1");
		user.setPassword("pepito1");
		user.setEnabled(true);
		
		Client client = new Client();
		client.setName("Pepito Palotes");
		client.setBirthDate(LocalDate.of(1995, 10, 19));
		client.setCreationDate(LocalDate.of(2019, 4, 3));
		client.setEmail("pepitopalotes@gmail.com");
		client.setPhone("notanumber");
		client.setUser(user);
		client.setIdentification("53933123X");
		
		assertThrows(ConstraintViolationException.class, () -> {
			this.clientService.saveClient(client);
		});		
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionCreatingClientWithFutureBirthDate() {
		
		User user = new User();
		user.setUsername("pepito1");
		user.setPassword("pepito1");
		user.setEnabled(true);
		
		Client client = new Client();
		client.setName("Pepito Palotes");
		client.setBirthDate(LocalDate.of(2021, 10, 19));
		client.setCreationDate(LocalDate.of(2019, 4, 3));
		client.setEmail("pepitopalotes@gmail.com");
		client.setPhone("666999666");
		client.setUser(user);
		client.setIdentification("53933123X");
		
		assertThrows(BirthDateIsAfterCreationDateException.class, () -> {
			this.clientService.saveClient(client);
		});		
	}
		
}
