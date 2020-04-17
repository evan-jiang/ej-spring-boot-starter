package com.ej.oss.spring.boot.starter.annotation;

import com.ej.oss.spring.boot.starter.builder.EjChainHandlerBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EjChainHandlerBuilder.class})
public @interface EnableEjChain {

    @AliasFor("packages")
    String [] value() default {};

    @AliasFor("value")
    String [] packages() default {};
}
