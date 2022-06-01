package foxz.commandhelper.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    public String name();
    
    String desc();
    
    String usage() default "";
    
    Class[] sub() default {};
}
