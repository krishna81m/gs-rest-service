package com.paycycle.util.devtools.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.h2.command.Command;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * does not work unless Spring Boot scans the classes inside the jar
 *
 */
@Aspect
// @Service    // cannot debug with Spring @Service
public class JDBCLogAspect {
    public static final String JDBC_DRIVER_STATEMENT_FIELD_NAME = "v";

    /*
     * Caused by: org.springframework.aop.framework.AopConfigException:
     * Advice must be declared inside an aspect type: Offending method
     * 'public java.lang.Object
     *  com.paycycle.util.devtools.aspect.JDBCLogAspect.aroundJdbcStatementExecute(org.aspectj.lang.ProceedingJoinPoint)
     * throws java.lang.Throwable' in class [com.paycycle.util.devtools.aspect.JDBCLogAspect]
     */
    @Around("execution(* org.h2.command..*+.execute*(..))")
    public Object aroundJdbcStatementExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        // Make the execute call and time it.
        Long before = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        Long callTime = System.currentTimeMillis() - before;

        try {
            // The SQL is in the target. In our TDS driver, it is obfuscated as the field named "v".
            Object target = joinPoint.getTarget();
            // reflect private "final field" from Parent Abstract class not target class
            Field field = Command.class.getDeclaredField("sql");
            if (field != null) {
                field.setAccessible(true);
                String sql = (String) field.get(target);
                // call information for logging later.
                if (sql != null) {
                    // saveJdbcStatementExecuteInfo(SQL_USAGE, sql, callTime);
                }
            }
        } catch (Exception ex) {
            // throw new RuntimeException(ex);
        }

        return result;
    }
}