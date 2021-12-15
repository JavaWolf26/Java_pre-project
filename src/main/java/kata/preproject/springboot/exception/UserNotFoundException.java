package kata.preproject.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

	public UserNotFoundException(Long id) {
		super(HttpStatus.NOT_FOUND, "Could not find user with ID = " + id);
	}
}
