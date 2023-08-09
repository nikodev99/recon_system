package who.reconsystem.app.io;

import lombok.Data;
import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.io.type.TXTFile;
import who.reconsystem.app.root.config.Functions;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class FileGenerator implements File {
    private static FileGenerator instance;

    protected final String fileName;

    protected final String path;

    protected final String ext;

    protected Path filePath;

    protected Path folderPath;

    protected boolean isSuccess = false;

    protected FileGenerator(String fileName, String path, String ext) {
        this.fileName = fileName.contains(".") ? fileName : fileName + "." + ext;
        this.path = path;
        this.ext = ext;
        this.filePath = Paths.get(path, fileName);
        folderPath = Paths.get(path);
    }

    protected FileGenerator(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.filePath = Paths.get(path, fileName);
        folderPath = Paths.get(path);
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

    public File create() throws FileGeneratorException {
        if (!ext.isEmpty() && ext.contains(".")) {
            if (ext.contains(".")) throw new FileGeneratorException("Dot duplication error in the extension");
        }
        switch (ext) {
            case "":
            case "txt":
                return TXTFile.getInstance(fileName, path).create();
            case "pdf":
                return createPDFFile();
            case "xsl":
                return createEXCELFile();
        }
        return this;
    }

    public long addContent(String content) {
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

    @Override
    public FileReader getContent() {
        if (isSuccess) {
            switch (ext) {
                case "":
                case "txt":
                    return TXTFile.getInstance(fileName, path).getContent();
                case "pdf":
                case "xsl":
            }
        }
        return new FileReader();
    }

    @Override
    public void remove() {
        try {

            isSuccess = Files.deleteIfExists(filePath);
        }catch (IOException io) {
            //TODO adding log and appropriate dialog
            io.printStackTrace();
        }
    }

    private FileGenerator createPDFFile() {
        return this;
    }

    private FileGenerator createEXCELFile() {
        return this;
    }

    protected FileAttribute<?>[] someAttributes(Path pathOfTheFile) throws IOException {
        FileTime creationTime = FileTime.from(Functions.now());
        FileTime lastModifiedTime = FileTime.from(Functions.now());
        Files.setAttribute(pathOfTheFile, "creationTime", creationTime);
        Files.setLastModifiedTime(pathOfTheFile, lastModifiedTime);

        return new FileAttribute<?>[]{
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-rw-rw-")),
        };
    }

    protected List<String> getAllFiles() {
        List<String> fileNames = new ArrayList<>();
        try {
            if (isFolderNotEmpty()) {
                Stream<Path> paths = Files.walk(folderPath);
                fileNames = paths.filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
                //TODO add a log here.
            }
        }catch (Exception ex) {
            //TODO adding the log and dialog
            ex.printStackTrace();
        }
        return fileNames;
    }

    protected boolean isFolderNotEmpty() throws IOException {
        DirectoryStream<Path> pathStreams = Files.newDirectoryStream(folderPath);
        return pathStreams.iterator().hasNext();
    }

}
