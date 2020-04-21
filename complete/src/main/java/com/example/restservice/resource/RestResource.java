package com.example.restservice.resource;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.example.restservice.service.RestService;
import com.paycycle.util.devtools.HttpRequestEvent;
import com.paycycle.util.devtools.HttpResponseEvent;
import com.paycycle.util.devtools.RequestTrace;
import com.paycycle.util.devtools.ThreadEvent;
import com.example.restservice.PersonalGreeting;
import com.paycycle.rest.common.Result;
import com.paycycle.util.devtools.aspect.MethodTraceAspect;
import com.paycycle.util.devtools.helper.RequestTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html
@RestController
public class RestResource {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	RestService restService;

	@Autowired
	MethodTraceAspect methodTraceAspect;

	@GetMapping("/greeting")
	public PersonalGreeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new PersonalGreeting(counter.incrementAndGet(), String.format(template, name));
	}

	// https://spring.io/guides/gs/rest-service-cors/
	// https://stackoverflow.com/questions/43317967/handle-response-syntaxerror-unexpected-end-of-input-when-using-mode-no-cors?rq=1
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/request")
	public Result request(@RequestParam(value = "id", defaultValue = "last") String lastName) {
		PersonalGreeting personalGreeting = restService.getGreeting(lastName);
		try {
			return Result.ResultBuilder
					.aResult()
					.data(
						RequestTracer.getCurrentRequest()
						// prepareRequest()
					)
					.build();
		} finally{
			// this won't work in our case as RestResource
			// 		as this method has not returned its response and not added to method stack yet
			// methodTraceAspect.printTrace();
		}
	}

	private RequestTrace prepareRequest() {
		RequestTrace httpRequest  = new RequestTrace();
		httpRequest.setTid("123");
		httpRequest.setStartTime(new Date());

		// setup default thread that will store events
		ThreadEvent threadEvent = new ThreadEvent();
		threadEvent.setThreadId("123");

		httpRequest.setRequest(new HttpRequestEvent());
		httpRequest.setResponse(new HttpResponseEvent());

		return httpRequest;
	}
}
