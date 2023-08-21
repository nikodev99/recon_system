package who.reconsystem.app.io;

import lombok.Getter;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.root.config.Functions;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    private static FileUtils instance;

    private final Path path;

    @Getter
    private FileDetails fileDetails;

    public FileUtils(Path path) {
        this.path = path;
    }

    public FileDetails getFile() {
        if (Files.exists(path)) {
            try {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                Path parent = path.getParent();
                String parentFolder = parent != null ? parent.toString() : Functions.getLocalePath("");
                Log.set(FileUtils.class).debug("is same: " + Files.isSameFile(path, path));
                fileDetails = FileDetails.builder()
                        .isSameFile(Files.isSameFile(path, path))
                        .name(path.getFileName().toString())
                        .lastModifiedTime(attributes.lastModifiedTime().toInstant())
                        .creationDate(attributes.creationTime().toInstant())
                        .parent(parentFolder)
                        .size(attributes.size())
                        .build();
            }catch (IOException io) {
                Log.set(FileUtils.class).trace(io);
                DialogMessage.exceptionDialog(io);
            }
            return fileDetails;
        }
        return new FileDetails();
    }

    public boolean moveFile(Path pathTo) {
        return moveFile(path, pathTo);
    }

    public boolean moveFile(Path pathFrom, Path pathTo) {
        boolean moveSuccess = false;
        try {
            if (Files.notExists(pathTo)) {
                Files.createDirectories(pathTo);
            }
            Path fileMoved = Files.move(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
            moveSuccess = Files.exists(fileMoved);
        }catch (IOException e) {
            Log.set(FileUtils.class).trace(e);
            DialogMessage.exceptionDialog(e);
        }
        return moveSuccess;
    }

    public List<String> getAllFiles() {
        List<String> fileNames = new ArrayList<>();
        try {
            if (isFolderNotEmpty()) {
                Stream<Path> paths = Files.walk(path);
                fileNames = paths.filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
                Log.set(FileUtils.class).debug("File tree: " + fileNames);
            }
        }catch (IOException | SecurityException ex) {
            Log.set(FileUtils.class).critical(ex.getMessage());
            DialogMessage.exceptionDialog(ex);
        }
        return fileNames;
    }

    protected boolean isFolderNotEmpty() throws IOException {
        DirectoryStream<Path> pathStreams = Files.newDirectoryStream(path);
        return pathStreams.iterator().hasNext();
    }
}
