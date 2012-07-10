/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.qualifier;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 *
 * @author rmpestano
 */
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface PropertyKey {
    
   
    @Nonbinding
    String key() default "";
    
    @Nonbinding
    String file() default "";
    
}
