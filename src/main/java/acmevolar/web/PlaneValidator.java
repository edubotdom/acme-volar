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

	private String positiveNumber = "You must introduce a positive number.";


	@Override
	public void validate(final Object obj, final Errors errors) {
		Plane plane = (Plane) obj;

		if (plane.getDescription().isEmpty() || plane.getLastMaintenance() == null || plane.getManufacter().isEmpty() || plane.getMaxDistance() == null || plane.getMaxSeats() == null || plane.getModel().isEmpty() || plane.getNumberOfKm() == null
			|| plane.getReference().isEmpty()) {
			//errors.rejectValue("reference", "NullInformation", "You must fill all information");
			if (plane.getDescription().isEmpty()) {
				errors.rejectValue("description", "NullDescription", "You must fill description");
			} else if (plane.getLastMaintenance() == null) {
				errors.rejectValue("lastMaintenance", "NullLastMaintenance", "You must fill last maintenance information");
			} else if (plane.getManufacter().isEmpty()) {
				errors.rejectValue("manufacter", "NullManufacturer", "You must fill manufacturer");
			} else if (plane.getMaxDistance() == null) {
				errors.rejectValue("maxDistance", "NullMaxDistance", "You must fill max distance");
			} else if (plane.getMaxSeats() == null) {
				errors.rejectValue("maxSeats", "NullMaxSeats", "You must fill max seats");
			} else if (plane.getModel().isEmpty()) {
				errors.rejectValue("model", "NullModel", "You must fill model");
			} else if (plane.getNumberOfKm() == null) {
				errors.rejectValue("numberOfKm", "NullNumberOfKm", "You must fill total number of kilometres of the aircraft");
			} else if (plane.getReference().isEmpty()) {
				errors.rejectValue("reference", "NullReference", "You must fill reference");
			}

		} else {
			if (plane.getMaxDistance() < 0) {
				errors.rejectValue("maxDistance", "NegativeMaxDistance", this.positiveNumber);
			} else if (plane.getNumberOfKm() < 0) {
				errors.rejectValue("numberOfKm", "NegativeNumberOfKm", this.positiveNumber);
			} else if (plane.getMaxSeats() < 0) {
				errors.rejectValue("maxSeats", "NegativeMaxSeats", this.positiveNumber);
			} else if (plane.getLastMaintenance().after(Date.from(Instant.now()))) {
				errors.rejectValue("lastMaintenance", "FutureLastMaintenance", "You must introduce a past date.");
			}
		}
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return Plane.class.isAssignableFrom(clazz);
	}

}
