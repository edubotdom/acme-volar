package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AirportSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_11 = Map(
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-WNS/10.0")

	val headers_22 = Map(
		"A-IM" -> "x-bm,gzip",
		"Accept-Encoding" -> "gzip, deflate",
		"Proxy-Connection" -> "keep-alive")

	val headers_33 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-CryptoAPI/10.0")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"
    val uri3 = "http://tile-service.weather.microsoft.com/es-ES/livetile/preinstall"
    val uri4 = "http://ctldl.windowsupdate.com/msdownload/update/v3/static/trustedr/en"

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
	}  

	object LoginClient {
		val loginClient = exec(http("LoginClient")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(24)
		.exec(http("LoggedClient")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "${stoken}"))
		.pause(89)
	}

	object ListAirport {
		val listAirport = exec(http("ListAirport")
			.get("/airports")
			.headers(headers_0))
		.pause(25)
	}

	object ShowAirport {
		val showAirport = exec(http("ShowAirport")
			.get("/airports/1")
			.headers(headers_0))
		.pause(82)
	}

	object LoginAirline {
		val loginAirline = exec(http("LoginAirline")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_9")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(19)
		.exec(http("request_10")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	object CreateAirport {
		val createAirport = exec(http("CreateAirport")
			.get("/airports/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(51)
		.exec(http("request_17")
			.post("/airports/new")
			.headers(headers_3)
			.formParam("name", "Sevilla Airport 2")
			.formParam("maxNumberOfPlanes", "456")
			.formParam("maxNumberOfClients", "4564")
			.formParam("latitude", "37.433")
			.formParam("longitude", "-80.67")
			.formParam("code", "SVA")
			.formParam("city", "Sevilla")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	object EditAirport {
		val editAirport = exec(http("EditAirport")
			.get("/airports/1/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(14)		
		.exec(http("request_21")
			.post("/airports/1/edit")
			.headers(headers_3)
			.formParam("name", "Sevilla Airport Editado")
			.formParam("maxNumberOfPlanes", "50")
			.formParam("maxNumberOfClients", "600")
			.formParam("latitude", "37.418")
			.formParam("longitude", "-5.89311")
			.formParam("code", "SVQ")
			.formParam("city", "Sevilla")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_22")
			.get(uri1 + "?osname=win&channel=stable&milestone=81")
			.headers(headers_22)))
		.pause(20)
	}

	object DeleteAirport {
		val deleteAirport = exec(http("DeleteAirport")
			.get("/airports/11")
			.headers(headers_0)
			.resources(http("request_44")
			.get("/airports/11/delete")
			.headers(headers_0)))
		.pause(8)
	}



	val scnClientListAirports = scenario("ClientListAirports").exec(Home.home, LoginClient.loginClient, ListAirport.listAirport)
	val scnClientShowAirport = scenario("ClientShowAirport").exec(Home.home, LoginClient.loginClient, ListAirport.listAirport, ShowAirport.showAirport)

	val scnAirlineListAirports = scenario("AirlineListAirports").exec(Home.home, LoginAirline.loginAirline, ListAirport.listAirport)
	val scnAirlineShowAirport = scenario("AirlineShowAirport").exec(Home.home, LoginAirline.loginAirline, ListAirport.listAirport, ShowAirport.showAirport)
	val scnAirlineCreateAirport = scenario("AirlineCreateAirport").exec(Home.home, LoginAirline.loginAirline, CreateAirport.createAirport)
	val scnAirlineEditAirport = scenario("AirlineEditAirport").exec(Home.home, LoginAirline.loginAirline, EditAirport.editAirport)	
	val scnAirlineDeleteAirport = scenario("AirlineDeleteAirport").exec(Home.home, LoginAirline.loginAirline, DeleteAirport.deleteAirport)
		
		

	setUp(
		scnClientListAirports.inject(rampUsers(1000) during (100 seconds)),
		scnClientShowAirport.inject(rampUsers(1000) during (100 seconds)),
		scnAirlineListAirports.inject(rampUsers(1000) during (100 seconds)),
		scnAirlineShowAirport.inject(rampUsers(1000) during (100 seconds)),
		scnAirlineCreateAirport.inject(rampUsers(1000) during (100 seconds)),
		scnAirlineEditAirport.inject(rampUsers(1000) during (100 seconds)),
		scnAirlineDeleteAirport.inject(rampUsers(1000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(global.responseTime.max.lt(5000),forAll.failedRequests.percent.lte(5),global.successfulRequests.percent.gt(90)).maxDuration(10 minutes)
}