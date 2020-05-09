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

	Object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	Object Register {
		val register = exec(http("Register")
			.get("/clients/new")
			.headers(headers_0))
		.pause(92)
	}

	Object Registered {
		val registered = exec(http("Registered")
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
	}

	Object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_4")
			.get("/login")
			.headers(headers_4)))
		.pause(19)
	}

	Object Logged {
		var logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "client4")
			.formParam("password", "client4")
			.formParam("_csrf", "d4ef9648-17d9-4000-a280-e519c5703792"))
		.pause(16)
	}

	Object Loggout{
		var loggout = exec(http("Logout")
			.get("/logout")
			.headers(headers_0))
		.pause(14)
	}

	Object LoggedOut {
		var loggedout = exec(http("Loggedout")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "8ce153dd-a621-496c-b404-4d539197b644"))
		.pause(12)
	}

	val scn = scenario("RegistrationAndLoginClient").exec(Home.home,
		Register.register,
		Registered.registered,
		Login.login,
		Logged.logged,
		Logout.loggout,
		LoggedOut.loggedout)
		
		// Home
		
		// Register
		
		// Registered
		
		// Login
		
		// Logged
		
		// Logout
		
		// Loggedout

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}