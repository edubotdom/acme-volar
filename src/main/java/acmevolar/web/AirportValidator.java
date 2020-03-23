package acmevolar.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acmevolar.model.Airport;

public class AirportValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Airport.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Airport airport = (Airport) target;
		
		if((airport.getLatitude()<=-180.) || (airport.getLatitude()>=180.)) {
			errors.rejectValue("latitude", "MustBeContained", "Must be contained in -180. and 180");
		} else if((airport.getLongitude()<=-180.) || (airport.getLongitude()>=180.)) {
			errors.rejectValue("longitude", "MustBeContained", "Must be contained in -180. and 180");
		}
	}
	
	

}
