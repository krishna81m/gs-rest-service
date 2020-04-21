package com.paycycle.util.devtools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * {
 *   "tid": "1",
 *   "request": "",
 *   "response": "",
 *   "threads": [
 *     {
 *       "threadId": "1",
 *       "threadName": "test",
 *       "events": [
 *         {
 *           "eventType": "method",
 *           "startTime": "",
 *           "endTime": ""
 *         },
 *         {
 *           "eventType": "database",
 *           "startTime": "",
 *           "endTime": ""
 *         }
 *       ]
 *     }
 *   ]
 * }
 * </pre>
 */
public class RequestTrace {

    String tid = "1";
    Date startTime = new Date();
    Date endTime = new Date();

    HttpRequestEvent request;
    HttpResponseEvent response;

    // support for async/parallel execution for same request
    final List<ThreadEvent> threadEvents = new ArrayList<>();

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public HttpRequestEvent getRequest() {
        return request;
    }

    public void setRequest(HttpRequestEvent request) {
        this.request = request;
    }

    public HttpResponseEvent getResponse() {
        return response;
    }

    public void setResponse(HttpResponseEvent response) {
        this.response = response;
    }

    public List<ThreadEvent> getThreadEvents() {
        return threadEvents;
    }
}
