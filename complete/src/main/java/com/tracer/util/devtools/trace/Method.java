package com.tracer.util.devtools.trace;

import java.util.ArrayList;
import java.util.List;

public class Method {

    public String methodName;
    public List<MethodArg> inputArgs = new ArrayList<>();
    public List<Method> childMethods = new ArrayList<>();
    public MethodArg outputArg;
    private Long timeInMs;

    public Long getTimeInMs() {
        return timeInMs;
    }

    public void setTimeInMs(Long timeInMs) {
        this.timeInMs = timeInMs;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<MethodArg> getInputArgs() {
        return inputArgs;
    }

    public void setInputArgs(List<MethodArg> inputArgs) {
        this.inputArgs = inputArgs;
    }

    public List<Method> getChildMethods() {
        return childMethods;
    }

    public void setChildMethods(List<Method> childMethods) {
        this.childMethods = childMethods;
    }

    public MethodArg getOutputArg() {
        return outputArg;
    }

    public void setOutputArg(MethodArg outputArg) {
        this.outputArg = outputArg;
    }

}
