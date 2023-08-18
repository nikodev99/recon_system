package who.reconsystem.app.io;

import lombok.Data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FileGenerator implements File {
    private static FileGenerator instance;

    protected final String fileName;

    protected final String path;

    protected final String ext;

    protected Path filePath;

    protected Path folderPath;

    protected FileContent fileContent;

    protected FileDetails fileDetails;

    protected FileUtils fileUtils;

    protected boolean isSuccess = false;

    protected boolean exists;

    protected FileGenerator(String fileName, String path, String ext) {
        this.fileName = fileName.contains(".") ? fileName : fileName + "." + ext;
        this.path = path;
        this.ext = ext;
        this.filePath = Paths.get(path, fileName);
        this.folderPath = Paths.get(path);
        exists = Files.exists(filePath);
        fileContent = FileContent.getInstance(filePath);
        fileUtils = FileUtils.getInstance(filePath);
    }

    protected FileGenerator(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.filePath = Paths.get(path, fileName);
        this.folderPath = Paths.get(path);
        exists = Files.exists(filePath);
        fileContent = FileContent.getInstance(filePath);
        fileUtils = FileUtils.getInstance(filePath);
    }

    protected FileGenerator(Path filePath) {
        this.filePath = filePath;
        this.fileName = filePath.getFileName().toString();
        this.path = filePath.toAbsolutePath().toString();
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.folderPath = filePath.getParent();
        exists = Files.exists(filePath);
        fileContent = FileContent.getInstance(filePath);
        fileUtils = FileUtils.getInstance(filePath);
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
            instance = new FileGenerator(fileName, path);
        }
        return instance;
    }

    public static synchronized FileGenerator getInstance(Path filePath) {
        System.out.println("instance: " + instance);
        if (instance == null) {
            instance = new FileGenerator(filePath);
        }
        return instance;
    }

    public File create() {
        if (fileDontExists(filePath)) {
            try {
                System.out.println("File to create: " + filePath);
                Files.createFile(filePath);
                BasicFileAttributeView attributesView = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
                BasicFileAttributes attributes = attributesView.readAttributes();
                FileTime newCreationTime = FileTime.fromMillis(System.currentTimeMillis());
                attributesView.setTimes(attributes.lastModifiedTime(), attributes.lastAccessTime(), newCreationTime);
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

    public void addContent(String content) {
        if (exists) {
            try {
                fileContent.write(content);
            }catch (IOException io) {
                //TODO adding log and dialog
                io.printStackTrace();
            }
        }
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

    public static boolean fileDontExists(Path filePath) {
        return Files.notExists(filePath);
    }

    public FileDetails getFiledetails() {
        return fileUtils.getFile();
    }

}
