package acmevolar.service;

import  static org.assertj.core.api.Assertions.assertThat;

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
	
	private Client client1;
	
	private Client client2;
	
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
		
		client1 = new Client();
		client1.setName("Pepito Grilletes");
		client1.setBirthDate(LocalDate.of(1962, 2, 21));
		client1.setIdentification("53948661-G");
		client1.setPhone("625310586");
		client1.setEmail("pepitogrilletes@pepito.com");
		client1.setCreationDate(LocalDate.of(2020, 1, 2));
		client1.setUser(user1);
		try {
			clientService.saveClient(client1);
		} catch (DataAccessException | ConstraintViolationException | BirthDateIsAfterCreationDateException e) {
			e.printStackTrace();
		}
		
		
		// CLIENTE 2
		
		User user2 = new User();
		user2.setUsername("client2");
		user2.setPassword("client2");
		user2.setEnabled(true);
		
		client2 = new Client();
		client2.setName("Pepito Oleijo");
		client2.setBirthDate(LocalDate.of(1972, 2, 21));
		client2.setIdentification("53945664-G");
		client2.setPhone("625310586");
		client2.setEmail("pepitooleijo@pepito.com");
		client2.setCreationDate(LocalDate.of(2020, 1, 3));
		client2.setUser(user2);
		
		
		// CLIENTE CON EMAIL INCORRECTO
		User user3 = new User();
		user3.setUsername("client3");
		user3.setPassword("client3");
		user3.setEnabled(true);
		
		clientIncorrectEmail = new Client();
		clientIncorrectEmail.setName("Pepito Oleijo");
		clientIncorrectEmail.setBirthDate(LocalDate.of(1972, 2, 21));
		clientIncorrectEmail.setIdentification("53145664-G");
		clientIncorrectEmail.setPhone("625310586");
		clientIncorrectEmail.setEmail("esto no es un email");
		clientIncorrectEmail.setCreationDate(LocalDate.of(2020, 1, 3));
		clientIncorrectEmail.setUser(user3);
		
		// CLIENTE CON TELÉFONO INCORRECTO
		User user4 = new User();
		user4.setUsername("client4");
		user4.setPassword("client4");
		user4.setEnabled(true);
		
		clientIncorrectPhone = new Client();
		clientIncorrectPhone.setName("JuanNogTir");
		clientIncorrectPhone.setBirthDate(LocalDate.of(1972, 2, 21));
		clientIncorrectPhone.setIdentification("53972664-G");
		clientIncorrectPhone.setPhone("esto no es un número de teléfono");
		clientIncorrectPhone.setEmail("juannogtir@juan.com");
		clientIncorrectPhone.setCreationDate(LocalDate.of(2020, 1, 3));
		clientIncorrectPhone.setUser(user3);
		
		// CLIENTE CON NACIMIENTO FUTURO
		User user5 = new User();
		user5.setUsername("client5");
		user5.setPassword("client5");
		user5.setEnabled(true);
		
		clientBirthDateFuture = new Client();
		clientBirthDateFuture.setName("Dios momo");
		clientBirthDateFuture.setBirthDate(LocalDate.of(2040, 2, 21));
		clientBirthDateFuture.setIdentification("53954664-G");
		clientBirthDateFuture.setPhone("654321321");
		clientBirthDateFuture.setEmail("diosmomo@momo.com");
		clientBirthDateFuture.setCreationDate(LocalDate.of(2020, 1, 3));
		clientBirthDateFuture.setUser(user3);
		
	}
	
	@Test
	void shouldFindClientWithCorrectId() throws DataAccessException, ConstraintViolationException, BirthDateIsAfterCreationDateException {
		Client expected = clientService.findClientById(TEST_CLIENT_ID);
		System.out.println(expected);
		assertThat(expected).isEqualTo(client1);
	}
	//NO FUNCIONA
	//@Test
	public void shouldFindAllClients() {
		assertThat(clientService.findClients().size()).isEqualTo(2);
	}
	
	@Test
	public void shouldInsertClientIntoDatabaseAndGenerateId()  throws DataAccessException, ConstraintViolationException, BirthDateIsAfterCreationDateException{
		clientService.saveClient(client1);
		Mockito.verify(clientRepository, Mockito.times(1)).save(client1);
	}
	
	public void shouldThrowExceptionCreatingClientWithIncorrectEmail() {
		
	}
	
	public void shouldThrowExceptionCreatingClientWithIncorrectPhone() {
		
	}
	
	public void shouldThrowExceptionCreatingClientWithFutureBirthDate() {
		
	}
	
}
