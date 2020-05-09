package performanceTest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ClienteConsultaAvion extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.ico""", """.*.js""", """.*.css""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	object LoginForm{
		val loginForm = exec(http("LoginForm")
			.get("/login")
			.headers(headers_0))
		.pause(16)
	}

	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "1237c2f9-c167-4817-813a-bb0d00e8a153"))
		.pause(14)
	}

	object ShowPlane{
		val showPlane = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "1237c2f9-c167-4817-813a-bb0d00e8a153"))
		.pause(14)
	}


	val airlineScn = scenario("ClienteConsultaAvion").exec(Home.home,
	LoginForm.loginForm,
	Logged.logged,
	ShowPlane.showPlane)


	setUp(airlineScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}