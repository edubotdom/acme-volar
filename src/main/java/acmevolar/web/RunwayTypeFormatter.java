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
import acmevolar.model.RunwayType;
import acmevolar.service.FlightService;
import acmevolar.service.RunwayService;

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
public class RunwayTypeFormatter implements Formatter<RunwayType> {

	private final RunwayService runwayService;


	@Autowired
	public RunwayTypeFormatter(final RunwayService runwayService) {
		this.runwayService = runwayService;
	}

	@Override
	public String print(final RunwayType runwayType, final Locale locale) {
		return runwayType.getName();
	}

	@Override
	public RunwayType parse(final String text, final Locale locale) throws ParseException {
		Collection<RunwayType> findRunwaysTypes = this.runwayService.findRunwaysTypes();
		for (RunwayType type : findRunwaysTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
