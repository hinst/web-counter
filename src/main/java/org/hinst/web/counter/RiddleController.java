package org.hinst.web.counter;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/riddle")
@RequiredArgsConstructor
public class RiddleController {
	private final RiddleManager riddleManager;

	@GetMapping("/prime-numbers")
	public ResponseEntity<int[]> getMethodName() throws IOException {
		return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
			.body(riddleManager.getPrimeNumbers());
	}

	@GetMapping("/generate")
	@ResponseBody
	public RiddleEntry generateRiddle() {
		return riddleManager.generate();
	}
}
