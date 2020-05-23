package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RunwaySimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18363")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}  

	object LoginAirline {
		val loginAirline = exec(http("LoginAirline")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("request_2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	object ListAirports {
		val listAirports = exec(http("ListAirports")
			.get("/airports")
			.headers(headers_0))
		.pause(11)
	}

	object ShowAirport {
		val showAirport = exec(http("ShowAirport")
			.get("/airports/1")
			.headers(headers_0))
		.pause(11)
	}

	object ListRunways {
		val listRunways = exec(http("ListRunways")
			.get("/airports/1/runways")
			.headers(headers_0))
		.pause(11)
	}

	object CreateRunway {
		val createRunway = exec(http("CreateRunway")
			.get("/airports/1/runways/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(52)
		.exec(http("request_7")
			.post("/airports/1/runways/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "A-80")
			.formParam("runwayType", "landing")
			.formParam("_csrf", "${stoken}"))
		.pause(24)
	}

	object EditRunway {
		val editRunway = exec(http("EditRunway")
			.get("/airports/1/runways/1/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("request_9")
			.post("/airports/1/runways/1/edit")
			.headers(headers_3)
			.formParam("id", "1")
			.formParam("name", "A-88")
			.formParam("runwayType", "landing")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}

	object DeleteRunway {
		val deleteRunway = exec(http("DeleteRunway")
			.get("/airports/1/runways/1/delete")
			.headers(headers_0))
		.pause(5)
	}


	val scnAirlineListRunways = scenario("AirlineListRunways").exec(Home.home, LoginAirline.loginAirline, ListAirports.listAirports, ShowAirport.showAirport, ListRunways.listRunways)
	val scnAirlineCreateRunway = scenario("AirlineCreateRunway").exec(Home.home, LoginAirline.loginAirline, CreateRunway.createRunway)
	val scnAirlineEditRunway = scenario("AirlineEditRunway").exec(Home.home, LoginAirline.loginAirline, EditRunway.editRunway)
	val scnAirlineDeleteRunway = scenario("AirlineDeleteRunway").exec(Home.home, LoginAirline.loginAirline, DeleteRunway.deleteRunway)
		

	setUp(
		scnAirlineListRunways.inject(rampUsers(2000) during (100 seconds)),
		scnAirlineCreateRunway.inject(rampUsers(2000) during (100 seconds)),
		scnAirlineEditRunway.inject(rampUsers(2000) during (100 seconds)),
		scnAirlineDeleteRunway.inject(rampUsers(2000) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(global.responseTime.max.lt(5000),forAll.failedRequests.percent.lte(5),global.successfulRequests.percent.gt(90)).maxDuration(10 minutes)
}