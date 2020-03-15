package acmevolar.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Plane;
import acmevolar.repository.PlaneRepository;
import acmevolar.repository.springdatajpa.SpringDataPlaneRepository;
import acmevolar.service.exceptions.DuplicatedPetNameException;

@Service
public class PlaneService {
	
	private PlaneRepository planeRepository;
	private SpringDataPlaneRepository springPlaneRepository;
	
	@Autowired
	public PlaneService(PlaneRepository planeRepository,SpringDataPlaneRepository springPlaneRepository) {
		this.planeRepository = planeRepository;
		this.springPlaneRepository = springPlaneRepository;
	}
	
	@Transactional(readOnly = true)
	public Plane findPlaneById(int id) throws DataAccessException {
		return planeRepository.findById(id);
	}
	
	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePlane(Plane plane) throws DataAccessException {
		planeRepository.save(plane);                
	}
	
	public void deleteById(int id) throws DataAccessException{
		planeRepository.deleteById(id);
	}
	
	public void deletePlane(Plane plane) throws DataAccessException {
		planeRepository.deleteById(plane.getId());
	}
	
	@Transactional(readOnly = true)	
	public Collection<Plane> findPlanes() throws DataAccessException {
		return planeRepository.findAll();
	}
	
	public List<Plane> getAllPlanesFromAirline(Airline airline) {
		return this.springPlaneRepository.findPlanesByAirlineId(airline.getId());
	}
	
	

}
