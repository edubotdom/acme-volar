/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acmevolar.web;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acmevolar.model.Flight;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class FlightValidator implements Validator {

	private static final String SEATS = "seats";

	@Override
	public void validate(final Object obj, final Errors errors) {
		Flight flight = (Flight) obj;

		boolean notNullorEmpty = (flight.getLands() == null || flight.getDepartes() == null || flight.getDepartDate() == null || flight.getLandDate() == null || flight.getSeats() == null || flight.getPrice() == null || flight.getReference().isEmpty()
			|| flight.getPlane() == null || flight.getPublished() == null || flight.getFlightStatus() == null);

		if (notNullorEmpty) {
			if (flight.getReference().isEmpty()) {
				errors.rejectValue("reference", "NullReferenceValue", "You must fill reference information.");

			} else if (flight.getSeats() == null) {
				errors.rejectValue(SEATS, "NullSeatValue", "You must fill seat information.");

			} else if (flight.getPrice() == null) {
				errors.rejectValue("price", "NullPriceValue", "You must fill price information.");

			} else if (flight.getFlightStatus() == null) {
				errors.rejectValue("flightStatus", "NullFlightStatusValue", "You must fill status information.");

			} else if (flight.getPublished() == null) {
				errors.rejectValue("published", "NullPublishedValue", "You must fill visibility information.");

			} else if (flight.getPlane() == null) {
				errors.rejectValue("plane", "NullPlaneValue", "You must fill plane information.");

			} else if (flight.getLands() == null) {
				errors.rejectValue("lands", "NullLandValue", "You must fill land's information.");

			} else if (flight.getLandDate() == null) {
				errors.rejectValue("landDate", "NullLandDateValue", "You must fill land date.");

			} else if (flight.getDepartes() == null) {
				errors.rejectValue("departes", "NullDepartValue", "You must fill depart's information.");

			} else if (flight.getDepartDate() == null) {
				errors.rejectValue("departDate", "NullDepartDateValue", "You must fill depart date.");

			}
		} else {
			if (flight.getSeats() < 0) {
				errors.rejectValue(SEATS, "Minus0Seats", "You must specificate a number equal or higher than 0.");

			}
			if (flight.getSeats() > flight.getPlane().getMaxSeats()) {
				errors.rejectValue(SEATS, "TooManySeats", "The number of seats in the flight cannot by higher than the number of seats in its plane.");

			}
			if (flight.getPrice() < 0) {
				errors.rejectValue("price", "Minus0Price", "You must specificate a number equal or higher than 0.");
			}
			if (flight.getDepartes().getAirport().getName().equals(flight.getLands().getAirport().getName())) {
				errors.rejectValue("lands", "PathClosed", "This path is close, choose another airport(runway)");

			}
			if (flight.getDepartDate().after(flight.getLandDate())) {
				errors.rejectValue("landDate", "LandingBeforeDepartDate", "Landing date can't be programmed before departing date");

			}
			if (flight.getDepartDate().before(Calendar.getInstance().getTime())) {
				errors.rejectValue("departDate", "DepartBeforePresentDate", "Depart date can't be programmed before the present");
			}
		}
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return Flight.class.isAssignableFrom(clazz);
	}

}
