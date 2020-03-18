
package acmevolar.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Plane;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.PlaneRepository;
import acmevolar.repository.springdatajpa.SpringDataPlaneRepository;
import acmevolar.service.exceptions.DuplicatedPetNameException;

@Service
public class PlaneService {

	private PlaneRepository planeRepository;
	private AirlineRepository airlineRepository;
	private SpringDataPlaneRepository springPlaneRepository;
	
	@Autowired
	public PlaneService(PlaneRepository planeRepository,SpringDataPlaneRepository springPlaneRepository,AirlineRepository airlineRepository) {
		this.planeRepository = planeRepository;
		this.springPlaneRepository = springPlaneRepository;
		this.airlineRepository = airlineRepository;
	}

	@Transactional(readOnly = true)
	public Plane findPlaneById(final int id) throws DataAccessException {
		return this.planeRepository.findById(id);
	}

	
	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePlane(Plane plane) throws DataAccessException {
		planeRepository.save(plane);                
	}

	public void deleteById(final int id) throws DataAccessException {
		this.planeRepository.deleteById(id);
	}

	public void deletePlane(final Plane plane) throws DataAccessException {
		this.planeRepository.deleteById(plane.getId());
	}

	public void updatePlane(final Plane plane) throws DataAccessException {
		Integer id = plane.getId();				// extract id of a plane
		Plane plane2 = this.findPlaneById(id);		// we know the original plane with that id
		this.deletePlane(plane2);					// we delete the original
		this.planeRepository.save(plane);			// we replace with the updated version
	}

	@Transactional(readOnly = true)
	public Collection<Plane> findPlanes() throws DataAccessException {
		return this.planeRepository.findAll();
	}

	//Airline
	@Transactional(readOnly = true)
	public Airline findAirlineByUsername(final String username) throws DataAccessException {
		return this.airlineRepository.findByUsername(username);
	}

	public Collection<Plane> getAllPlanesFromAirline(final String airline) {
		return this.planeRepository.findPlanesbyAirline(airline);
	}

	public List<Plane> getAllPlanesFromAirline(Airline airline) {
		return this.springPlaneRepository.findPlanesByAirlineId(airline.getId());
	}

	public List<Plane> findPlanesByReference(String reference) {
		
		return this.planeRepository.findPlanesbyReference(reference);
	}
	
}
