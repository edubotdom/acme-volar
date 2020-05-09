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

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("ClienteConsultaAvion")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(7)
		// Home
		.exec(http("request_1")
			.get("/login")
			.headers(headers_0))
		.pause(17)
		// LoginForm
		.exec(http("request_3")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "9ecbb5c9-444d-4bc4-9d68-1c4ba9daa4fb"))
		.pause(9)
		// Logged
		.exec(http("request_4")
			.get("/planes/1")
			.headers(headers_0))
		.pause(7)
		// ShowPlane

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}