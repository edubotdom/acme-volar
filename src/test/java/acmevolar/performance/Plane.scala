package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Plane extends Simulation {

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

	val headers_7 = Map("Proxy-Connection" -> "keep-alive")

	val headers_8 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	val headers_9 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_10 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_11 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object AirlineLogin {
		val airlineLogin = exec(
			http("AirlineLogin")
				.get("/login")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken")))
			.pause(25)
			.exec(
				http("AirlineLogged")
					.post("/login")
					.headers(headers_3)
					.formParam("username", "airline1")
					.formParam("password", "airline1")
					.formParam("_csrf", "${stoken}"))
			.pause(43)
	}

	object CreatePlane {
		val createPlane = exec(
			http("CreatePlane")
				.get("/planes/new")
				.headers(headers_0)
				.check(css("input[name=_csrf]", "value").saveAs("stoken")))
			.pause(59)
			.exec(
				http("CreatedPlane")
					.post("/planes/new")
					.headers(headers_3)
					.formParam("id", "")
					.formParam("reference", "V12-1")
					.formParam("maxSeats", "12")
					.formParam("description", "The best plane in tha world 56")
					.formParam("manufacter", "MomoIndustries")
					.formParam("model", "SuperFlightMMx2")
					.formParam("numberOfKm", "513546")
					.formParam("maxDistance", "90000000")
					.formParam("lastMaintenance", "2020-05-12")
					.formParam("_csrf", "${stoken}"))
			.pause(13)
	}

	object EditPlane {
		val editPlane = exec(
				http("EditPlane")
					.get("/planes/1/edit")
					.headers(headers_7)
					.check(css("input[name=_csrf]", "value").saveAs("stoken")))
			.pause(42)
			.exec(
				http("EditedPlane")
					.post("/planes/1/edit")
					.headers(headers_8)
					.formParam("id", "1")
					.formParam("reference", "V14-5")
					.formParam("maxSeats", "150")
					.formParam("description", "The best plane in tha world")
					.formParam("manufacter", "Boeing")
					.formParam("model", "B747")
					.formParam("numberOfKm", "500000.23")
					.formParam("maxDistance", "2000000.0")
					.formParam("lastMaintenance", "2011-04-17")
					.formParam("_csrf", "${stoken}"))
			.pause(21)
	}

	object ListPlanes {
		val listPlanes = exec(
				http("ListPlanes")
					.get("/my_planes")
					.headers(headers_0))
		.pause(25)
	}

	object ShowPlaneAirline {
		val showPlaneAirline = exec(
				http("ShowPlaneAirline")
					.get("/planes/1")
					.headers(headers_7))
	}

	object ClientLogin {
		val clientLogin = exec(
				http("ClientLogin")
					.get("/login")
					.headers(headers_9)
					.check(css("input[name=_csrf]", "value").saveAs("stoken")))
			.pause(17)
			// LoginForm
			.exec(
				http("ClientLogged")
					.post("/login")
					.headers(headers_11)
					.formParam("username", "client1")
					.formParam("password", "client1")
					.formParam("_csrf", "${stoken}"))
			.pause(9)
	}

	object ShowPlaneClient {
		val showPlaneClient = exec(
				http("ShowPlaneClient")
					.get("/planes/1")
					.headers(headers_9))
			.pause(7)
	}

	val airlinesScn = scenario("Airlines").exec(
		AirlineLogin.airlineLogin,
		CreatePlane.createPlane,
		EditPlane.editPlane,
		ListPlanes.listPlanes,
		ShowPlaneAirline.showPlaneAirline
	)
	
	val clientsScn = scenario("Clients").exec(
		ClientLogin.clientLogin,
		ShowPlaneClient.showPlaneClient
	)

	setUp(
		airlinesScn.inject(rampUsers(1000) during (100 seconds)),
		clientsScn.inject(rampUsers(500) during (100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)

}