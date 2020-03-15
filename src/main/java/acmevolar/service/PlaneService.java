package acmevolar.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Plane;
import acmevolar.repository.PlaneRepository;
import acmevolar.service.exceptions.DuplicatedPetNameException;

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
	
	

}
