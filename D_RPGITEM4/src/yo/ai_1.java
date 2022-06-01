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
public @interface ai_1 {
    public a a() default a.BOTH;

    public static enum a {
        PLAYER(0),
        CONSOLE(1),
        BOTH(2);
        
        private final int slot;

        private a(int slot) {
            this.slot = slot;
        }

        public int getSlot() {
            return this.slot;
        }
    }

}

