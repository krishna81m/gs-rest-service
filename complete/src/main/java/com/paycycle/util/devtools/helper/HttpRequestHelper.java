package com.paycycle.util.devtools.helper;

import com.paycycle.util.devtools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class HttpRequestHelper {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestHelper.class);

    //  MDC.put("ThreadId", String.valueOf(Thread.currentThread().getId()));
    public static final Map<String, HttpRequest> tIdRequestMap = new ConcurrentHashMap<>();

    // simple time based ordering of tIds
    // everytime an event is added/updated, it is removed and added/offered to list at head/tail
    // any tId which has respond endTime + grace period is removed
    public static ConcurrentLinkedQueue<TidContext> tIds = new ConcurrentLinkedQueue<>();

    public static HttpRequest createRequest(String httpRequestLog){
        // LoggingFilter sets intuit_id in threadlocal as well as MC setMDC(TID, newTid);
        // in TransactionIdUtil.createTransactionId() -> com.intuit.util.TransactionIdUtil.setIntuitTid

        // replaces http request with same threadId as key <<<
        String threadId = String.valueOf(Thread.currentThread().getId());
        String tId = TransactionIdUtil.getTransactionId();

        HttpRequest httpRequest  = new HttpRequest();
        httpRequest.setTid(tId);
        httpRequest.setStartTime(new Date());

        // setup default thread that will store events
        ThreadEvent threadEvent = new ThreadEvent();
        threadEvent.setThreadId(threadId);

        httpRequest.setRequest(httpRequestLog.toString());

        tIdRequestMap.put(tId, httpRequest);
        updateCache(tId);

        return httpRequest;
    }

    public static void updateResponse(HttpRequest httpRequest, String httpResponseLog){
        httpRequest.setResponse(httpResponseLog);
        httpRequest.setEndTime(new Date());
        // TODO: clean up cache
    }

    /*
     * doFilter puts the first event into
     *  tIdHttpRequestMapping for the incoming request threadId or tId
     */
    public static void addEvent(Event event){
        String tId = TransactionIdUtil.getTransactionId();
        if(tId != null) {
            addEvent(tId, event);
        } else {
            log.warn("HttpLoggingFilter failed to add event as there is no tId");
        }
    }
    public static void addDBEvent(String sql) {
        DBExecEvent dbEvent = new DBExecEvent();
        DBExecEventData data = new DBExecEventData();
        data.setSql(sql);
        // data.setParams();
        dbEvent.setData(data);

        addEvent(dbEvent);
    }

    public static void addEvent(String tId, Event event){
        HttpRequest httpRequest = tIdRequestMap.get(tId);
        if(httpRequest != null && httpRequest.getThreads().size() > 0) {
            // FIXME: add to the right thread
            httpRequest.getThreads().get(0).getEvents().add(event);
            updateCache(tId);
        } else {
            log.warn("HttpLoggingFilter failed to add event as there is no request for this tId");
        }
    }

    /*
     * keeps latest tId in the front
     */
    private static void updateCache(String tId) {
        TidContext tidContext = TidContext.of(tId);
        boolean removed = tIds.remove(tidContext);
        tIds.offer(tidContext);
        // updatetId(tId);

        // remove older requests from the back based on its last update timestamp
    }

    public static HttpRequest getCurrentRequest() {
        String tId = TransactionIdUtil.getTransactionId();
        return getRequestByTId(tId);
    }

    public static HttpRequest getRequestByTId(String tId) {
        return tIdRequestMap.get(tId);
    }

}

/**
 * equals/hashcode uses only tId
 */
class TidContext {

    public String tId;
    public Date date;

    public TidContext(String tId, Date date) {
        this.tId = tId;
        this.date = date;
    }

    public static TidContext of(String tId){
        return new TidContext(tId, new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TidContext that = (TidContext) o;
        return Objects.equals(tId, that.tId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tId);
    }
}
