package performanceTest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RegistrationAndLoginAirline extends Simulation {

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

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("AirlineRegistrationAndLogin")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(17)
		// Home
		.exec(http("request_1")
			.get("/airlines/new")
			.headers(headers_0))
		.pause(40)
		// RegistrationForm
		.exec(http("request_2")
			.post("/airlines/new")
			.headers(headers_2)
			.formParam("name", "Airlinetest")
			.formParam("identification", "53934261-P")
			.formParam("reference", "KA-001")
			.formParam("email", "airlinetest@gmail.com")
			.formParam("country", "Spain")
			.formParam("phone", "678901234")
			.formParam("user.username", "airlinetest")
			.formParam("user.password", "1nv1tad0")
			.formParam("_csrf", "aba7adf4-8edd-4dec-a033-856035fd2bc2"))
		.pause(27)
		// RegisteredRedirection
		.exec(http("request_3")
			.get("/login")
			.headers(headers_0))
		.pause(13)
		// LoginForm
		.exec(http("request_5")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airlinetest")
			.formParam("password", "1nv1tad0")
			.formParam("_csrf", "aba7adf4-8edd-4dec-a033-856035fd2bc2"))
		.pause(12)
		// Logged
		.exec(http("request_6")
			.get("/logout")
			.headers(headers_0))
		.pause(8)
		// LogOut
		.exec(http("request_7")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "5b3778e3-d182-4d89-b9ad-f29c6418c833"))
		.pause(6)
		// LoggedOut

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}