package who.reconsystem.app.io.type;

import lombok.Data;
import lombok.NoArgsConstructor;
import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

public class TXTFile extends FileGenerator implements File {
    private static TXTFile instance;

    private TXTFile(String fileName, String path) {
        super(fileName, path);
    }

    public static synchronized TXTFile getInstance(String fileName, String path) {
        if (instance != null) {
            instance = new TXTFile(fileName, path);
        }
        return instance;
    }

    @Override
    public File create() throws FileGeneratorException {
        try {
            FileAttribute<?>[] attrs = someAttributes(filePath);
            Files.createFile(filePath, attrs);
            isSuccess = true;
            //TODO adding log for the success
        }catch (IOException io) {
            //TODO Adding log file and Dialog
            io.printStackTrace();
        }
        return this;
    }
}
