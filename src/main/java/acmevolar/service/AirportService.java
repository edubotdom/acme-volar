
package acmevolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.repository.AirportRepository;

@Service
public class AirportService {

	private AirportRepository airportRepository;


	@Autowired
	public AirportService(final AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	@Transactional(readOnly = true)
	public Airport findAirportById(final int id) throws DataAccessException {
		return this.airportRepository.findById(id);
	}

}
