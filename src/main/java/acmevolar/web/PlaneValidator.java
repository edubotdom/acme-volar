/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package acmevolar.web;

import java.time.Instant;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acmevolar.model.Plane;

/**
 * <code>Validator</code> for <code>Plane</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class PlaneValidator implements Validator {

	//private static final String REQUIRED = "required";
	//private FlightService flightService;

	@Override
	public void validate(Object obj, Errors errors) {		
		Plane plane = (Plane) obj;
		
		if(plane.getDescription().isEmpty()||plane.getLastMaintenance()==null||plane.getManufacter().isEmpty()||plane.getMaxDistance()==null||plane.getMaxSeats()==null||plane.getModel().isEmpty()||plane.getNumberOfKm()==null||plane.getReference().isEmpty()) {
			//errors.rejectValue("reference", "NullInformation", "You must fill all information");
			if(plane.getDescription().isEmpty()) {
				errors.rejectValue("description", "NullDescription", "You must fill description");
			} if(plane.getLastMaintenance()==null) {
				errors.rejectValue("lastMaintenance", "NullLastMaintenance", "You must fill last maintenance information");
			} if(plane.getManufacter().isEmpty()) {
				errors.rejectValue("manufacter", "NullManufacturer", "You must fill manufacturer");
			} if(plane.getMaxDistance()==null) {
				errors.rejectValue("maxDistance", "NullMaxDistance", "You must fill max distance");				
			} if(plane.getMaxSeats()==null) {
				errors.rejectValue("maxSeats", "NullMaxSeats", "You must fill max seats");				
			} if(plane.getModel().isEmpty()) {
				errors.rejectValue("model", "NullModel", "You must fill model");				
			} if(plane.getNumberOfKm()==null) {
				errors.rejectValue("numberOfKm", "NullNumberOfKm", "You must fill total number of kilometres of the aircraft");
			} if(plane.getReference().isEmpty()) {
				errors.rejectValue("reference", "NullReference", "You must fill reference");
			}
			
		} else {
			if(plane.getMaxDistance()<0) {
				errors.rejectValue("maxDistance", "NegativeMaxDistance", "You must introduce a positive number.");
			} 	if(plane.getNumberOfKm()<0) {
				errors.rejectValue("numberOfKm", "NegativeNumberOfKm", "You must introduce a positive number.");
			} 	if(plane.getMaxSeats()<0) {
				errors.rejectValue("maxSeats", "NegativeMaxSeats", "You must introduce a positive number.");
			}   if(plane.getLastMaintenance().after(Date.from(Instant.now()))) {
				errors.rejectValue("lastMaintenance", "FutureLastMaintenance", "You must introduce a past date.");
			}
		}
	}
/*						
		if(flight.getLands()==null||flight.getDepartes()==null||flight.getDepartDate()==null||flight.getLandDate()==null||flight.getSeats()==null||flight.getPrice()==null||flight.getReference().isEmpty()) {
			if(flight.getLands()==null) {
				errors.rejectValue("lands", "NullLandValue", "You must fill land's information.");
				
			} else if(flight.getDepartes()==null) {
				errors.rejectValue("departes", "NullDepartValue", "You must fill depart's information.");

			} else if(flight.getDepartDate()==null) {
				errors.rejectValue("departDate", "NullDepartDateValue", "You must fill depart date.");

			}else if(flight.getLandDate()==null) {
				errors.rejectValue("landDate", "NullLandDateValue", "You must fill land date.");
			
			} else if(flight.getSeats()==null) {
				errors.rejectValue("seats", "NullSeatValue", "You must fill seat information.");
			
			} else if(flight.getPrice()==null) {
				errors.rejectValue("price", "NullPriceValue", "You must fill price information.");
			
			} else if(flight.getReference().isEmpty()) {
				errors.rejectValue("reference", "NullReferenceValue", "You must fill reference information.");
			
			}
		} else {
			if(flight.getSeats()<0){
				errors.rejectValue("seats", "Minus0Seats", "You must specificate a number equal or higher than 0.");
			
			} if(flight.getPrice()<0){
							errors.rejectValue("price", "Minus0Price", "You must specificate a number equal or higher than 0.");
		} if (flight.getDepartes().getAirport().getName().equals(flight.getLands().getAirport().getName())) {
							errors.rejectValue("lands", "PathClosed", "This path is close, choose another airport(runway)");

		} if (flight.getDepartDate().after(flight.getLandDate())) {
							errors.rejectValue("landDate", "LandingBeforeDepartDate",
									"Landing date can't be programmed before departing date");

		} if (flight.getDepartDate().before(Calendar.getInstance().getTime())) {
							errors.rejectValue("departDate", "DepartBeforePresentDate",
									"Depart date can't be programmed before the present");
		}
		}
	}
*/
	/**
	 * This Validator validates *just* Plane instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Plane.class.isAssignableFrom(clazz);
	}

}
