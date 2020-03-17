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

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'. Starting
 * from Spring 3.0, Formatters have come as an improvement in comparison to legacy
 * PropertyEditors. See the following links for more details: - The Spring ref doc:
 * http://static.springsource.org/spring/docs/current/spring-framework-reference/html/validation.html#format-Formatter-SPI
 * - A nice blog entry from Gordon Dickens:
 * http://gordondickens.com/wordpress/2010/09/30/using-spring-3-0-custom-type-converter/
 * <p/>
 * Also see how the bean 'conversionService' has been declared inside
 * /WEB-INF/mvc-core-config.xml
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 */

@Component
public class PlaneFormatter implements Formatter<Plane> {

	private final PlaneService planeService;


	@Autowired
	public PlaneFormatter(final PlaneService planeService) {
		this.planeService = planeService;
	}

	@Override
	public String print(final Plane plane, final Locale locale) {
		return plane.getReference();
	}

	@Override
	public Plane parse(String text, Locale locale) throws ParseException {
		Collection<Plane> findPlanes = this.planeService.findPlanes();
		for (Plane plane : findPlanes) {
			if (plane.getReference().equals(text)) {
				return plane;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
