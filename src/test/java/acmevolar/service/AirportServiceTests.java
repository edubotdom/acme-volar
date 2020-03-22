package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.List;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.model.Owner;
import acmevolar.model.Pet;
import acmevolar.model.PetType;
import acmevolar.model.Visit;
import acmevolar.service.exceptions.DuplicatedPetNameException;
import acmevolar.util.EntityUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AirportServiceTests {
	
	@Autowired
	protected AirportService airportService;
	
	@Autowired
	protected RunwayService runwayService;
	
	void shouldFindAirportsByName() {
		
		Collection<Airport> airport = this.airportService.findAirportsByName("SevilleAirport");
		assertThat(airport.size()).isNotEqualTo(0);
		
	}
	
	@Test
	void shouldFindAirport() {
		Collection<Airport> airports = this.airportService.findAirports();
		
		Airport airport1 = EntityUtils.getById(airports, Airport.class, 1);
		assertThat(airport1.getName()).isEqualTo("Sevilla Airport");
		assertThat(airport1.getMaxNumberOfPlanes()).isEqualTo(50);
		assertThat(airport1.getMaxNumberOfClients()).isEqualTo(600);
		assertThat(airport1.getLatitude()).isEqualTo(37.4180000);
		assertThat(airport1.getLongitude()).isEqualTo(-5.8931100);
		assertThat(airport1.getCode()).isEqualTo("SQV");
		assertThat(airport1.getCity()).isEqualTo("Sevilla");
	}
	
	@Test
	@Transactional
	public void shouldInsertAirport() {
		
	}
	
	
	/*
	@Test
	@Transactional
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
	Owner owner6 = this.ownerService.findOwnerById(6);
	int found = owner6.getPets().size();
	
	Pet pet = new Pet();
	pet.setName("bowser");
	Collection<PetType> types = this.petService.findPetTypes();
	pet.setType(EntityUtils.getById(types, PetType.class, 2));
	pet.setBirthDate(LocalDate.now());
	owner6.addPet(pet);
	assertThat(owner6.getPets().size()).isEqualTo(found + 1);
	
	try {
	this.petService.savePet(pet);
	} catch (DuplicatedPetNameException ex) {
	Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
	}
	this.ownerService.saveOwner(owner6);
	
	owner6 = this.ownerService.findOwnerById(6);
	assertThat(owner6.getPets().size()).isEqualTo(found + 1);
	// checks that id has been generated
	assertThat(pet.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingPetsWithTheSameName() {
	Owner owner6 = this.ownerService.findOwnerById(6);
	Pet pet = new Pet();
	pet.setName("wario");
	Collection<PetType> types = this.petService.findPetTypes();
	pet.setType(EntityUtils.getById(types, PetType.class, 2));
	pet.setBirthDate(LocalDate.now());
	owner6.addPet(pet);
	try {
	petService.savePet(pet);
	} catch (DuplicatedPetNameException e) {
	// The pet already exists!
	e.printStackTrace();
	}
	
	Pet anotherPetWithTheSameName = new Pet();
	anotherPetWithTheSameName.setName("wario");
	anotherPetWithTheSameName.setType(EntityUtils.getById(types, PetType.class, 1));
	anotherPetWithTheSameName.setBirthDate(LocalDate.now().minusWeeks(2));
	Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
	owner6.addPet(anotherPetWithTheSameName);
	petService.savePet(anotherPetWithTheSameName);
	});
	}
	
	@Test
	@Transactional
	public void shouldUpdatePetName() throws Exception {
	Pet pet7 = this.petService.findPetById(7);
	String oldName = pet7.getName();
	
	String newName = oldName + "X";
	pet7.setName(newName);
	this.petService.savePet(pet7);
	
	pet7 = this.petService.findPetById(7);
	assertThat(pet7.getName()).isEqualTo(newName);
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionUpdatingPetsWithTheSameName() {
	Owner owner6 = this.ownerService.findOwnerById(6);
	Pet pet = new Pet();
	pet.setName("wario");
	Collection<PetType> types = this.petService.findPetTypes();
	pet.setType(EntityUtils.getById(types, PetType.class, 2));
	pet.setBirthDate(LocalDate.now());
	owner6.addPet(pet);
	
	Pet anotherPet = new Pet();
	anotherPet.setName("waluigi");
	anotherPet.setType(EntityUtils.getById(types, PetType.class, 1));
	anotherPet.setBirthDate(LocalDate.now().minusWeeks(2));
	owner6.addPet(anotherPet);
	
	try {
	petService.savePet(pet);
	petService.savePet(anotherPet);
	} catch (DuplicatedPetNameException e) {
	// The pets already exists!
	e.printStackTrace();
	}
	
	Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
	anotherPet.setName("wario");
	petService.savePet(anotherPet);
	});
	}
	
	@Test
	@Transactional
	public void shouldAddNewVisitForPet() {
	Pet pet7 = this.petService.findPetById(7);
	int found = pet7.getVisits().size();
	Visit visit = new Visit();
	pet7.addVisit(visit);
	visit.setDescription("test");
	this.petService.saveVisit(visit);
	try {
	this.petService.savePet(pet7);
	} catch (DuplicatedPetNameException ex) {
	Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	pet7 = this.petService.findPetById(7);
	assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
	assertThat(visit.getId()).isNotNull();
	}
	
	@Test
	void shouldFindVisitsByPetId() throws Exception {
	Collection<Visit> visits = this.petService.findVisitsByPetId(7);
	assertThat(visits.size()).isEqualTo(2);
	Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
	assertThat(visitArr[0].getPet()).isNotNull();
	assertThat(visitArr[0].getDate()).isNotNull();
	assertThat(visitArr[0].getPet().getId()).isEqualTo(7);
	}
	*/
}