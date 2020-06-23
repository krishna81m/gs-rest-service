package com.tracer.util.devtools.trace;

public class MethodArg {

    // FIXME: should clone or not use object at all, serialized representation is enough
    public Object arg;
    public String argAsStr;

    public MethodArg(Object arg, String argAsStr) {
        this.arg = arg;
        this.argAsStr = argAsStr;
    }

    public static MethodArg of(Object arg, String argAsStr) {
        return new MethodArg(arg, argAsStr);
    }
}
