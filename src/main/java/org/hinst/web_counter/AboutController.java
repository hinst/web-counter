package org.hinst.web_counter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {
	@Value("${spring.application.name}")
	private String applicationName;

	@RequestMapping("/about")
	public String index() {
		return "about.html";
	}
}
