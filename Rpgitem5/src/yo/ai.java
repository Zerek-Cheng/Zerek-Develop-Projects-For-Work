// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface aI {
    a a() default a.BOTH;
    
    public enum a
    {
        PLAYER(0), 
        CONSOLE(1), 
        BOTH(2);
        
        private final int slot;
        
        private a(final int slot) {
            this.slot = slot;
        }
        
        public int getSlot() {
            return this.slot;
        }
    }
}
