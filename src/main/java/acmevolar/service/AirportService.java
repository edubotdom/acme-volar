
package acmevolar.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.projections.AirportListAttributes;
import acmevolar.repository.AirportRepository;
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
	@Cacheable("airports")
	public Collection<Airport> findAirports() throws DataAccessException {
		return this.airportRepository.findAll();
	}

	public List<Airport> findAirportsByName(final String airportName) throws DataAccessException {
		return this.airportRepository.findAirportsByName(airportName);
	}

	@Transactional(rollbackFor = IncorrectCartesianCoordinatesException.class)
	@CacheEvict(cacheNames = "listAirports", allEntries = true)
	public void saveAirport(final Airport airport) throws DataAccessException, IncorrectCartesianCoordinatesException {
		if (airport.getLatitude() <= -90. || airport.getLatitude() >= 90. || airport.getLongitude() <= -180. || airport.getLongitude() >= 180.) {
			throw new IncorrectCartesianCoordinatesException();
		}
		this.airportRepository.save(airport);
	}

	@Transactional
	@CacheEvict(cacheNames = "listAirports", allEntries = true)
	public void deleteAirport(final Airport airport) {
		this.airportRepository.delete(airport);
	}

	@Transactional(readOnly = true)
	public Optional<Airport> findById(final int airportId) {
		return this.airportRepository.findById(airportId);
	}

	@Transactional(readOnly = true)
	@Cacheable("listAirports")
	public List<AirportListAttributes> findAirportListAttributes() throws DataAccessException {
		return this.airportRepository.findAllAirportAttributes();
	}

}
