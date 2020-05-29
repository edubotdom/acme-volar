package acmevolar.performance

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AerolineaRegistraVueloCSV extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")

    val csvFeeder = csv("flights.csv").random

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"DNT" -> "1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"DNT" -> "1",
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))			
		).pause(19)
			.exec(http("AirlineLogged")
				.post("/login")
				.headers(headers_2)
				.formParam("username", "airline1")
				.formParam("password", "airline1")
				.formParam("_csrf", "${stoken}"))
			.pause(12)
	}

	object FlightList{
		val flightList = exec(http("FlightList")
			.get("/flights")
			.headers(headers_0))
		.pause(8)
	}

	object FlightCreationForm{
		val flightCreationForm = exec(
			http("FlightCreationForm")
			.get("/flights/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(44)
            .feed(csvFeeder)
                .exec(http("FlightShow")
                .post("/flights/new")
                .headers(headers_2)
                    .formParam("reference", "${reference}")
                    .formParam("seats", "${seats}")
                    .formParam("price", "${price}")
                    .formParam("flightStatus", "${flightStatus}")
                    .formParam("published", "${published}")
                    .formParam("plane", "${plane}")
                    .formParam("lands", "A-03, airport: Adolfo Su�rez Madrid-Barajas Airport, city: Madrid")
                    .formParam("landDate", "${landDate}")
                    .formParam("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
                    .formParam("departDate", "${departDate}")
                    .formParam("_csrf", "${stoken}"))
		.pause(10)
	}

	val scn = scenario("AirlineRegistraVuelo").exec(Home.home, 
		Login.login, 
		FlightList.flightList, 
		FlightCreationForm.flightCreationForm)


	setUp(
		scn.inject(rampUsers(500) during (100 seconds))	
		).protocols(httpProtocol)
	 	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}