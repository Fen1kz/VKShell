package vkshell.commands.core;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AOption {
    String[] names();

    String desc() default "";

    Flag[] flags() default {};

    public static enum Flag {
        NOHELP, REQUIRED
    }
}
