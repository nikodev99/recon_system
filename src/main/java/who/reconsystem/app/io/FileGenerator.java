package who.reconsystem.app.io;

import lombok.Data;
import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.root.config.Functions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;

@Data
public class FileGenerator {
    private static FileGenerator instance;

    private final String fileName;

    private final String path;

    private final String ext;

    private Path filePath;

    private boolean isSuccess = false;

    private FileGenerator(String fileName, String path, String ext) {
        this.fileName = fileName.contains(".") ? fileName : fileName + "." + ext;
        this.path = path;
        this.ext = ext;
        this.filePath = Paths.get(path, fileName);
    }

    private FileGenerator(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.filePath = Paths.get(path, fileName);
    }

    public static synchronized FileGenerator getInstance(String fileName, String path, String ext) {
        if (instance != null) {
            instance = new FileGenerator(fileName, path, ext);
        }
        return instance;
    }

    public static synchronized FileGenerator getInstance(String fileName, String path) {
        if (instance != null) {
            instance = new FileGenerator(fileName, path);
        }
        return instance;
    }

    public FileGenerator create() throws FileGeneratorException {
        if (!ext.isEmpty() && ext.contains(".")) {
            if (ext.contains(".")) throw new FileGeneratorException("Dot duplication error in the extension");
        }
        switch (ext) {
            case "":
            case "txt":
                return createTXTFile();
            case "pdf":
                return createPDFFile();
            case "xsl":
                return createEXCELFile();
        }
        return this;
    }

    public long content(String content) {
        long fileSize = 0;
        if (isSuccess) {
            FileContent fileContent = new FileContent(content);
            try {
                fileSize = fileContent.fileContent(filePath);
            }catch (IOException io) {
                //TODO adding log and dialog
                io.printStackTrace();
            }
        }
        return fileSize;
    }

    private FileGenerator createTXTFile() {
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

    private FileGenerator createPDFFile() {
        return this;
    }

    private FileGenerator createEXCELFile() {
        return this;
    }

    private FileAttribute<?>[] someAttributes(Path pathOfTheFile) throws IOException {
        FileTime creationTime = FileTime.from(Functions.now());
        FileTime lastModifiedTime = FileTime.from(Functions.now());
        Files.setAttribute(pathOfTheFile, "creationTime", creationTime);
        Files.setLastModifiedTime(pathOfTheFile, lastModifiedTime);

        return new FileAttribute<?>[]{
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-rw-rw-")),
        };
    }

}
