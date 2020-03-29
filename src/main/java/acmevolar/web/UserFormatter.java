package acmevolar.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import acmevolar.model.User;
import acmevolar.service.UserService;

public class UserFormatter implements Formatter<User> {

	private final UserService userService;
	
	@Autowired
	public UserFormatter(final UserService userService) {
		this.userService=userService;
	}
	
	public String print(User user, Locale locale) {
		return user.getUsername();
	}

	public User parse(String text, Locale locale) throws ParseException {
		Collection<User> findUsers = this.userService.findAllUsers();
		for(User user : findUsers) {
			if(user.getUsername().equals(text)) {
				return user;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
