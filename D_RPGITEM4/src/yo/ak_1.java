/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface ak_1 {
    public String a();

    public boolean b() default false;

    public String c() default "rpgitem";
}

