package acmevolar.performance

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ClienteListaVuelosDisponibles extends Simulation {

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

	object Home {
	val home = exec(http("Home")
			.get("/"))
		.pause(1)
	}

	object Login{
	val login = exec(http("Login")
			.get("/login"))
		.pause(10)
	}

	object ClientLogged{

	val clientlogged = exec(http("ClientLogged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "724637ee-3097-4bfa-ae6c-92cd81e5575c"))
		.pause(12)
	}

	object ListFlights{

	val listflights = exec(http("ListFlights")
			.get("/flights"))
		.pause(10) 
	}


	val clientsScn = scenario("ClienteListaVuelosDisponibles").exec(Home.home,
		Login.login,
		ClientLogged.clientlogged,
		ListFlights.listflights)


	setUp(
		clientsScn.inject(rampUsers(5000) during (100 seconds))	
		).protocols(httpProtocol)
	 	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}