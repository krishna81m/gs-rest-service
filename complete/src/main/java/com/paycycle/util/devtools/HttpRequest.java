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
public class HttpRequest {

    String tid = "1";
    Date startTime = new Date();
    Date endTime = new Date();

    String request;
    String response;

    // support for async/parallel execution for same request
    List<ThreadEvent> threads = new ArrayList<>();

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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ThreadEvent> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadEvent> threads) {
        this.threads = threads;
    }
}
