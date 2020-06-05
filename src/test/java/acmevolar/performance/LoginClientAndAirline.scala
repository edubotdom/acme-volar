package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoginClientAndAirline extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.jpg""", """.*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.jar""", """.*.xz""", """.*.xml""", """.*.dif""", """.*.klz"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,en-US;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object LogingAndLoggedClient {
		var log = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(19)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	object LogoutClient {
		var logout = exec(http("Logout")
			.get("/logout")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(14)
		.exec(http("Loggedout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object LogingAndLoggedAirline{
		val log = exec(http("LoginForm")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(13)
		// LoginForm
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
		// Logged
	}
	
	object LogoutAirline {
		val logout = exec(http("Logout")
			.get("/logout")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8)
		// LogOut
		.exec(http("Loggedout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}"))
		.pause(6)
		// LoggedOut
	}
	val airlineScn = scenario("LoginAirline").exec(Home.home,
		LogingAndLoggedAirline.log,
		LogoutAirline.logout)
		

	val clientScn = scenario("LoginClient").exec(Home.home,
		LogingAndLoggedClient.log,
		LogoutClient.logout)
		

	setUp(
		clientScn.inject(rampUsers(1500) during (100 seconds)),
		airlineScn.inject(rampUsers(500) during (100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}