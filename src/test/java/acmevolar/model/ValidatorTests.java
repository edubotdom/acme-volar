
package acmevolar.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 *         when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldValidateBook() throws ParseException {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Book book = new Book();
		
		BookStatusType bst1 = new BookStatusType();
		bst1.setId(1);
		bst1.setName("approved");
		BookStatusType bst2 = new BookStatusType();
		bst2.setId(1);
		bst2.setName("cancelled");
		
		Client client = new Client();
		client.setId(1);
		client.setIdentification("identification");//
		client.setBirthDate(LocalDate.of(1990, 12, 12));
		client.setPhone("987456321");//
		client.setEmail("email@email.com");//
		client.setCreationDate(LocalDate.of(2010, 12, 12));
	
		String stringDate1 = "31/12/2020";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate1);
		String stringDate2 = "31/12/2020";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate2);
		Flight flight1 = new Flight();
		flight1.setId(1);
		flight1.setAirline(new Airline());
		flight1.setDepartDate(date1);
		flight1.setDepartes(new Runway());
		flight1.setFlightStatus(new FlightStatusType());
		flight1.setLandDate(date2);
		flight1.setLands(new Runway());
		flight1.setPlane(new Plane());
		flight1.setPrice(500.);
		flight1.setPublished(true);
		flight1.setReference("REF78");
		flight1.setSeats(255);
		
		book.setBookStatusType(bst1);
		book.setClient(client);
		book.setFlight(flight1);
		book.setId(1);
		book.setMoment(LocalDate.now());
		book.setPrice(200.);
		book.setQuantity(2);
		
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
		
	}
	
	@Test
	void shouldNotValidateBook() throws ParseException {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Book book = new Book();
		
		BookStatusType bst1 = new BookStatusType();
		bst1.setId(1);
		bst1.setName("approved");
		BookStatusType bst2 = new BookStatusType();
		bst2.setId(1);
		bst2.setName("cancelled");
		
		Client client = new Client();
		client.setId(1);
		client.setIdentification("identification");//
		client.setBirthDate(LocalDate.of(1990, 12, 12));
		client.setPhone("987456321");//
		client.setEmail("email@email.com");//
		client.setCreationDate(LocalDate.of(2010, 12, 12));
	
		String stringDate1 = "31/12/2020";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate1);
		String stringDate2 = "31/12/2020";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate2);
		Flight flight1 = new Flight();
		flight1.setId(1);
		flight1.setAirline(new Airline());
		flight1.setDepartDate(date1);
		flight1.setDepartes(new Runway());
		flight1.setFlightStatus(new FlightStatusType());
		flight1.setLandDate(date2);
		flight1.setLands(new Runway());
		flight1.setPlane(new Plane());
		flight1.setPrice(500.);
		flight1.setPublished(true);
		flight1.setReference("REF78");
		flight1.setSeats(255);
		
		book.setBookStatusType(bst1);
		book.setClient(client);
		book.setFlight(flight1);
		book.setId(1);
		book.setMoment(LocalDate.now());
		book.setPrice(-200.);
		book.setQuantity(-2);
		
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
		
		Comparator<ConstraintViolation<Book>> comparator = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Book>> comparator2 = Comparator.comparing(c->c.getMessage().toString());
		List<ConstraintViolation<Book>> cva = constraintViolations.stream().sorted(comparator.thenComparing(comparator2)).collect(Collectors.toList());

		
		ConstraintViolation<Book> violation1 = cva.get(0);
		Assertions.assertThat(violation1.getPropertyPath().toString()).isEqualTo("price");
		Assertions.assertThat(violation1.getMessage()).isEqualTo("must be greater than or equal to 0");
		
		ConstraintViolation<Book> violation2 = cva.get(1);
		Assertions.assertThat(violation2.getPropertyPath().toString()).isEqualTo("quantity");
		Assertions.assertThat(violation2.getMessage()).isEqualTo("must be greater than or equal to 1");

		
	}
	
	@Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Person person = new Person();
		person.setFirstName("");
		person.setLastName("smith");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	/**
	 *
	 * @author edubotdom
	 * @author juanogtir
	 */
	@Test
	void shouldNotValidateAirline() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Airline airline = new Airline();
		airline.setCountry("");
		airline.setCreationDate(LocalDate.now().plusDays(1));
		airline.setEmail("");
		airline.setFlightsInternal(new HashSet<Flight>());
		airline.setId(1);
		airline.setIdentification("");
		airline.setName("");
		airline.setPhone("");
		airline.setPlanesInternal(new HashSet<Plane>());
		airline.setReference("");
		airline.setUser(new User());

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Airline>> constraintViolations = validator.validate(airline);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(7);

		Comparator<ConstraintViolation<Airline>> comparator = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Airline>> comparator2 = Comparator.comparing(c->c.getMessage().toString());
		List<ConstraintViolation<Airline>> cva = constraintViolations.stream().sorted(comparator.thenComparing(comparator2)).collect(Collectors.toList());

		
		ConstraintViolation<Airline> violation1 = cva.get(0);
		Assertions.assertThat(violation1.getPropertyPath().toString()).isEqualTo("country");
		Assertions.assertThat(violation1.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Airline> violation2 = cva.get(1);
		Assertions.assertThat(violation2.getPropertyPath().toString()).isEqualTo("email");
		Assertions.assertThat(violation2.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Airline> violation3 = cva.get(2);
		Assertions.assertThat(violation3.getPropertyPath().toString()).isEqualTo("identification");
		Assertions.assertThat(violation3.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Airline> violation4 = cva.get(3);
		Assertions.assertThat(violation4.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation4.getMessage()).isEqualTo("size must be between 3 and 50");

		ConstraintViolation<Airline> violation5 = cva.get(4);
		Assertions.assertThat(violation5.getPropertyPath().toString()).isEqualTo("phone");
		Assertions.assertThat(violation5.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Airline> violation6 = cva.get(5);
		Assertions.assertThat(violation6.getPropertyPath().toString()).isEqualTo("phone");
		Assertions.assertThat(violation6.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");

		ConstraintViolation<Airline> violation7 = cva.get(6);
		Assertions.assertThat(violation7.getPropertyPath().toString()).isEqualTo("reference");
		Assertions.assertThat(violation7.getMessage()).isEqualTo("must not be empty");
		/*
		 * ConstraintViolation<Airline> violation = constraintViolations.iterator().next();
		 * Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		 * Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
		 */
	}

	/**
	 *
	 * @author edubotdom
	 * @author juanogtir
	 */
	@Test
	void shouldNotValidatePlane() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Plane plane = new Plane();
		plane.setAirline(new Airline());
		plane.setDescription("");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setId(1);
		plane.setLastMaintenance(java.util.Date.from(Instant.parse("2031-04-17T00:00:00.00Z")));
		plane.setMaxSeats(-150);
		plane.setManufacter("");
		plane.setModel("");
		plane.setNumberOfKm(-100.);
		plane.setMaxDistance(-200.);
		plane.setReference("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Plane>> constraintViolations = validator.validate(plane);

		Comparator<ConstraintViolation<Plane>> comparator = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Plane>> comparator2 = Comparator.comparing(c->c.getMessage().toString());
		List<ConstraintViolation<Plane>> cvp = constraintViolations.stream().sorted(comparator.thenComparing(comparator2)).collect(Collectors.toList());

		Assertions.assertThat(constraintViolations.size()).isEqualTo(8);

		ConstraintViolation<Plane> violation1 = cvp.get(0);//.iterator().next();
		Assertions.assertThat(violation1.getPropertyPath().toString()).isEqualTo("description");
		Assertions.assertThat(violation1.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Plane> violation3 = cvp.get(4);//constraintViolations.iterator().next();
		Assertions.assertThat(violation3.getPropertyPath().toString()).isEqualTo("maxSeats");
		Assertions.assertThat(violation3.getMessage()).isEqualTo("must be greater than or equal to 0");

		ConstraintViolation<Plane> violation2 = cvp.get(1);//constraintViolations.iterator().next();
		Assertions.assertThat(violation2.getPropertyPath().toString()).isEqualTo("lastMaintenance");
		Assertions.assertThat(violation2.getMessage()).isEqualTo("must be a past date");

		ConstraintViolation<Plane> violation6 = cvp.get(6);//constraintViolations.iterator().next();
		Assertions.assertThat(violation6.getPropertyPath().toString()).isEqualTo("numberOfKm");
		Assertions.assertThat(violation6.getMessage()).isEqualTo("must be greater than or equal to 0");

		ConstraintViolation<Plane> violation4 = cvp.get(2);//constraintViolations.iterator().next();
		Assertions.assertThat(violation4.getPropertyPath().toString()).isEqualTo("manufacter");
		Assertions.assertThat(violation4.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Plane> violation8 = cvp.get(7);//constraintViolations.iterator().next();
		Assertions.assertThat(violation8.getPropertyPath().toString()).isEqualTo("reference");
		Assertions.assertThat(violation8.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Plane> violation7 = cvp.get(3);//constraintViolations.iterator().next();
		Assertions.assertThat(violation7.getPropertyPath().toString()).isEqualTo("maxDistance");
		Assertions.assertThat(violation7.getMessage()).isEqualTo("must be greater than or equal to 0");

		ConstraintViolation<Plane> violation5 = cvp.get(5);//constraintViolations.iterator().next();
		Assertions.assertThat(violation5.getPropertyPath().toString()).isEqualTo("model");
		Assertions.assertThat(violation5.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateClient() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = new Client();

		client.setId(1);
		client.setIdentification("");//
		client.setBirthDate(LocalDate.of(2010, 12, 12));
		client.setPhone("");//
		client.setEmail("");//
		client.setCreationDate(LocalDate.of(2010, 12, 12));

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		Comparator<ConstraintViolation<Client>> comparator = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Client>> comparator2 = Comparator.comparing(c->c.getMessage().toString());
		//comparator.thenComparing(comparator2);
		List<ConstraintViolation<Client>> cvc = constraintViolations.stream().sorted(comparator.thenComparing(comparator2)).collect(Collectors.toList());

		Assertions.assertThat(constraintViolations.size()).isEqualTo(4);

		ConstraintViolation<Client> violation1 = cvc.get(0);
		Assertions.assertThat(violation1.getPropertyPath().toString()).isEqualTo("email");
		Assertions.assertThat(violation1.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Client> violation2 = cvc.get(1);
		Assertions.assertThat(violation2.getPropertyPath().toString()).isEqualTo("identification");
		Assertions.assertThat(violation2.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Client> violation3 = cvc.get(2);
		Assertions.assertThat(violation3.getPropertyPath().toString()).isEqualTo("phone");
		Assertions.assertThat(violation3.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Client> violation4 = cvc.get(3);
		Assertions.assertThat(violation4.getPropertyPath().toString()).isEqualTo("phone");
		Assertions.assertThat(violation4.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
	}

	@Test
	void shouldNotValidateRunwayEmptyName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		RunwayType runwayType1 = new RunwayType();
		runwayType1.setName("take_off");
		runwayType1.setId(1);

		RunwayType runwayType2 = new RunwayType();
		runwayType2.setName("landing");
		runwayType2.setId(2);

		Runway runway1 = new Runway();
		runway1.setId(1);
		runway1.setName("");
		runway1.setRunwayType(runwayType1);

		Runway runway2 = new Runway();
		runway2.setId(2);
		runway2.setName("Example Runway 2");
		runway2.setRunwayType(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Runway>> constraintViolationsName = validator.validate(runway1);

		Assertions.assertThat(constraintViolationsName.size()).isEqualTo(1);
		ConstraintViolation<Runway> violationName = constraintViolationsName.iterator().next();
		Assertions.assertThat(violationName.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violationName.getMessage()).isEqualTo("must not be empty");

		Set<ConstraintViolation<Runway>> constraintViolationsRunwayType = validator.validate(runway2);

		Assertions.assertThat(constraintViolationsRunwayType.size()).isEqualTo(1);
		ConstraintViolation<Runway> violationRunwayType = constraintViolationsRunwayType.iterator().next();
		Assertions.assertThat(violationRunwayType.getPropertyPath().toString()).isEqualTo("runwayType");
		Assertions.assertThat(violationRunwayType.getMessage()).isEqualTo("must not be null");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void shouldNotValidateFlight() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		String stringDate1 = "31/12/2020";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate1);

		String stringDate2 = "31/12/2020";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate2);

		Flight flight1 = new Flight();
		flight1.setId(1);
		flight1.setAirline(new Airline());
		flight1.setDepartDate(date1);
		flight1.setDepartes(null);
		flight1.setFlightStatus(null);
		flight1.setLandDate(date2);
		flight1.setLands(null);
		flight1.setPlane(null);
		flight1.setPrice(null);
		flight1.setPublished(null);
		flight1.setReference("");
		flight1.setSeats(null);

		Validator validator1 = this.createValidator();
		Set<ConstraintViolation<Flight>> constraintViolations = validator1.validate(flight1);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(8);

		Comparator<ConstraintViolation<Flight>> comparator = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Flight>> comparator2 = Comparator.comparing(c->c.getMessage().toString());
		List<ConstraintViolation<Flight>> cvp = constraintViolations.stream().sorted(comparator.thenComparing(comparator2)).collect(Collectors.toList());

		
		ConstraintViolation<Flight> violation1 = cvp.get(6);
		Assertions.assertThat(violation1.getPropertyPath().toString()).isEqualTo("reference");
		Assertions.assertThat(violation1.getMessage()).isEqualTo("must not be empty");

		ConstraintViolation<Flight> violation2 = cvp.get(7);
		Assertions.assertThat(violation2.getPropertyPath().toString()).isEqualTo("seats");
		Assertions.assertThat(violation2.getMessage()).isEqualTo("must not be null");

		ConstraintViolation<Flight> violation3 = cvp.get(4);
		Assertions.assertThat(violation3.getPropertyPath().toString()).isEqualTo("price");
		Assertions.assertThat(violation3.getMessage()).isEqualTo("must not be null");

		ConstraintViolation<Flight> violation4 = cvp.get(1);
		Assertions.assertThat(violation4.getPropertyPath().toString()).isEqualTo("flightStatus");
		Assertions.assertThat(violation4.getMessage()).isEqualTo("must not be null");

		ConstraintViolation<Flight> violation5 = cvp.get(3);
		Assertions.assertThat(violation5.getPropertyPath().toString()).isEqualTo("plane");
		Assertions.assertThat(violation5.getMessage()).isEqualTo("must not be null");

		ConstraintViolation<Flight> violation6 = cvp.get(5);
		Assertions.assertThat(violation6.getPropertyPath().toString()).isEqualTo("published");
		Assertions.assertThat(violation6.getMessage()).isEqualTo("must not be null");

		ConstraintViolation<Flight> violation7 = cvp.get(0);
		Assertions.assertThat(violation7.getPropertyPath().toString()).isEqualTo("departes");
		Assertions.assertThat(violation7.getMessage()).isEqualTo("must not be null");

		ConstraintViolation<Flight> violation8 = cvp.get(2);
		Assertions.assertThat(violation8.getPropertyPath().toString()).isEqualTo("lands");
		Assertions.assertThat(violation8.getMessage()).isEqualTo("must not be null");

		Flight flight2 = new Flight();
		flight2.setId(2);
		flight2.setAirline(new Airline());
		flight2.setDepartDate(date1);
		flight2.setDepartes(new Runway());
		flight2.setFlightStatus(new FlightStatusType());
		flight2.setLandDate(date2);
		flight2.setLands(new Runway());
		flight2.setPlane(new Plane());
		flight2.setPrice(-100.0);
		flight2.setPublished(true);
		flight2.setReference("R-08");
		flight2.setSeats(-100);

		Validator validator2 = this.createValidator();
		Set<ConstraintViolation<Flight>> constraintViolations2 = validator2.validate(flight2);

		Assertions.assertThat(constraintViolations2.size()).isEqualTo(2);

		Comparator<ConstraintViolation<Flight>> comparator3 = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Flight>> comparator4 = Comparator.comparing(c->c.getMessage().toString());
		List<ConstraintViolation<Flight>> cvp2 = constraintViolations2.stream().sorted(comparator3.thenComparing(comparator4)).collect(Collectors.toList());

		System.out.println(cvp2);
		
		ConstraintViolation<Flight> violation9 = cvp2.get(1);
		Assertions.assertThat(violation9.getPropertyPath().toString()).isEqualTo("seats");
		Assertions.assertThat(violation9.getMessage()).isEqualTo("Can't be negative");

		ConstraintViolation<Flight> violation10 = cvp2.get(0);
		Assertions.assertThat(violation10.getPropertyPath().toString()).isEqualTo("price");
		Assertions.assertThat(violation10.getMessage()).isEqualTo("Can't be negative");

	}

	@Test
	void shouldNotValidateAirportRestrictions() throws ParseException {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Airport ap = new Airport();
		ap.setCity("");
		ap.setCode("abcd");
		ap.setLatitude(-200.);
		ap.setLongitude(-200.);
		ap.setMaxNumberOfClients(-10);
		ap.setMaxNumberOfPlanes(-10);
		ap.setName("");
		ap.setRunwaysInternal(new HashSet<>());
		ap.setId(1);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Airport>> constraintViolations = validator.validate(ap);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(8);

		Comparator<ConstraintViolation<Airport>> comparator = Comparator.comparing(c -> c.getPropertyPath().toString());
		Comparator<ConstraintViolation<Airport>> comparator2 = Comparator.comparing(c->c.getMessage().toString());
		List<ConstraintViolation<Airport>> cvp = constraintViolations.stream().sorted(comparator.thenComparing(comparator2)).collect(Collectors.toList());

		ConstraintViolation<Airport> violation0 = cvp.get(2);
		Assertions.assertThat(violation0.getPropertyPath().toString()).isEqualTo("code");
		Assertions.assertThat(violation0.getMessage()).isEqualTo("The code must contain 3 chraracters in caps");
		
		ConstraintViolation<Airport> violation1 = cvp.get(1);
		Assertions.assertThat(violation1.getPropertyPath().toString()).isEqualTo("city");
		Assertions.assertThat(violation1.getMessage()).isEqualTo("must not be empty");
		
		ConstraintViolation<Airport> violation2 = cvp.get(3);
		Assertions.assertThat(violation2.getPropertyPath().toString()).isEqualTo("latitude");
		Assertions.assertThat(violation2.getMessage()).isEqualTo("must be between -90 and 90");
		
		ConstraintViolation<Airport> violation3 = cvp.get(0);
		Assertions.assertThat(violation3.getPropertyPath().toString()).isEqualTo("city");
		Assertions.assertThat(violation3.getMessage()).isEqualTo("Only must contains letters");
		
		ConstraintViolation<Airport> violation4 = cvp.get(6);
		Assertions.assertThat(violation4.getPropertyPath().toString()).isEqualTo("maxNumberOfPlanes");
		Assertions.assertThat(violation4.getMessage()).isEqualTo("must be between 0 and 30000");
		
		ConstraintViolation<Airport> violation5 = cvp.get(5);
		Assertions.assertThat(violation5.getPropertyPath().toString()).isEqualTo("maxNumberOfClients");
		Assertions.assertThat(violation5.getMessage()).isEqualTo("must be between 0 and 30000");
		
		
		ConstraintViolation<Airport> violation6 = cvp.get(7);
		Assertions.assertThat(violation6.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation6.getMessage()).isEqualTo("must not be empty");
		
		ConstraintViolation<Airport> violation7 = cvp.get(4);
		Assertions.assertThat(violation7.getPropertyPath().toString()).isEqualTo("longitude");
		Assertions.assertThat(violation7.getMessage()).isEqualTo("must be between -180 and 180");
		

		
	}

}
