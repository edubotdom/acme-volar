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

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acmevolar.model.Runway;

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
public class RunwayValidator implements Validator {

	@Override
	public void validate(final Object obj, final Errors errors) {
		Runway runway = (Runway) obj;

		if (runway.getRunwayType() == null || runway.getName().isEmpty()) {
			if (runway.getRunwayType() == null) {
				errors.rejectValue("runwayType", "NullRunwayType", "You must fill all information");
			}
			if (runway.getName().isEmpty()) {
				errors.rejectValue("name", "NullName", "You must fill all information");
			}
		} else {
			//Ningún error posible
		}
	}

	/**
	 * This Validator validates *just* Runway instances
	 */
	@Override
	public boolean supports(final Class<?> clazz) {
		return Runway.class.isAssignableFrom(clazz);
	}

}
