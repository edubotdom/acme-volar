
package acmevolar.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Plane;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.PlaneRepository;

@Service
public class PlaneService {

	private PlaneRepository		planeRepository;
	private AirlineRepository	airlineRepository;


	@Autowired
	public PlaneService(final PlaneRepository planeRepository, final AirlineRepository airlineRepository) {
		this.planeRepository = planeRepository;
		this.airlineRepository = airlineRepository;
	}

	@Transactional(readOnly = true)
	public Plane findPlaneById(final int id) throws DataAccessException {
		return this.planeRepository.findById(id);
	}

	@Transactional
	public void savePlane(final Plane plane) throws DataAccessException {
		this.planeRepository.save(plane);
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

}
