
package acmevolar.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.repository.AirportRepository;
import acmevolar.service.exceptions.DuplicatedAirportNameException;
import acmevolar.service.exceptions.IncorrectCartesianCoordinatesException;

@Service
public class AirportService {

	private AirportRepository airportRepository;


	@Autowired
	public AirportService(final AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	@Transactional(readOnly = true)
	public Airport findAirportById(final int id) throws DataAccessException {
		return this.airportRepository.findAirportById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Airport> findAirports() throws DataAccessException {
		return this.airportRepository.findAll();
	}
	
	public List<Airport> findAirportsByName(String airportName) throws DataAccessException{
		return this.airportRepository.findAirportsByName(airportName);
	}

	@Transactional(rollbackFor = IncorrectCartesianCoordinatesException.class, noRollbackFor = DuplicatedAirportNameException.class)
	public void saveAirport(final Airport airport) throws DataAccessException,IncorrectCartesianCoordinatesException,DuplicatedAirportNameException {
		if((airport.getLatitude()<=-180.) || (airport.getLatitude()>=180.)
				|| (airport.getLongitude()<=-180.) || (airport.getLongitude()>=180.)) {
			throw new IncorrectCartesianCoordinatesException();
		} else if(this.airportRepository.findAirportsByName(airport.getName()).size()!=0) {
			throw new DuplicatedAirportNameException();
		}
		this.airportRepository.save(airport);
	}

	public void deleteAirport(final Airport airport) {
		this.airportRepository.delete(airport);
	}

	@Transactional(readOnly = true)
	public Optional<Airport> findById(final int airportId) {
		return this.airportRepository.findById(airportId);
	}

}
