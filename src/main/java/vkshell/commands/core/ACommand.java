package vkshell.commands.core;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ACommand {
    String[] names();

    String desc();
}
