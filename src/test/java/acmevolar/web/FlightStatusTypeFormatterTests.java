
package acmevolar.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import acmevolar.model.FlightStatusType;
import acmevolar.service.FlightService;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class FlightStatusTypeFormatterTests {

	@Mock
	private FlightService				flightService;

	private FlightStatusTypeFormatter	flightStatusTypeFormatter;


	@BeforeEach
	void setup() {
		this.flightStatusTypeFormatter = new FlightStatusTypeFormatter(this.flightService);
	}

	@Test
	void testPrint() {
		FlightStatusType flightStatusType = new FlightStatusType();
		flightStatusType.setName("on_time");
		String flightStatusTypeName = this.flightStatusTypeFormatter.print(flightStatusType, Locale.ENGLISH);
		assertEquals("on_time", flightStatusTypeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(this.flightService.findFlightStatusTypes()).thenReturn(this.makeFlightStatusTypes());
		FlightStatusType flightStatusType = this.flightStatusTypeFormatter.parse("on_time", Locale.ENGLISH);
		assertEquals("on_time", flightStatusType.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(this.flightService.findFlightStatusTypes()).thenReturn(this.makeFlightStatusTypes());
		assertThrows(ParseException.class, () -> {
			this.flightStatusTypeFormatter.parse("otro_tipo", Locale.ENGLISH);
		});
	}

	private List<FlightStatusType> makeFlightStatusTypes() {
		List<FlightStatusType> flightStatusTypes = new ArrayList<>();
		flightStatusTypes.add(new FlightStatusType() {

			{
				this.setName("on_time");
			}
		});
		flightStatusTypes.add(new FlightStatusType() {

			{
				this.setName("delayed");
			}
		});
		flightStatusTypes.add(new FlightStatusType() {

			{
				this.setName("cancelled");
			}
		});
		return flightStatusTypes;
	}

}
