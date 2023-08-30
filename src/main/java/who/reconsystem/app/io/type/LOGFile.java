package who.reconsystem.app.io.type;

import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class LOGFile extends FileGenerator {

    public LOGFile(String fileName, String path) {
        super(fileName, path, "log");
    }

    public LOGFile(Path path) {
        super(path);
    }

    @Override
    public File create() {
        try {
            System.out.println("Pour les logs je suis ici");
            fileCreation();
        }catch (IOException io) {
            DialogMessage.exceptionDialog(io);
        }
        return this;
    }
}
