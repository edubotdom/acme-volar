package acmevolar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import acmevolar.model.api.Forecast;

@SpringBootApplication()
public class AcmeVolarApplication {

	private static final Logger log = LoggerFactory.getLogger(AcmeVolarApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AcmeVolarApplication.class, args);
	}
/*
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		String url = "https://api.openweathermap.org/data/2.5/weather?q=Sevilla&APPID=d2f0a2704a754d4725df51ffd0749391";
		return args -> {
			Forecast forecast = restTemplate.getForObject(
					url , Forecast.class);
			log.info(forecast.toString());
		};
	}
	*/
}
