package acmevolar.performance

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AirlineConsultaVuelo extends Simulation {

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
		.pause(14)
	}

	object AirlineLogged{
		val airlineLogged = exec(http("AirlineLogged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "d24572f1-b7a3-4adf-be00-fbca77cf9a6d"))
		.pause(29)
	}

	object ListMyFlights{
		val listMyFlights = exec(http("ListMyFlights")
			.get("/my_flights"))
		.pause(15)
	}

	object ShowFlight{
		val showFlight = exec(http("ShowFlight")
			.get("/flights/1"))
		.pause(8)
	}

	val scn = scenario("AirlineConsultaVuelo").exec(Home.home, 
		Login.login,
		AirlineLogged.airlineLogged,
		ListMyFlights.listMyFlights,
		ShowFlight.showFlight)

	setUp(
		scn.inject(rampUsers(5000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}