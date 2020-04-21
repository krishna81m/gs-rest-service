package com.paycycle.util.devtools.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paycycle.util.devtools.MethodExecEvent;
import com.paycycle.util.devtools.MethodExecEventData;
import com.paycycle.util.devtools.MethodParam;
import com.paycycle.util.devtools.helper.RequestTracer;
import com.paycycle.util.devtools.helper.ThreadLocalHelper;
import com.paycycle.util.devtools.trace.Method;
import com.paycycle.util.devtools.trace.MethodArg;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FIXME: read classes, packages from runtime?
 *
 * Auto add/remove method inputs and outputs before entering and after exiting
 *      do not include objects/methods that might:
 *          do lazy initialization on access or serialization ;)
 *          have business logic in POJOs or objects passed to methods :(
 *          are expensive to serialize :<
 *
 * Adds method events as they begin and end including
 *      start timestamp and end timestamp
 *
 * Goal is for clients to see methods appear like this
 *      as the methods exit, we will pop method instances to track timestamps and method execution times
 *
 * Every time there is a BEFORE event, we add space, every time there is an AFTER event, we reduce space
 *
 * main_method << only one per thread id
 *    child_method1
 *       sub_child_method1
 *          db_method1
 *          db_method2
 *       sub_child_method2
 *          db_method3
 *    child_method2
 *       sub_child_method2
 *     ...
 */
@Aspect
@Service
@Configuration
public class MethodTraceAspect {

    // FIXME: exclude lazy/indirect list from being serialized
    ObjectMapper mapper = new ObjectMapper();

    // when stack becomes empty on last popup, add entire method stack event
    Boolean generateStackTraceOnCompletion = true;
    Boolean publishEvent = true;

    // cannot add all packages!!
    @Pointcut(value = "execution(* com.example.restservice.resource.RestResource.*(..)) " +
            " || execution(* com.example.restservice.service.*.*(..))  " +
            " || execution(* com.example.restservice.repository.*.*(..))")
    private void executionInService() {
        // pointcut def
    }

    @Before(value = "executionInService()")
    public void pushStackInBean(JoinPoint joinPoint) {
        pushStack(joinPoint);
    }

    @AfterReturning(value = "executionInService()", returning = "returnValue")
    public void popStackInBean(Object returnValue) {
        popStack(returnValue);

        if(generateStackTraceOnCompletion){
            if (ThreadLocalHelper.getMethodStack().isEmpty()) {
                printTrace();
            }
        }
    }

    private void pushStack(JoinPoint joinPoint) {
        Method m = new Method();
        m.methodName = StringUtils.replace(joinPoint.getSignature().toString(), "com.example.demo.service.", "");
        List<String> inputs = getInputParametersString(joinPoint.getArgs());
        inputs.stream().forEach(input -> {
            m.inputArgs.add(MethodArg.of(null, input));
        });
        m.setTimeInMs(Long.valueOf(System.currentTimeMillis()));
        ThreadLocalHelper.getMethodStack().push(m);

        // add method before Event
        MethodExecEvent execEvent = prepareMethodBeforeEvent(m);
        RequestTracer.addEvent(execEvent);
    }

    private MethodExecEvent prepareMethodBeforeEvent(Method m) {
        MethodExecEvent execEvent = new MethodExecEvent();
        execEvent.setEventType(MethodExecEvent.METHOD_EVENT_TYPES.METHOD_BEFORE.name());

        MethodExecEventData data = new MethodExecEventData();
        data.setSignature(m.methodName);    // FIXME: consolidate naming convention

        List<MethodParam> methodParams = new ArrayList<>();
        m.inputArgs.stream().forEach(input -> {
            MethodParam methodParam = new MethodParam();
//            methodParam.setName(input.argAsStr);
            methodParam.setValue(input.argAsStr);
            methodParams.add(methodParam);
        });
        data.setInputs(methodParams);

        execEvent.setData(data);
        return execEvent;
    }

    private List<String> getInputParametersString(Object[] joinPointArgs) {
        List<String> inputs = new ArrayList<>();
        for(Object arg : joinPointArgs) {
            try {
                inputs.add(mapper
                    .writerWithDefaultPrettyPrinter()   // Formatted JSON string
                    .writeValueAsString(joinPointArgs));
            } catch (Exception e) {
                inputs.add("Unable to create input parameters string. Error:" + e.getMessage());
            }
        }
        return inputs;
    }

    private void popStack(Object output) {
        Method childMethod = ThreadLocalHelper.getMethodStack().pop();
        try {
            childMethod.outputArg = MethodArg.of(output, output == null ? "" :
                    mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(output));
        } catch (JsonProcessingException e) {
            childMethod.outputArg = MethodArg.of(output, e.getMessage());
        }

        childMethod.setTimeInMs(Long.valueOf(System.currentTimeMillis() - childMethod.getTimeInMs().longValue()));
        if (ThreadLocalHelper.getMethodStack().isEmpty()) {
            // add main method at the last method
            ThreadLocalHelper.setMainMethod(childMethod);
        } else {
            Method parentMethod = ThreadLocalHelper.getMethodStack().peek();
            addChildMethod(childMethod, parentMethod);
        }

        // add method before Event
        MethodExecEvent execEvent = prepareMethodAfterEvent(childMethod);
        RequestTracer.addEvent(execEvent);
    }

    // FIXME: consolidate to original parent MethodExecEvent by tracking a reference
    private MethodExecEvent prepareMethodAfterEvent(Method m) {
        MethodExecEvent execEvent = new MethodExecEvent();
        execEvent.setEndTime(new Date());
        execEvent.setEventType(MethodExecEvent.METHOD_EVENT_TYPES.METHOD_AFTER.name());

        MethodExecEventData data = new MethodExecEventData();
        data.setSignature(m.methodName);    // FIXME: consolidate naming convention

        // there can only be one output
        MethodParam methodParam = new MethodParam();
        if(m.outputArg != null) {
            methodParam.setValue(m.outputArg.argAsStr);
        }
        data.setOutput(methodParam);

        execEvent.setData(data);
        return execEvent;
    }

    private void addChildMethod(Method childMethod, Method parentMethod) {
        if (parentMethod != null) {
            if (parentMethod.childMethods == null) {
                parentMethod.setChildMethods(new ArrayList<>());
            }
            parentMethod.childMethods.add(childMethod);
        }
    }

    public void printTrace() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n<TRACE>\n").append(mapper
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(
                    ThreadLocalHelper.getBeginMethod()));
            sb.append("\n</TRACE>");
            System.out.println(sb.toString());
        } catch (JsonProcessingException e) {
            StringUtils.abbreviate(ExceptionUtils.getStackTrace(e), 2000);
        }
    }

}
