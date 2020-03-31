
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

import acmevolar.model.Plane;
import acmevolar.service.PlaneService;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class PlaneFormatterTests {

	@Mock
	private PlaneService	planeService;

	private PlaneFormatter	planeFormatter;


	@BeforeEach
	void setup() {
		this.planeFormatter = new PlaneFormatter(this.planeService);
	}

	@Test
	void testPrint() {
		Plane plane = new Plane();
		plane.setReference("V14-5");
		String planeName = this.planeFormatter.print(plane, Locale.ENGLISH);
		assertEquals("V14-5", planeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(this.planeService.findPlanes()).thenReturn(this.makePlanes());
		Plane plane = this.planeFormatter.parse("V14-5", Locale.ENGLISH);
		assertEquals("V14-5", plane.getReference());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(this.planeService.findPlanes()).thenReturn(this.makePlanes());
		assertThrows(ParseException.class, () -> {
			this.planeFormatter.parse("B10-0", Locale.ENGLISH);
		});
	}

	private List<Plane> makePlanes() {
		List<Plane> planes = new ArrayList<>();
		planes.add(new Plane() {

			{
				this.setReference("V14-5");
			}
		});
		planes.add(new Plane() {

			{
				this.setReference("V14-6");
			}
		});
		planes.add(new Plane() {

			{
				this.setReference("V12-9");
			}
		});
		return planes;
	}

}
