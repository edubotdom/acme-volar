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

	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	Object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
	}

	Object RegistrationForm {
		val registrationForm = exec(http("RegistrationForm")
			.get("/airlines/new")
			.headers(headers_0))
		.pause(59)
	}

	Object Registered {
		val registered = exec(http("Registered")
			.post("/airlines/new")
			.headers(headers_2)
			.formParam("name", "Airlinetest")
			.formParam("identification", "53934261-P")
			.formParam("reference", "MA-001")
			.formParam("email", "airlinetest@gmail.com")
			.formParam("country", "Spain")
			.formParam("phone", "618293456")
			.formParam("user.username", "airlinetest")
			.formParam("user.password", "1nv1tad0")
			.formParam("_csrf", "5d105c1b-1e28-4f06-903b-1753389ba83c"))
		.pause(27)
	}

	Object LoginForm {
		val loginForm = exec(http("LoginForm")
			.get("/login")
			.headers(headers_0)
		.pause(24)
	}

	Object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airlinetest")
			.formParam("password", "1nv1tad0")
			.formParam("_csrf", "5d105c1b-1e28-4f06-903b-1753389ba83c"))
		.pause(19)
	}

	val airlineScn = scenario("RegistrationAndLoginAirline").exec(Home.home,
	RegistrationForm.registrationForm,
	Registered.registered,
	LoginForm.loginForm,
	Logged.logged
	)
		
			//.resources(http("request_4")
			//.get("/login")
			//.headers(headers_4)))
		
		// LoginFormx2

	setUp(airlineScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}