package com.paycycle.rest.common;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private T data;
    private List<Error> errors;

    public T getData() {
        return data;
    }

    void setData(T data) {
        this.data = data;
    }

    public List<Error> getErrors() {
        return errors;
    }

    void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void addError(Error error) {
        if(this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }


    public static final class ResultBuilder<T> {
        private Result<T> result;

        private ResultBuilder() {
            result = new Result<T>();
        }

        public static <T> ResultBuilder aResult() {
            return new ResultBuilder<T>();
        }

        public ResultBuilder data(T data) {
            result.setData(data);
            return this;
        }

        public ResultBuilder errors(List<Error> errors) {
            result.setErrors(errors);
            return this;
        }

        public ResultBuilder addError(Error error) {
            result.addError(error);
            return this;
        }

        public Result build() {
            return result;
        }
    }
}
