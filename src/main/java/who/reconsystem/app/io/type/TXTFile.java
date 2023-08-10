package who.reconsystem.app.io.type;

import who.reconsystem.app.io.FileGenerator;

public class TXTFile extends FileGenerator {
    protected TXTFile(String fileName, String path) {
        super(fileName, path, "txt");
    }
}
