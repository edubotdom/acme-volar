package acmevolar.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Plane;
import acmevolar.repository.PlaneRepository;
import acmevolar.service.exceptions.DuplicatedPetNameException;
import acmevolar.service.exceptions.DuplicatedPlaneReferenceException;

@Service
public class PlaneService {
	
	private PlaneRepository planeRepository;
	
	@Autowired
	public PlaneService(PlaneRepository planeRepository) {
		this.planeRepository = planeRepository;
	}
	
	@Transactional(readOnly = true)
	public Plane findPlaneById(int id) throws DataAccessException {
		return planeRepository.findById(id);
	}
	
	@Transactional(rollbackFor = DuplicatedPlaneReferenceException.class)
	public void savePlane(Plane plane) throws DataAccessException, DuplicatedPetNameException {
		planeRepository.save(plane);                
	}
	/*
	public void deletePlaneById(int id) throws DataAccessException{
		planeRepository.deleteById(id);
	}
	
	public void deletePlane(Plane plane) throws DataAccessException {
		planeRepository.deleteById(plane.getId());
	}
	
	public void updatePlane(Plane plane) throws DataAccessException {
		Integer id = plane.getId();				// extract id of a plane
		Plane plane2 = findPlaneById(id);		// we know the original plane with that id
		deletePlane(plane2);					// we delete the original
		planeRepository.save(plane);			// we replace with the updated version
	}
	*/
	@Transactional(readOnly = true)	
	public Collection<Plane> findPlanes() throws DataAccessException {
		return planeRepository.findAll();
	}
	
	

}
