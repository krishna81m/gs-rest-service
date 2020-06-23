package com.tracer.util.devtools;

import java.util.ArrayList;
import java.util.List;

public class HttpHeaders {

    List<HttpHeader> headers = new ArrayList<>();

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public void addHeader(HttpHeader header) {
        headers.add(header);
    }
}
