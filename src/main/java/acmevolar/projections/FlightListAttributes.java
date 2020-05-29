package acmevolar.projections;

import java.util.Date;

import acmevolar.model.Runway;

public interface FlightListAttributes {
	
	Integer				getLandAirportId();
	String				getLandAirportCity();
	String				getDepartAirportCity();
	Integer				getDepartAirportId();
	String				getPlaneModel();
	Integer				getPlaneId();	
	String				getAirlineName();
	Integer				getAirlineId();
	Integer				getId();
	String				getReference();
	Double				getPrice();
	Date				getLandDate();
	Date				getDepartDate();


}
