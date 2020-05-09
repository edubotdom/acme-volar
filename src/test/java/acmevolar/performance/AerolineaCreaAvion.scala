package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AerolineaCreaAvion extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.jpg""", """.*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.jar""", """.*.xz""", """.*.xml""", """.*.dif""", """.*.klz"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,en-US;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	Object ListPlanes {
		var listPlanes = exec(http("ListPlanes")
			.get("/my_planes")
			.headers(headers_0))
		.pause(19)
	}

	Object CreatePlane {
		var createPlane = exec(http("CreatePlane")
			.get("/planes/new?")
			.headers(headers_0))
		.pause(63)
	}

	Object CreatedPlane {
		var createdPlane = exec(http("CreatedPlane")
			.post("/planes/new")
			.headers(headers_2)
			.formParam("id", "")
			.formParam("reference", "V16-9")
			.formParam("maxSeats", "10")
			.formParam("description", "The smallest plane in the world")
			.formParam("manufacter", "Boeing")
			.formParam("model", "Mini 89")
			.formParam("numberOfKm", "513546")
			.formParam("maxDistance", "90000000")
			.formParam("lastMaintenance", "2020-05-01")
			.formParam("_csrf", "e59c9a10-054b-4846-9e4e-3ce04fc55081"))
		.pause(7)
	}


	val scn = scenario("AerolineaCreaAvion").exec(ListPlanes.listPlanes,
		CreatePlane.createPlane,
		CreatedPlane.createdPlane)
		
		// ListPlanes
		
		// CreatePlane
		
		// CreatedPlane

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}