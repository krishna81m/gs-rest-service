package com.paycycle.rest.common;

public class Error {
    private String code;
    private String type;
    private String field;
    private String message;
    private String detail;


    public String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    void setDetail(String detail) {
        this.detail = detail;
    }

    public static final class ErrorBuilder {
        private Error error;

        private ErrorBuilder() {
            error = new Error();
        }

        public static ErrorBuilder anError() {
            return new ErrorBuilder();
        }

        public ErrorBuilder code(String code) {
            error.setCode(code);
            return this;
        }

        public ErrorBuilder type(String type) {
            error.setType(type);
            return this;
        }

        public ErrorBuilder field(String field) {
            error.setField(field);
            return this;
        }

        public ErrorBuilder message(String message) {
            error.setMessage(message);
            return this;
        }

        public ErrorBuilder detail(String detail) {
            error.setDetail(detail);
            return this;
        }

        public Error build() {
            return error;
        }
    }
}
