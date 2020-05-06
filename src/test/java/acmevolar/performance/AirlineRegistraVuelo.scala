package acmevolar.performance

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AirlineRegistraVuelo extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")

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
			.headers(headers_0))
		.pause(19)
	}

	object AirlineLogged{
		val airlineLogged = exec(http("AirlineLogged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "737bc308-1068-48de-9613-4b504f84211d"))
		.pause(12)
	}

	object FlightList{
		val flightList = exec(http("FlightList")
			.get("/flights")
			.headers(headers_0))
		.pause(8)
	}

	object FlightCreationForm{
		val flightCreationForm = exec(http("FlightCreationForm")
			.get("/flights/new")
			.headers(headers_0))
		.pause(44)
	}

	object FlightShow{
		val flightShow = exec(http("FlightShow")
			.post("/flights/new")
			.headers(headers_2)
			.formParam("reference", "REF-023")
			.formParam("seats", "2")
			.formParam("price", "1234.0")
			.formParam("flightStatus", "on_time")
			.formParam("published", "true")
			.formParam("plane", "V14-5")
			.formParam("lands", "A-03, airport: Adolfo Su�rez Madrid-Barajas Airport, city: Madrid")
			.formParam("landDate", "2020-08-02")
			.formParam("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
			.formParam("departDate", "2020-08-01")
			.formParam("_csrf", "56fcf47a-16d2-4e1a-b2dd-1040bc1c9ad7"))
		.pause(10)
	}

	val scn = scenario("AirlineRegistraVuelo").exec(Home.home, 
		Login.login, 
		AirlineLogged.airlineLogged, 
		FlightList.flightList, 
		FlightCreationForm.flightCreationForm, 
		FlightShow.flightShow)


	setUp(
		scn.inject(rampUsers(5000) during (100 seconds))	
		).protocols(httpProtocol)
	 	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}