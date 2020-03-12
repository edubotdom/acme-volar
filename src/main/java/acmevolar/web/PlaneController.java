
package acmevolar.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import acmevolar.model.Plane;
import acmevolar.service.PlaneService;

@Controller
public class PlaneController {

	private final PlaneService planeService;


	@Autowired
	public PlaneController(final PlaneService planeService) {
		this.planeService = planeService;
	}

	@GetMapping(value = {
		"/planes"
	})
	public String showPlaneList(final Map<String, Object> model) {
		Collection<Plane> planes = new ArrayList<Plane>();
		planes.addAll(this.planeService.findPlanes());
		model.put("planes", planes);
		return "planes/planesList";
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param flightId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/planes/{planeId}")
	public ModelAndView showPlane(@PathVariable("planeId") final int planeId) {
		ModelAndView mav = new ModelAndView("planes/planeDetails");
		mav.addObject(this.planeService.findPlaneById(planeId));
		return mav;
	}

}
