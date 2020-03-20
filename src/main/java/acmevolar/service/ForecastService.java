package acmevolar.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import acmevolar.model.api.Forecast;

import reactor.core.publisher.Mono;

@Service
public class ForecastService {	

	    static final String API_BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
	    //private static final String USER_AGENT = "Spring 5 WebClient";
	    //private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);
	    
	    final WebClient webClient;
	    
	    public ForecastService() {
	        this.webClient = WebClient.builder()
	                .baseUrl(API_BASE_URL)
	                //.defaultHeader(HttpHeaders.CONTENT_TYPE, OMDB_MIME_TYPE)
	                //.defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
	                .build();
	    }

	    public Mono<Forecast> searchForecastByCity(String city) {
	          return webClient.post()
	                .uri("?q="+city+"&APPID=d2f0a2704a754d4725df51ffd0749391")
	                  .retrieve()
	                  .bodyToMono(Forecast.class);
	    }

	

}
