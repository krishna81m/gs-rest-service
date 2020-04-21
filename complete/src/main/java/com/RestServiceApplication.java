package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

/*- `@Configuration`: Tags the class as a source of bean definitions for the application
context.
- `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath
settings, other beans, and various property settings. For example, if `spring-webmvc` is
on the classpath, this annotation flags the application as a web application and activates
key behaviors, such as setting up a `DispatcherServlet`.
- `@ComponentScan`: Tells Spring to look for other components, configurations, and
services in the `com/example` package, letting it find the controllers.
*/

/**
 * Just enabling @EnableLoadTimeWeaving
 *      and -Daj.weaving.verbose=true -javaagent:/Users/vproddaturi/.m2/repository/org/aspectj/aspectjweaver/1.9.5/aspectjweaver-1.9.5.jar
 *      still does not work by enabling spring-aop
 *      and removing aop.xml
 *
 * java.lang.IllegalStateException: ClassLoader [sun.misc.Launcher$AppClassLoader]
 *      does NOT provide an 'addTransformer(ClassFileTransformer)' method. Specify a
 *      custom LoadTimeWeaver or start your Java virtual machine with Spring's agent:
 *      -javaagent:spring-instrument-{version}.jar
 *
 * Looks like the only way to enable
 *      is to Caused by:
 *
 *
 * Caused by: org.springframework.beans.BeanInstantiationException:
 * Failed to instantiate [org.springframework.instrument.classloading.LoadTimeWeaver]:
 * Factory method 'loadTimeWeaver' threw exception; nested exception is
 * java.lang.IllegalStateException: ClassLoader [sun.misc.Launcher$AppClassLoader]
 * does NOT provide an 'addTransformer(ClassFileTransformer)' method.
 *
 * Specify a custom LoadTimeWeaver or start your Java virtual machine with
 * Spring's agent:
 *
 */
// @ComponentScan({"com", "org.h2.command"})
@SpringBootApplication
// @EnableAspectJAutoProxy
// @EnableLoadTimeWeaving // LTW https://github.com/dsyer/spring-boot-aspectj
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
