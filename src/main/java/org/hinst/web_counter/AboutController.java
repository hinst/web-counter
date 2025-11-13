package org.hinst.web_counter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {
	@RequestMapping("/about")
	public String index() {
		return "about.html";
	}
}
