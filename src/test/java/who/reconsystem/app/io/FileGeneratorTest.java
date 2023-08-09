package who.reconsystem.app.io;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.root.config.Functions;

import static org.junit.jupiter.api.Assertions.*;

public class FileGeneratorTest {

    @Test
    public void testCreate() {
        File file = FileGenerator.getInstance("test.txt", Functions.getLocalePath(""), "txt");
        File fi = null;
        try {
            fi = file.create();
        }catch (FileGeneratorException e) {
            e.printStackTrace();
        }
        assertInstanceOf(File.class, fi);
    }

}
