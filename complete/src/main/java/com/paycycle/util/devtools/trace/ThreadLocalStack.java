package com.paycycle.util.devtools.trace;

import java.util.Deque;

public class ThreadLocalStack {

    // method call stack for the thread's life, push/pop methods when submethod is called/returned from top
    public Deque<Method> methodStack;
    public Method beginMethod;


}
