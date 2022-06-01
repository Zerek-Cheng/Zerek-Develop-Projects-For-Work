/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Class {
    public String[] value();

    public Minecraft.Version[] versions() default {};

    public boolean ignoreExceptions() default true;
}

