package com.paycycle.util.devtools.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * does not work unless Spring Boot scans the classes inside the jar
 *
 */
@Aspect
@Service
@Configuration
public class JDBCLogAspect {
    public static final String JDBC_DRIVER_STATEMENT_FIELD_NAME = "v";

    @Pointcut(value = "execution(* org.h2.command.Command.executeQuery(..))")
    private void executionInService() {
        //do nothing, just for pointcut def
    }

    @Before(value = "executionInService()")
    public void pushStackInBean(JoinPoint joinPoint) {
        System.out.println();
    }

    @AfterReturning(value = "executionInService()", returning = "returnValue")
    public void popStackInBean(Object returnValue) {
        System.out.println();
    }

    @Around("execution(* org.h2.command.Command.executeQuery(..))")
    public Object aroundJdbcStatementExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        // Make the execute call and time it.
        Long before = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        Long callTime = System.currentTimeMillis() - before;

        try {
            // The SQL is in the target. In our TDS driver, it is obfuscated as the field named "v".
            Object target = joinPoint.getTarget();
            Field field = target.getClass().getDeclaredField(JDBC_DRIVER_STATEMENT_FIELD_NAME);
            if (field != null) {
                field.setAccessible(true);
                String sql = (String) field.get(target);
                // Save the SQL call information for logging later.
                if (sql != null) {
                    // saveJdbcStatementExecuteInfo(SQL_USAGE, sql, callTime);
                }
            } else {
                field = target.getClass().getDeclaredField("sql");
                if (field != null) {
                    field.setAccessible(true);
                    String sql = (String) field.get(target);
                    // call information for logging later.
                    if (sql != null) {
                        // saveJdbcStatementExecuteInfo(SQL_USAGE, sql, callTime);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }
}