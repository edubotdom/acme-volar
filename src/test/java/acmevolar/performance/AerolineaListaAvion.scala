package simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AerolineaListaAvion extends Simulation {

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

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es,en-US;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es,en-US;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-CryptoAPI/10.0")

	Object Login {
		var login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/login")
			.headers(headers_1)))
		.pause(17)
	}

	Obejct Logged {
		var logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "4f7a88b4-7290-4a53-bc6e-958de9ea5619"))
		.pause(22) 
	}

	Object ListPlanes {
		var listPlanes = exec(http("ListPlanes")
			.get("/my_planes")
			.headers(headers_0))
		.pause(25)
	}

	val scn = scenario("AerolineaListaAvion").exec(Login.login,
		Logged.logged,
		ListPlanes.listPlanes)
		
		// Login
		
		// Logged
		
		// ListPlanes

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}