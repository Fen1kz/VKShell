package misc;

import vkshell.app.App;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ConfigTest {
    @Test
    public void test() {
        try {
            System.out.println(App.get().config().createFileByPath("data" + File.separator + "trash" + File.separator + "xd"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(App.get().config().getFile(null));
        //System.out.println(App.get().config().getFile(Config.key.COOKIE_FILE));
    }
}
