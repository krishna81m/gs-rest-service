package com.tracer.util.devtools.aspect;

import com.tracer.util.devtools.DBExecEvent;
import com.tracer.util.devtools.DBExecEventData;
import com.tracer.util.devtools.DBParam;
import com.tracer.util.devtools.helper.RequestTracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.h2.command.Command;
import org.h2.command.CommandContainer;
import org.h2.expression.Parameter;
import org.h2.expression.ParameterInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
                    List<DBParam> dbParams = new ArrayList<>();
                    if(target instanceof CommandContainer){
                        ArrayList<? extends ParameterInterface> parameters = ((CommandContainer) target).getParameters();
                        parameters.stream().forEach(p -> {
                            DBParam dbParam = new DBParam();
                            if(p instanceof Parameter) {
                                dbParam.setName(((Parameter)p).getColumnName());
                            }
                            dbParam.setValue(p.getParamValue().getCurrentValue().toString());
                            dbParams.add(dbParam);
                        });
                    }
                    DBExecEvent execEvent = prepareDBExecEvent(sql, dbParams);
                    RequestTracer.addEvent(execEvent);
                    // saveJdbcStatementExecuteInfo(SQL_USAGE, sql, callTime);
                }
            }


            /*Field parametersField = Command.class.getDeclaredField("parameters");
            if (parametersField != null) {
                parametersField.setAccessible(true);
                Object obj = parametersField.get(target);

                if(obj != null){

                }
            }*/
        } catch (Exception ex) {
            // throw new RuntimeException(ex);
        }

        return result;
    }

    private DBExecEvent prepareDBExecEvent(String sql, List<DBParam> dbParams) {
        DBExecEvent dbExecEvent = new DBExecEvent();
        DBExecEventData data = new DBExecEventData();
        data.setSql(sql);
        data.setParams(dbParams);
        dbExecEvent.setData(data);
        return dbExecEvent;
    }
}
