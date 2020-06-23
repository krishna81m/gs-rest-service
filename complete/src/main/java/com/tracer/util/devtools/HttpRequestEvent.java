package com.tracer.util.devtools;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestEvent extends Event {

    public static enum HTTP_REQUEST_EVENT_TYPES {
        REST_GET_REQUEST,
        REST_GET_RESPONSE,
        REST_POST_REQUEST,
        REST_POST_RESPONSE
    }

    String urlPath;
    String servletPath;
    String remoteAddress;

    List<RequestParam> requestParams = new ArrayList<>();
    HttpHeaders httpHeaders;
    String requestBody;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

}
