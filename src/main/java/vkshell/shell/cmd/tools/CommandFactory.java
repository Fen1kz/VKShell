package vkshell.shell.cmd.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import vkshell.commands.core.ICommand;
import vkshell.shell.cmd.tools.interfaces.ICommandFactory;

import java.util.Map;

public class CommandFactory implements ICommandFactory {
    @Autowired
    private ApplicationContext context;

    @Override
    public <T extends ICommand> T getCommandInstance(Class<T> commandclass) {
        T cmd = null;
//        return context.getBean("command", commandclass);
        try {
            cmd = commandclass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        context.getAutowireCapableBeanFactory().autowireBean(cmd);
        return getBeanByType(commandclass);
    }

    public <T> T getBeanByType(final Class<T> claz) {
        Map beansOfType2 = context.getBeansOfType(ICommand.class);
        Map beansOfType = context.getBeansOfType(claz);
        final int size = beansOfType.size();
        String name = (String) beansOfType.keySet().iterator().next();
        return claz.cast(context.getBean(name, claz));
    }
}
