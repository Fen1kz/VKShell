package vkshell.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import vkshell.shell.cli.DefaultCLI;
import vkshell.shell.cli.ICLI;

@Configuration
@ImportResource("classpath:context.xml")
public class SpringConfig {
    @Bean
    public ICLI cli() {
//        return new JLine2CLI(System.in);
        return new DefaultCLI(System.in);
    }
}
