package crach.stage.game.desktop;

import java.io.File;
import java.io.IOException;

public class CreteFile {
    public static void main(String[] args) {
        try {
            File file = File.createTempFile("test",".tmx",new File("C:\\Users\\jamal\\Desktop"));
            file.deleteOnExit();
            System.out.println("file name :"+file.getName()+"file path "+file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
