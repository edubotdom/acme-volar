package acmevolar.model;

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Book extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,de-DE;q=0.8,de;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36")

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

	object Home {
		val home=exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object LoginForm{
		val loginForm = exec(http("LoginForm")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(16)
	}

	object LoggedClient{
		val loggedClient = exec(http("LoggedClient")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "client1")
			.formParam("password", "client1")
			.formParam("_csrf", "92c8ea29-9a83-426a-adcf-85e76f77c57b"))
		.pause(16)
	}

	object LoggedAirline {
		val loggedAirline = exec(http("LoggedAirline")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "airline1")
			.formParam("password", "airline1")
			.formParam("_csrf", "0ca7f782-0c50-4e4f-8bf8-f251d2c3a7eb"))
		.pause(13)
	}

	object BookListClient{
		val bookListClient = exec(http("BookListClient")
			.get("/books/client")
			.headers(headers_0))
		.pause(16)
	}

	object BookListAirline {
		val bookListAirline = exec(http("BookListAirline")
			.get("/books/airline")
			.headers(headers_0))
		.pause(13)
	}

	object FlightList{
		val flightList= exec(http("FlightList")
			.get("/flights")
			.headers(headers_0))
		.pause(6)
	}

	object FlightShow{
		val flightShow= exec(http("FlightShow")
			.get("/flights/3")
			.headers(headers_0))
		.pause(29)
	}

	object CreateBook{
		val createBook=exec(http("CreateBook")
			.get("/books/3/new")
			.headers(headers_0))
		.pause(18)
	}

	object EditBook{
		val editBook=exec(http("EditBook")
			.get("/books/1/edit")
			.headers(headers_0))
		.pause(20)
	}

	object BookCreated{
		val bookCreated=exec(http("BookCreated")
			.post("/books/3/new")
			.headers(headers_3)
			.formParam("quantity", "3")
			.formParam("price", "0")
			.formParam("moment", "")
			.formParam("_csrf", "43d054d9-80dd-471e-9dc5-64bf6d93e64d"))
		.pause(10)
	}

	object EditedBook{
		val editedBook=exec(http("request_6")
			.post("/books/1/edit")
			.headers(headers_3)
			.formParam("price", "0")
			.formParam("moment", "2020-05-05")
			.formParam("quantity", "2")
			.formParam("bookStatusType", "cancelled")
			.formParam("_csrf", "f3660a2f-9a5d-429c-80ba-3f6dee06f105"))
		.pause(14)
	}

	//val scnBookClient = scenario("Book clients").exec(Home.home,LoginForm.loginForm,LoggedClient.loggedClient,BookListClient.bookListClient, FlightList.flightList, FlightShow.flightShow, CreateBook.createBook,BookCreated.bookCreated)

	//val scnBookAirline = scenario("Book airlines").exec(Home.home,LoginForm.loginForm,LoggedAirline.loggedAirline,BookListAirline.bookListAirline, EditBook.editBook, EditedBook.editedBook)



	val scnBookClientShow = scenario("Clients show books").exec(Home.home,LoginForm.loginForm,LoggedClient.loggedClient,BookListClient.bookListClient)
	val scnBookClientList = scenario("Clients list books").exec(Home.home,LoginForm.loginForm,LoggedClient.loggedClient,BookListClient.bookListClient)
	val scnBookClientCreate = scenario("Clients create books").exec(Home.home,LoginForm.loginForm,LoggedClient.loggedClient,FlightList.flightList, FlightShow.flightShow, CreateBook.createBook,BookCreated.bookCreated)


	val scnBookAirlineList = scenario("Airline list books").exec(Home.home,LoginForm.loginForm,LoggedAirline.loggedAirline,BookListAirline.bookListAirline)
	val scnBookAirlineShow = scenario("Airline show books").exec(Home.home,LoginForm.loginForm,LoggedAirline.loggedAirline,BookListAirline.bookListAirline)
	val scnBookAirlineUpdate = scenario("Airline update books").exec(Home.home,LoginForm.loginForm,LoggedAirline.loggedAirline,BookListAirline.bookListAirline, EditBook.editBook, EditedBook.editedBook)

	
	
	
	
	setUp(
		//nothingFor(5 seconds),
		scnBookClientShow.inject(rampUsers(3000) during (100 seconds)),
		scnBookClientList.inject(rampUsers(3000) during (100 seconds)),
		scnBookClientCreate.inject(rampUsers(3000) during (100 seconds)),
		scnBookAirlineList.inject(rampUsers(3000) during (100 seconds)),
		scnBookAirlineShow.inject(rampUsers(3000) during (100 seconds)),
		scnBookAirlineUpdate.inject(rampUsers(3000) during (100 seconds))
		).protocols(httpProtocol)
	.assertions(global.responseTime.max.lt(5000),forAll.failedRequests.percent.lte(5),global.successfulRequests.percent.gt(90)).maxDuration(10 minutes)

}