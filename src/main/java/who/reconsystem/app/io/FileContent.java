package who.reconsystem.app.io;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileContent {

    private static FileContent instance;

    private String content;

    private Path path;

    @Getter
    @Setter
    private StandardOpenOption[] standardOpenOption;

    private FileContent(Path path) {
        this.path = path;
    }

    private FileContent(Path path, String content) {
        this.path = path;
        this.content = content;
    }

    public static synchronized FileContent getInstance(Path path, String content) {
        if (instance == null) {
            instance = new FileContent(path, content);
        }
        return instance;
    }

    public static synchronized FileContent getInstance(Path path) {
        if (instance == null) {
            instance = new FileContent(path);
        }
        return instance;
    }

    public void write() throws IOException {
        Files.write(path, content.getBytes(), standardOpenOption);
    }

    public void write(String content) throws IOException {
        Files.write(path, content.getBytes(), standardOpenOption);
    }
}
