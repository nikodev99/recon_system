package who.reconsystem.app.io.type;

import who.reconsystem.app.io.FileGenerator;

import java.nio.file.Path;

public class LOGFile extends FileGenerator {
    private static LOGFile instance;

    protected LOGFile(String fileName, String path) {
        super(fileName, path, "log");
    }

    protected LOGFile(Path path) {
        super(path);
    }

}
