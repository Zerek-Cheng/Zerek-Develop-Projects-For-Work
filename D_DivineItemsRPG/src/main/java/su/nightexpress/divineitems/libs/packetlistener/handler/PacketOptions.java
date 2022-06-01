/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.packetlistener.handler;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface PacketOptions {
    public boolean forcePlayer() default false;

    public boolean forceServer() default false;
}

