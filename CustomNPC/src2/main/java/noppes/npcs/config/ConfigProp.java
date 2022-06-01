package noppes.npcs.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ConfigProp {
    String name() default "";
    
    String info() default "";
}
