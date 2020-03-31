package acmevolar.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import acmevolar.model.RunwayType;
import acmevolar.service.RunwayService;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class RunwayTypeFormatterTests {

	@Mock
	private RunwayService runwayService;

	private RunwayTypeFormatter runwayTypeFormatter;

	@BeforeEach
	void setup() {
		runwayTypeFormatter = new RunwayTypeFormatter(runwayService);
	}

	@Test
	void testPrint() {
		RunwayType runwayType = new RunwayType();
		runwayType.setName("take_off");
		String runwayTypeName = runwayTypeFormatter.print(runwayType, Locale.ENGLISH);
		assertEquals("take_off", runwayTypeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(runwayService.findRunwaysTypes()).thenReturn(makeRunwayTypes());
		RunwayType runwayType = runwayTypeFormatter.parse("landing", Locale.ENGLISH);
		assertEquals("landing", runwayType.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(runwayService.findRunwaysTypes()).thenReturn(makeRunwayTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			runwayTypeFormatter.parse("otro_tipo", Locale.ENGLISH);
		});
	}

	private List<RunwayType> makeRunwayTypes() {
		List<RunwayType> runwayTypes = new ArrayList<>();
		runwayTypes.add(new RunwayType() {
			{
				setName("take_off");
			}
		});
		runwayTypes.add(new RunwayType() {
			{
				setName("landing");
			}
		});
		return runwayTypes;
	}

}
