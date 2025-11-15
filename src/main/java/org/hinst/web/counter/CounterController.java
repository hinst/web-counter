package org.hinst.web.counter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
	@PostMapping(value = "/ping")
	public String ping(@RequestParam String url) {
		System.out.println("ping");
		return "ok";
	}
}
