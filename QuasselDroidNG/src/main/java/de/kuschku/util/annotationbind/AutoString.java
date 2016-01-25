package de.kuschku.util.annotationbind;

import android.support.annotation.AnyRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AutoString {
    @AnyRes
    int[] value() default {};
}
