package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AerolineaCreaAvionCSV extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.jpg""", """.*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.jar""", """.*.xz""", """.*.xml""", """.*.dif""", """.*.klz"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es,en-US;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es,en-US;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es,en-US;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-CryptoAPI/10.0")

	val csvFeeder = csv("aeroplanes.csv").random

	val scn = scenario("AerolineaCreaAvionCSV")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(25)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "${stoken}"))
		.pause(43)


		//CSV
		.exec(repeat(1) {

			exec(http("CreatePlane")
				.get("/planes/new")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken")))
			.pause(59)

			.feed(csvFeeder)
				.exec(http("CreatedPlane")
					.post("/planes/new")
					.headers(headers_3)
						.formParam("id", "$id")
						.formParam("reference", "${reference}")
						.formParam("maxSeats", "${maxSeats}")
						.formParam("description", "${description}")
						.formParam("manufacter", "${manufacter}")
						.formParam("model", "${model}")
						.formParam("numberOfKm", "${numberOfKm}")
						.formParam("maxDistance", "${maxDistance}")
						.formParam("lastMaintenance", "${lastMaintenance}")
					.formParam("_csrf", "${stoken}"))			
		})
		// CreatedPlane

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}