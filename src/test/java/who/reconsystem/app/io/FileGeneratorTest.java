package who.reconsystem.app.io;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.io.type.TXTFile;
import who.reconsystem.app.root.config.Functions;

import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileGeneratorTest {

    private final File file;

    private final String CONTENT = "append content\nin the new line";


    public FileGeneratorTest() {
        this.file = TXTFile.getInstance("test.txt", Functions.getLocalePath(""));
    }

    @Test
    public void testCreate() {
        File fi = file.create();
        assertInstanceOf(File.class, fi);
    }

    @Test
    public void testAddContent() {
        file.addContent(CONTENT, StandardOpenOption.APPEND);
        assertFalse(file.getContent().read().isEmpty());
    }

    @Test
    public void getContentTest() {
        FileReader fr = file.getContent();
        List<String[]> contents = fr.read();
        assertEquals(2, contents.size());
        assertFalse(contents.isEmpty());
        assertArrayEquals(new String[]{CONTENT.split("\\n")[0]}, contents.get(0));
    }

}
