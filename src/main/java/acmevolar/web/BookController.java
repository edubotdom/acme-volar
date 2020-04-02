
package acmevolar.web;

import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.service.BookService;
import acmevolar.service.FlightService;

@Controller
public class BookController {

	private final BookService	bookService;
	private final FlightService	flightService;

	private static final String	VIEWS_BOOKS_CREATE_OR_UPDATE_FORM	= "books/createBookForm";


	@Autowired
	public BookController(final BookService bookService, final FlightService flightService) {
		this.bookService = bookService;
		this.flightService = flightService;
	}
	/*
	 * @GetMapping(value = {
	 * "/planes"
	 * })
	 * public String showPlaneList(final Map<String, Object> model) {
	 * Collection<Plane> planes = this.planeService.findPlanes();
	 * model.put("planes", planes);
	 * return "planes/planesList";
	 * }
	 *
	 * @GetMapping(value = {
	 * "/my_planes"
	 * })
	 * public String showMyPlaneList(final Map<String, Object> model) {
	 *
	 * // first, we get the airline role
	 * String username = SecurityContextHolder.getContext().getAuthentication().getName();
	 *
	 * // now, we substract the planes created by the same airline
	 * Collection<Plane> planes = this.planeService.getAllPlanesFromAirline(username);
	 *
	 * model.put("planes", planes);
	 * return "planes/planesList";
	 * }
	 *
	 * @GetMapping("/planes/{planeId}")
	 * public ModelAndView showPlane(@PathVariable("planeId") final int planeId) {
	 * ModelAndView mav = new ModelAndView("planes/planeDetails");
	 * mav.addObject(this.planeService.findPlaneById(planeId));
	 * return mav;
	 * }
	 *
	 * @InitBinder("airline")
	 * public void initAirlineBinder(final WebDataBinder dataBinder) {
	 * dataBinder.setDisallowedFields("id");
	 * }
	 */

	@PreAuthorize("hasAuthority('client')")
	@GetMapping(value = "/books/{flightId}/new")
	public String initCreationForm(@PathVariable("flightId") final int flightId, final Map<String, Object> model) {
		Book book = new Book();
		BookStatusType bookStatusType = this.bookService.findBookStatusTypeById(1);
		book.setBookStatusType(bookStatusType);
		LocalDate date = LocalDate.now();
		book.setMoment(date);
		Flight flight = this.flightService.findFlightById(flightId);
		String clientUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Client client = this.bookService.findClientByUsername(clientUsername);
		book.setClient(client);
		book.setFlight(flight);

		model.put("flight", flight);
		model.put("book", book);

		return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;
	}

	@PreAuthorize("hasAuthority('client')")
	@PostMapping(value = "/books/{flightId}/new")
	public String processCreationForm(@PathVariable("flightId") final int flightId, @Valid final Book book, final BindingResult result, final Map<String, Object> model) {

		if (result.hasErrors()) {

			Flight flight = this.flightService.findFlightById(flightId);
			//	Client client = SecurityContextHolder.getContext().getAuthentication().;

			model.put("flight", flight);
			return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;
		} else {
			BookStatusType bookStatusType = this.bookService.findBookStatusTypeById(1);
			book.setBookStatusType(bookStatusType);
			LocalDate date = LocalDate.now();
			book.setMoment(date);
			Flight flight = this.flightService.findFlightById(flightId);
			String clientUsername = SecurityContextHolder.getContext().getAuthentication().getName();
			Client client = this.bookService.findClientByUsername(clientUsername);
			book.setClient(client);
			book.setFlight(flight);

			this.bookService.saveBook(book);

			return "redirect:/flights";
		}
	}
	/*
	 * @PreAuthorize("hasAuthority('airline')")
	 *
	 * @GetMapping(value = "/planes/{planeId}/edit")
	 * public String initUpdateForm(@PathVariable("planeId") final int planeId, final ModelMap model) {
	 * Plane plane = this.planeService.findPlaneById(planeId);
	 *
	 * model.put("plane", plane);
	 *
	 * return BookController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
	 * }
	 *
	 * @PreAuthorize("hasAuthority('airline')")
	 *
	 * @PostMapping(value = "/planes/{planeId}/edit")
	 * public String processUpdateForm(@Valid final Plane plane, final BindingResult result, @PathVariable("planeId") final int planeId, final ModelMap model) {
	 * if (result.hasErrors()) {
	 * model.put("plane", plane);
	 * return BookController.VIEWS_PLANES_CREATE_OR_UPDATE_FORM;
	 * } else {
	 * Plane planeToUpdate = this.planeService.findPlaneById(planeId);
	 * BeanUtils.copyProperties(planeToUpdate, plane, "reference", "maxSeats", "description", "manufacter", "model", "numberOfKm", "maxDistance", "lastMaintenance");
	 *
	 * try {
	 * this.planeService.savePlane(plane);
	 * ;
	 * } catch (DataAccessException e) {
	 * e.printStackTrace();
	 * }
	 *
	 * return "redirect:/planes/{planeId}";// + plane.getId();
	 * }
	 * }
	 *
	 * @InitBinder("plane")
	 * public void initFlightBinder(final WebDataBinder dataBinder) {
	 * dataBinder.setValidator(new PlaneValidator());
	 * }
	 */

}
