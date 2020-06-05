package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AerolineaCreaAeropuertoCSV extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

    val csvFeeder = csv("airports.csv").random

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

        .feed(csvFeeder)
            .exec(http("request_17")
                .post("/airports/new")
                .headers(headers_3)
                .formParam("name", "${name}")
                .formParam("maxNumberOfPlanes", "${maxNumberOfPlanes}")
                .formParam("maxNumberOfClients", "${maxNumberOfClients}")
                .formParam("latitude", "${latitude}")
                .formParam("longitude", "${longitude}")
                .formParam("code", "${code}")
                .formParam("city", "${city}")
                .formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	val scnAirlineCreateAirport = scenario("AirlineCreateAirport").exec(Home.home, LoginAirline.loginAirline, CreateAirport.createAirport)
			

	setUp(
		scnAirlineCreateAirport.inject(rampUsers(1000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(global.responseTime.max.lt(5000),forAll.failedRequests.percent.lte(5),global.successfulRequests.percent.gt(90)).maxDuration(10 minutes)
}