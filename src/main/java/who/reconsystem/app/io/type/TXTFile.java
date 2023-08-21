package who.reconsystem.app.io.type;

import who.reconsystem.app.io.FileGenerator;

import java.nio.file.Path;

public class TXTFile extends FileGenerator {
    public TXTFile(String fileName, String path) {
        super(fileName, path, "txt");
    }

    public TXTFile(Path path) {
        super(path);
    }
}
