package com.paycycle.util.devtools.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Aspect
@Service
@Configuration
// FIXME: read classes, packages from runtime?
public class MethodTraceAspect {

    // FIXME: exclude lazy/indirect list from being serialized
    ObjectMapper mapper = new ObjectMapper();

    @Pointcut(value = "execution(* com.example.restservice.GreetingController.*(..))")
    private void executionInService() {
        //do nothing, just for pointcut def
    }

    @Before(value = "executionInService()")
    public void pushStackInBean(JoinPoint joinPoint) {
        pushStack(joinPoint);
    }

    @AfterReturning(value = "executionInService()", returning = "returnValue")
    public void popStackInBean(Object returnValue) {
        popStack(returnValue);
    }

    private void pushStack(JoinPoint joinPoint) {
        Method m = new Method();
        m.methodName = StringUtils.replace(joinPoint.getSignature().toString(), "com.example.demo.service.", "");
        String input = getInputParametersString(joinPoint.getArgs());
        m.inputArgs.add(MethodArg.of(null, input));
        // m.setTimeInMs(Long.valueOf(System.currentTimeMillis()));
        ThreadLocalHelper.getMethodStack().push(m);
    }

    private String getInputParametersString(Object[] joinPointArgs) {
        String input;
        try {
            input = mapper.writeValueAsString(joinPointArgs);
        } catch (Exception e) {
            input = "Unable to create input parameters string. Error:" + e.getMessage();
        }
        return input;
    }

    private void popStack(Object output) {
        Method childMethod = ThreadLocalHelper.getMethodStack().pop();
        try {
            childMethod.outputArg = MethodArg.of(output, output == null ? "" : mapper.writeValueAsString(output));
        } catch (JsonProcessingException e) {
            childMethod.outputArg = MethodArg.of(output, e.getMessage());
        }

        // childMethod.setTimeInMs(Long.valueOf(System.currentTimeMillis() - childMethod.getTimeInMs().longValue()));
        if (ThreadLocalHelper.getMethodStack().isEmpty()) {
            ThreadLocalHelper.setMainMethod(childMethod);
        } else {
            Method parentMethod = ThreadLocalHelper.getMethodStack().peek();
            addChildMethod(childMethod, parentMethod);
        }
    }

    private void addChildMethod(Method childMethod, Method parentMethod) {
        if (parentMethod != null) {
            /*if (parentMethod.childMethods == null) {
                parentMethod.setMethodList(new ArrayList<>());
            }*/
            parentMethod.childMethods.add(childMethod);
        }
    }

    public void printTrace() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n<TRACE>\n").append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    ThreadLocalHelper.getBeginMethod()));
            sb.append("\n</TRACE>");
            System.out.println(sb.toString());
        } catch (JsonProcessingException e) {
            StringUtils.abbreviate(ExceptionUtils.getStackTrace(e), 2000);
        }
    }

}
