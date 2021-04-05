package ca.kirti.jdbc.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {
	@GetMapping("/")
	public String home() {
		return  "Welcome to Spring JDBC!";
				
	}

	@GetMapping("/user")
	public String user() {
		return  "Welcome to Spring User page!";
				
	}
	@GetMapping("/admin")
	public String admin() {
		return  "Welcome to Spring Admin page!";
				
	}
}
