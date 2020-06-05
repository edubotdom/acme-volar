package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RegistrationClientAndAirline extends Simulation {

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

	object RegistrationAndRegisteredClient {
		val regist = exec(http("ClientRegistration")
			.get("/clients/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(92)
		.exec(http("ClientRegistered")
			.post("/clients/new")
			.headers(headers_2)
			.formParam("name", "Pepito Camotes Areto")
			.formParam("identification", "87654321-L")
			.formParam("birthDate", "1998/11/04")
			.formParam("email", "pcamarotes@gmail.com")
			.formParam("phone", "987654321")
			.formParam("user.username", "client4")
			.formParam("user.password", "client4")
			.formParam("_csrf", "${stoken}"))
		.pause(14)
		
	}

	object RegistrationAndRegisteredAirline {
		var regist = exec(http("AirlineRegistration")
			.get("/airlines/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(40)
		// RegistrationForm
		.exec(http("AirlineRegistered")
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
			.formParam("_csrf", "${stoken}"))
		.pause(27)
		// RegisteredRedirection
	}

	val airlineScn = scenario("RegistrationAirline").exec(Home.home,
		RegistrationAndRegisteredAirline.regist)
		

	val clientScn = scenario("RegistrationClient").exec(Home.home,
		RegistrationAndRegisteredClient.regist)
		

	setUp(
		airlineScn.inject(rampUsers(6000) during (100 seconds)), 
		clientScn.inject(rampUsers(6000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(global.responseTime.max.lt(5000),forAll.failedRequests.percent.lte(5),global.successfulRequests.percent.gt(90)).maxDuration(10 minutes)
}