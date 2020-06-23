package com.tracer.util.devtools.helper;

import com.tracer.util.devtools.trace.Method;
import com.tracer.util.devtools.trace.ThreadLocalStack;

import java.util.Deque;

// FIXME: should serialize before invoking as state can change later?
public class ThreadLocalHelper {

    static final ThreadLocal<ThreadLocalStack> threadLocal = new ThreadLocal<>();

    // FIXME: synchronize
    public static void setMethodStack(Deque<Method> methodStack) {
        ThreadLocalStack threadLocalStack = getThreadLocalStack();
        threadLocalStack.methodStack = methodStack;
    }

    public static void setMainMethod(Method beginMethod) {
        ThreadLocalStack threadLocalStack = getThreadLocalStack();
        threadLocalStack.beginMethod = beginMethod;
    }

    public static Method getBeginMethod() {
        ThreadLocalStack threadLocalStack = getThreadLocalStack();
        /*ThreadLocalStack threadLocalStack = threadLocal.get();
        if (threadLocalStack == null) {
            return null;
        }*/
        return threadLocalStack.beginMethod;
    }

    public static Deque<Method> getMethodStack() {
        ThreadLocalStack threadLocalStack = getThreadLocalStack();
        /*ThreadLocalStack threadLocalStack = threadLocal.get();
        if (threadLocalStack == null) {
            return null;
        }*/
        return threadLocalStack.methodStack;
    }

    private static ThreadLocalStack getThreadLocalStack() {
        ThreadLocalStack threadLocalStack = threadLocal.get();
        if (null == threadLocalStack) {
            threadLocalStack = new ThreadLocalStack();
            threadLocal.set(threadLocalStack);
        }
        return threadLocalStack;
    }

}
