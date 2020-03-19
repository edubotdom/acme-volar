
package acmevolar.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import acmevolar.model.Plane;
import acmevolar.service.PlaneService;
import acmevolar.util.EntityUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PlaneServiceTests {

	@Autowired
	protected PlaneService planeService;


	@Test
	void shouldShowPlaneInformation() {
		Collection<Plane> planes = this.planeService.findPlanes();

		Plane plane1 = EntityUtils.getById(planes, Plane.class, 1);
		Assertions.assertThat(plane1.getReference()).isEqualTo("V14-5");
		Assertions.assertThat(plane1.getDescription()).isEqualTo("This is a description");
		Assertions.assertThat(plane1.getMaxSeats()).isEqualTo(150);
		Assertions.assertThat(plane1.getNumberOfKm()).isEqualTo(500000.23);
		Assertions.assertThat(plane1.getAirline().getId()).isEqualTo(1);
	}

	
}
