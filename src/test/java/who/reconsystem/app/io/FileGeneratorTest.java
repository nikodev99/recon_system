package who.reconsystem.app.io;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.root.config.Functions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileGeneratorTest {

    private final File file;

    private final String CONTENT = "append content\n\rin the new line";


    public FileGeneratorTest() {
        this.file = FileGenerator.getInstance("test.txt", Functions.getLocalePath(""));
    }

    @Test
    public void testCreate() {
        File fi = null;
        try {
            fi = file.create();
        }catch (FileGeneratorException e) {
            e.printStackTrace();
        }
        assertInstanceOf(File.class, fi);
    }

    @Test
    public void testAddContent() {
        file.addContent(CONTENT);
        assertFalse(file.getContent().read().isEmpty());
    }

    @Test
    public void getContentTest() {
        FileReader fr = file.getContent();
        List<String[]> contents = fr.read();
        assertEquals(4, contents.size());
        assertFalse(contents.isEmpty());
        assertArrayEquals(new String[]{CONTENT.split("\\n\\r")[0]}, contents.get(0));
    }

}
