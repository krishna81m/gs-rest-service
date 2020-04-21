package com.paycycle.util.devtools;

public class RequestParam extends NameValuePair<String, String> {

    public RequestParam(String key, String value) {
        this.name = key;
        this.value = value;
    }

    public static RequestParam of(String key, String value) {
        return new RequestParam(key, value);
    }
}
