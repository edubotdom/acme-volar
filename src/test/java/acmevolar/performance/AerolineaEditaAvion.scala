package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AerolineaEditaAvion extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.jpg""", """.*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.jar""", """.*.xz""", """.*.xml""", """.*.dif""", """.*.klz"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,en-US;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_1 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	Object EditPlane {
		var editPlane = exec(http("EditPlane")
			.get("/planes/1/edit")
			.headers(headers_0))
		.pause(42)
	}

	Object EditedPlane {
		var editedPlane = exec(http("EditedPlane")
			.post("/planes/1/edit")
			.headers(headers_1)
			.formParam("id", "1")
			.formParam("reference", "V14-5")
			.formParam("maxSeats", "150")
			.formParam("description", "The best plane in tha world")
			.formParam("manufacter", "Boeing")
			.formParam("model", "B747")
			.formParam("numberOfKm", "500000.23")
			.formParam("maxDistance", "2000000.0")
			.formParam("lastMaintenance", "2011-04-17")
			.formParam("_csrf", "e59c9a10-054b-4846-9e4e-3ce04fc55081"))
		.pause(21)
	}

	val scn = scenario("AerolineaEditaAvion").exec(EditPlane.editPlane,
		EditedPlane.editedPlane)
		
		// EditPlane
		
		// EditedPlane

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}