package com.mn.engcivil.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author Matheus
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
public @interface RC{}
