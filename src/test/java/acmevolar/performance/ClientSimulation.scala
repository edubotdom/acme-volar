package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ClientSimulation extends Simulation {

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

	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")



	val scn = scenario("ClientSimulation")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(10)
		// Home
		.exec(http("Register")
			.get("/clients/new")
			.headers(headers_0))
		.pause(92)
		// Register
		.exec(http("Registered")
			.post("/clients/new")
			.headers(headers_2)
			.formParam("name", "Pepito Camotes Areto")
			.formParam("identification", "87654321-L")
			.formParam("birthDate", "1998/11/04")
			.formParam("email", "pcamarotes@gmail.com")
			.formParam("phone", "987654321")
			.formParam("user.username", "client4")
			.formParam("user.password", "client4")
			.formParam("_csrf", "d4ef9648-17d9-4000-a280-e519c5703792"))
		.pause(14)
		// Registered
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_4")
			.get("/login")
			.headers(headers_4)))
		.pause(19)
		// Login
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "client4")
			.formParam("password", "client4")
			.formParam("_csrf", "d4ef9648-17d9-4000-a280-e519c5703792"))
		.pause(16)
		// Logged
		.exec(http("Logout")
			.get("/logout")
			.headers(headers_0))
		.pause(14)
		// Logout
		.exec(http("Loggedout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "8ce153dd-a621-496c-b404-4d539197b644"))
		.pause(12)
		// Loggedout

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}