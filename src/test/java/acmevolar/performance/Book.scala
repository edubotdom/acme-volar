package acmevolar.model;

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Book extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.doNotTrackHeader("1")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com")

	object Home {
		val home=exec(http("Home")
			.get("/"))
		.pause(10)
	}
// i.g.h.a.HttpRequestAction - 'httpRequest-42' failed to execute: No attribute named 'stoken' is defined
	object LoginFormClient{
		val loginFormClient = exec(http("LoginFormClient")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))	
		).pause(16)
		.exec(http("LoggedClient")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	object LoginFormAirline{
		val loginFormAirline = exec(http("LoginFormAirline")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))	
		).pause(16)
		.exec(http("LoggedAirline")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "${stoken}"))
		.pause(16)
	}

	object BookListClient{
		val bookListClient = exec(http("BookListClient")
			.get("/books/client"))
		.pause(16)
	}

	object BookListAirline {
		val bookListAirline = exec(http("BookListAirline")
			.get("/books/airline"))
		.pause(13)
	}

	object FlightList{
		val flightList= exec(http("FlightList")
			.get("/flights"))
		.pause(6)
	}

	object FlightShow{
		val flightShow= exec(http("FlightShow")
			.get("/flights/3"))
		.pause(29)
	}

	object CreateBook{
		val createBook=exec(http("CreateBook")
			.get("/books/3/new")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))	
		).pause(18)
		.exec(http("BookCreated")
			.post("/books/3/new")
			.headers(headers_3)
			.formParam("quantity", "3")
			.formParam("price", "0")
			.formParam("moment", "")
			.formParam("_csrf", "${stoken}"))
		.pause(10)
	}

	object EditBook{
		val editBook=exec(http("EditBook")
			.get("/books/1/edit")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))	
		).pause(20)
		.exec(http("EditedBook")
			.post("/books/1/edit")
			.headers(headers_3)
			.formParam("price", "0")
			.formParam("moment", "2020-05-05")
			.formParam("quantity", "2")
			.formParam("bookStatusType", "cancelled")
			.formParam("_csrf", "${stoken}"))
		.pause(14)
	}


	val scnBookClientShow = scenario("Clients show books").exec(Home.home,LoginFormClient.loginFormClient,BookListClient.bookListClient)
	val scnBookClientList = scenario("Clients list books").exec(Home.home,LoginFormClient.loginFormClient,BookListClient.bookListClient)
	val scnBookClientCreate = scenario("Clients create books").exec(Home.home,LoginFormClient.loginFormClient,FlightList.flightList, FlightShow.flightShow, CreateBook.createBook)


	val scnBookAirlineList = scenario("Airline list books").exec(Home.home,LoginFormAirline.loginFormAirline,BookListAirline.bookListAirline)
	val scnBookAirlineShow = scenario("Airline show books").exec(Home.home,LoginFormAirline.loginFormAirline,BookListAirline.bookListAirline)
	val scnBookAirlineUpdate = scenario("Airline update books").exec(Home.home,LoginFormAirline.loginFormAirline,BookListAirline.bookListAirline, EditBook.editBook)

	
	
	
	
	setUp(
		//nothingFor(5 seconds),
		scnBookClientShow.inject(rampUsers(2000) during (100 seconds)),
		scnBookClientList.inject(rampUsers(2000) during (100 seconds)),
		scnBookClientCreate.inject(rampUsers(2000) during (100 seconds)),
		scnBookAirlineList.inject(rampUsers(2000) during (100 seconds)),
		scnBookAirlineShow.inject(rampUsers(2000) during (100 seconds)),
		scnBookAirlineUpdate.inject(rampUsers(2000) during (100 seconds))
		).protocols(httpProtocol)
	.assertions(global.responseTime.max.lt(5000),forAll.failedRequests.percent.lte(5),global.successfulRequests.percent.gt(90)).maxDuration(10 minutes)

}