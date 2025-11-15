package org.hinst.web.counter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
	@Value("${spring.application.name}")
	private String applicationName;

	@GetMapping("/about")
	public String index() {
		return "about.html";
	}
}
