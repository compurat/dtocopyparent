package com.purat.dto.copy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface CopyDto {
    String sourceFolder() default "";
    String copyFromPackage();
    String copyToPackage();
}
