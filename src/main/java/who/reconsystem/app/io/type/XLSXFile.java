package who.reconsystem.app.io.type;

import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.io.FileReader;

import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class XLSXFile extends FileGenerator {

    public XLSXFile(String fileName, String path, String ext) {
        super(fileName, path, ext);
    }

    public XLSXFile(String fileName, String path) {
        super(fileName, path);
    }

    public XLSXFile(Path filePath) {
        super(filePath);
    }

    @Override
    public FileReader getContent() {
        return super.getContent();
    }
}
