package who.reconsystem.app.io;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileContent {

    private static FileContent instance;

    private final String content;

    private Path path;

    @Getter
    @Setter
    private StandardOpenOption standardOpenOption;

    private FileContent(Path path, String content) {
        this.path = path;
        this.content = content;
    }

    private FileContent(String content) {
        this.content = content;
    }

    public static synchronized FileContent getInstance(Path path, String content) {
        if (instance == null) {
            instance = new FileContent(path, content);
        }
        return instance;
    }

    public static synchronized FileContent getInstance(String content) {
        if (instance == null) {
            instance = new FileContent(content);
        }
        return instance;
    }

    public Path write() throws IOException {
       return Files.write(path, content.getBytes(), standardOpenOption);
    }
}
