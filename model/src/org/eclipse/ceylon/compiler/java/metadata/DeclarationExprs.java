package org.eclipse.ceylon.compiler.java.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Applied to an annotation constructor, encodes the "literal" 
 * declaration (metamodel) expressions in the invocation 
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DeclarationExprs {
    DeclarationValue[] value();
}
