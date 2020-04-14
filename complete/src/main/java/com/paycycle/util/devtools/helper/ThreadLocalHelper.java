package com.paycycle.util.devtools.helper;

import com.paycycle.util.devtools.trace.Method;
import com.paycycle.util.devtools.trace.ThreadLocalStack;

import java.util.Deque;

// FIXME: should serialize before invoking as state can change later?
public class ThreadLocalHelper {

    static final ThreadLocal<ThreadLocalStack> threadLocal = new ThreadLocal<>();

    // FIXME: synchronize
    public static void setMethodStack(Deque<Method> methodStack) {
        ThreadLocalStack threadLocalStack = threadLocal.get();
        if (null == threadLocalStack) {
            threadLocalStack = new ThreadLocalStack();
        }
        threadLocalStack.methodStack = methodStack;
        threadLocal.set(threadLocalStack);
    }

    public static void setMainMethod(Method beginMethod) {
        ThreadLocalStack threadLocalStack = threadLocal.get();
        if (null == threadLocalStack) {
            threadLocalStack = new ThreadLocalStack();
        }
        threadLocalStack.beginMethod = beginMethod;
        threadLocal.set(threadLocalStack);
    }

    public static Method getBeginMethod() {
        ThreadLocalStack threadLocalStack = threadLocal.get();
        if (threadLocalStack == null) {
            return null;
        }
        return threadLocalStack.beginMethod;
    }

    public static Deque<Method> getMethodStack() {
        ThreadLocalStack threadLocalStack = threadLocal.get();
        if (threadLocalStack == null) {
            return null;
        }
        return threadLocalStack.methodStack;
    }

}
