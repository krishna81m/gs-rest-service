package com.paycycle.util.devtools.trace;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadLocalStack {

    // method call stack for the thread's life, push/pop methods when submethod is called/returned from top
    public Deque<Method> methodStack = new LinkedList<>();
    public Method beginMethod;


}
