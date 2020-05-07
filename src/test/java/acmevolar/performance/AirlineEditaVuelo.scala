package acmevolar.performance

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AirlineEditaVuelo extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.doNotTrackHeader("1")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	object Home{
		val home = exec(http("Home")
			.get("/"))
		.pause(7)
	}

	object Login{
		val login = exec(http("Login")
			.get("/login"))
		.pause(10)
	}

	object AirlineLogged{
		val airlineLogged = exec(http("AirlineLogged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "a09e4a00-876e-41ef-8ee7-544bd35bcfd8"))
		.pause(19)
	}

	object ListMyFlights{
		val listMyFlights = exec(http("ListMyFlights")
			.get("/my_flights"))
		.pause(12)
	}

	object ShowFlight{
		val showFlight = exec(http("ShowFlight")
			.get("/flights/4"))
		.pause(14)
	}

	object EditFormFlight{
		val editFormFlight = exec(http("EditFormFlight")
			.get("/flights/4/edit"))
		.pause(21)
	}

	object ShowEditedFlight{
		val showEditedFlight = exec(http("ShowEditedFlight")
			.post("/flights/4/edit")
			.headers(headers_2)
			.formParam("reference", "R-04")
			.formParam("id", "4")
			.formParam("seats", "400")
			.formParam("price", "340.5")
			.formParam("flightStatus", "delayed")
			.formParam("published", "false")
			.formParam("plane", "RB7")
			.formParam("lands", "A-50, airport: Aeropuerto de Huesca-Pirineos, city: Huesca")
			.formParam("landDate", "2021-06-07")
			.formParam("departes", "A-23, airport: Aeropuerto Federico Garc�a Lorca Granada-Ja�n, city: Granada")
			.formParam("departDate", "2021-06-07")
			.formParam("_csrf", "d0812734-3b80-41d6-a1e2-612abd6804c2"))
		.pause(13)
	}

	val scn = scenario("AirlineEditaVuelo").exec(Home.home, 
			Login.login, 
			AirlineLogged.airlineLogged, 
			ListMyFlights.listMyFlights, 
			ShowFlight.showFlight, 
			EditFormFlight.editFormFlight,
			ShowEditedFlight.showEditedFlight)

	setUp(
		scn.inject(rampUsers(5000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}