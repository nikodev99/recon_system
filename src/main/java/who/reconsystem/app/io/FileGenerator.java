package who.reconsystem.app.io;

import lombok.Data;
import lombok.Getter;
import who.reconsystem.app.exception.FileGeneratorException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    @Getter
    protected boolean isSuccess = false;

    @Getter
    protected boolean exists;

    protected FileGenerator(String fileName, String path, String ext) {
        this.fileName = fileName.contains(".") ? fileName : fileName + "." + ext;
        this.path = path;
        this.ext = ext;
        this.filePath = Paths.get(path, fileName);
        this.folderPath = Paths.get(path);
        exists = Files.exists(filePath);
    }

    protected FileGenerator(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.filePath = Paths.get(path, fileName);
        this.folderPath = Paths.get(path);
        exists = Files.exists(filePath);
    }

    public static synchronized FileGenerator getInstance(String fileName, String path, String ext) {
        System.out.println("instance: " + instance);
        if (instance == null) {
            instance = new FileGenerator(fileName, path, ext);
        }
        return instance;
    }

    public static synchronized FileGenerator getInstance(String fileName, String path) {
        System.out.println("instance: " + instance);
        if (instance == null) {
            try {
                instance = new FileGenerator(fileName, path);
            }catch (Exception e) {
                System.out.println("error message: " + e.getMessage());
            }
        }
        return instance;
    }

    public File create() throws FileGeneratorException {
        if (!exists) {
            try {
                Files.createFile(filePath);
                isSuccess = true;
                //TODO adding log for the success
            } catch (IOException io) {
                //TODO Adding log file and Dialog
                io.printStackTrace();
            }
            exists = true;
        }
        return this;
    }

    public long addContent(String content) {
        long fileSize = 0;
        if (exists) {
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
        List<String> lines = new ArrayList<>();
        if (exists) {
            try {
                lines = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
            } catch (IOException io) {
                //TODO adding log and dialog
                io.printStackTrace();
            }
            return FileReader.getInstance(lines);
        }
        return null;
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
