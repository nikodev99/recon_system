package who.reconsystem.app.io;

import lombok.Data;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.log.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FileGenerator implements File {

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

    public FileGenerator(String fileName, String path, String ext) {
        this.fileName = fileName.contains(".") ? fileName : fileName + "." + ext;
        this.path = path;
        this.ext = ext;
        this.filePath = Paths.get(path, fileName);
        this.folderPath = Paths.get(path);
        this.fileUtils = getFileUtilsInstance();
        exists = Files.exists(filePath);
        fileContent = getFileContentInstance();
    }

    public FileGenerator(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.filePath = Paths.get(path, fileName);
        this.folderPath = Paths.get(path);
        this.fileUtils = getFileUtilsInstance();
        exists = Files.exists(filePath);
        fileContent = getFileContentInstance();
    }

    public FileGenerator(Path filePath) {
        this.filePath = filePath;
        this.fileName = filePath.getFileName().toString();
        this.path = filePath.toAbsolutePath().toString();
        this.ext = fileName.contains(".") ? fileName.split("\\.")[1]: "";
        this.folderPath = filePath.getParent();
        this.fileUtils = getFileUtilsInstance();
        exists = Files.exists(filePath);
        fileContent = getFileContentInstance();
    }

    public File create() {
        if (fileDontExists(filePath)) {
            try {
                Log.set(FileGenerator.class).debug("File to create: " + filePath);
                fileCreation();
                Log.set(FileGenerator.class).info("File created successfully");
            } catch (IOException io) {
                Log.set(FileGenerator.class).trace(io);
                DialogMessage.exceptionDialog(io);
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
                Log.set(FileGenerator.class).critical(io.getMessage());
                DialogMessage.exceptionDialog(io);
            }
        }
    }

    @Override
    public void addContent(String content, StandardOpenOption... standardOpenOptions) {
        if (exists) {
            try {
                fileContent.setStandardOpenOption(standardOpenOptions);
                fileContent.write(content);
            }catch (IOException io) {
                Log.set(FileGenerator.class).fatal(io.getMessage());
                DialogMessage.exceptionDialog(io);
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
                Log.set(FileGenerator.class).fatal(io.getMessage());
                DialogMessage.exceptionDialog(io);
            }
            return new FileReader(lines);
        }
        return null;
    }

    @Override
    public void remove() {
        try {
            isSuccess = Files.deleteIfExists(filePath);
            if (isSuccess) Log.set(FileGenerator.class).info("Fichier supprimer avec success");
        }catch (IOException io) {
            Log.set(FileGenerator.class).fatal(io.getMessage());
            DialogMessage.exceptionDialog(io);
        }
    }

    public FileDetails getFiledetails() {
        return fileUtils.getFile();
    }

    protected static boolean fileDontExists(Path filePath) {
        return Files.notExists(filePath);
    }

    protected void fileCreation() throws IOException {
        Files.createFile(filePath);
        BasicFileAttributeView attributesView = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
        BasicFileAttributes attributes = attributesView.readAttributes();
        FileTime newCreationTime = FileTime.fromMillis(System.currentTimeMillis());
        attributesView.setTimes(attributes.lastModifiedTime(), attributes.lastAccessTime(), newCreationTime);
        isSuccess = true;
    }

    private FileUtils getFileUtilsInstance(){
        return new FileUtils(filePath);
    }

    private FileContent getFileContentInstance(){
        return new FileContent(filePath);
    }

}
