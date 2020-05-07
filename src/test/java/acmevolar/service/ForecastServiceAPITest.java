package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.api.Forecast;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ForecastServiceAPITest {

	@Autowired
	protected ForecastService forecastService;
	
	@Test
	@Transactional
	void shouldSearchForecastByCity() {
		String city = "Sevilla";
		Forecast forecast = this.forecastService.searchForecastByCity(city).block();
		assertThat(forecast).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldThrowForecastByCity() {
		String city = "Safsigs5154";
		
		assertThrows(Exception.class, () -> {
			this.forecastService.searchForecastByCity(city).block();
		});
		
	}
	
	
}
