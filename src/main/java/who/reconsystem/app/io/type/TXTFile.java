package who.reconsystem.app.io.type;

import who.reconsystem.app.io.FileGenerator;

import java.nio.file.Path;

public class TXTFile extends FileGenerator {
    protected TXTFile(String fileName, String path) {
        super(fileName, path, "txt");
    }

    protected TXTFile(Path path) {
        super(path);
    }
}
