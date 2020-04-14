package com.example.restservice;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.paycycle.util.devtools.HttpRequest;
import com.paycycle.util.devtools.ThreadEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	// https://spring.io/guides/gs/rest-service-cors/
	// https://stackoverflow.com/questions/43317967/handle-response-syntaxerror-unexpected-end-of-input-when-using-mode-no-cors?rq=1
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/request")
	public HttpRequest request(@RequestParam(value = "id", defaultValue = "") String id) {
		return prepareRequest();
	}

	private HttpRequest prepareRequest() {
		HttpRequest httpRequest  = new HttpRequest();
		httpRequest.setTid("123");
		httpRequest.setStartTime(new Date());

		// setup default thread that will store events
		ThreadEvent threadEvent = new ThreadEvent();
		threadEvent.setThreadId("123");

		httpRequest.setRequest("HttpRequest");
		httpRequest.setResponse("HttpResponse");

		return httpRequest;
	}
}
