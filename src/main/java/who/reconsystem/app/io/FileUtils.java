package who.reconsystem.app.io;

import lombok.Getter;

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

    private FileUtils(Path path) {
        this.path = path;
    }

    public static synchronized FileUtils getInstance(Path path) {
        if (instance == null) {
            instance = new FileUtils(path);
        }
        return instance;
    }

    public FileDetails getFile() {
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            fileDetails = FileDetails.builder()
                    .isSameFile(Files.isSameFile(path, path))
                    .name(path.getFileName().toString())
                    .lastModifiedTime(attributes.lastModifiedTime().toInstant())
                    .creationDate(attributes.creationTime().toInstant())
                    .parent(path.getParent().toString())
                    .size(attributes.size())
                    .build();
        }catch (IOException io) {
            //TODO add log
            io.printStackTrace();
        }
        return fileDetails;
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
            //TODO log
            e.printStackTrace();
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
                //TODO add a log here.
            }
        }catch (IOException | SecurityException ex) {
            //TODO adding the log and dialog
            ex.printStackTrace();
        }
        return fileNames;
    }

    protected boolean isFolderNotEmpty() throws IOException {
        DirectoryStream<Path> pathStreams = Files.newDirectoryStream(path);
        return pathStreams.iterator().hasNext();
    }
}
