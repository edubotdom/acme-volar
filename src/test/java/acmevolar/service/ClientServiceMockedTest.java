package acmevolar.service;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import acmevolar.model.Client;
import acmevolar.model.User;
import acmevolar.repository.ClientRepository;
import acmevolar.service.exceptions.BirthDateIsAfterCreationDateException;

@ExtendWith(MockitoExtension.class)
public class ClientServiceMockedTest {
	
	private static final int	TEST_CLIENT_ID	= 1;

	@Mock
	private ClientRepository clientRepository;
	
	@InjectMocks
	private ClientService clientService;
	
	private Client client;
	
	private Client clientIncorrectEmail;
	
	private Client clientIncorrectPhone;
	
	private Client clientBirthDateFuture;
	
	@Before
	void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		// CLIENTE 1
		
		User user1 = new User();
		user1.setUsername("client1");
		user1.setPassword("client1");
		user1.setEnabled(true);
		
		client = new Client();
		client.setName("Pepito Grilletes");
		client.setBirthDate(LocalDate.of(1962, 2, 21));
		client.setIdentification("53948661-G");
		client.setPhone("625310586");
		client.setEmail("pepitogrilletes@pepito.com");
		client.setCreationDate(LocalDate.of(2020, 1, 2));
		client.setUser(user1);
		try {
			clientService.saveClient(client);
		} catch (DataAccessException | ConstraintViolationException | BirthDateIsAfterCreationDateException e) {
			e.printStackTrace();
		}
		
		
		// CLIENTE 2
		
		User user2 = new User();
		user1.setUsername("client1");
		user1.setPassword("client1");
		user1.setEnabled(true);
		
		Client client2 = new Client();
		client2.setName("Pepito Oleijo");
		client2.setBirthDate(LocalDate.of(1972, 2, 21));
		client2.setIdentification("53945664-G");
		client2.setPhone("625310586");
		client2.setEmail("pepitooleijo@pepito.com");
		client2.setCreationDate(LocalDate.of(2020, 1, 3));
		client2.setUser(user1);
		
		
		// CLIENTE CON NACIMIENTO FUTURO
		
	}
	
	@Test
	void shouldFindClientWithCorrectId() throws DataAccessException, ConstraintViolationException, BirthDateIsAfterCreationDateException {
		clientService.saveClient(client);
		Mockito.verify(clientRepository, Mockito.times(1)).save(client);
	}
	
	public void shouldFindAllClients() {
		
	}
	
	public void shouldInsertClientIntoDatabaseAndGenerateId() {
		
	}
	
	public void shouldThrowExceptionCreatingClientWithIncorrectEmail() {
		
	}
	
	public void shouldThrowExceptionCreatingClientWithIncorrectPhone() {
		
	}
	
	public void shouldThrowExceptionCreatingClientWithFutureBirthDate() {
		
	}
	
}
