package who.reconsystem.app.io;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileContent {

    private String content;

    private final Path path;

    @Getter
    @Setter
    private StandardOpenOption[] standardOpenOption;

    public FileContent(Path path) {
        this.path = path;
    }

    public FileContent(Path path, String content) {
        this.path = path;
        this.content = content;
    }

    public void write() throws IOException {
        Files.write(path, content.getBytes(), standardOpenOption);
    }

    public void write(String content) throws IOException {
        Files.write(path, content.getBytes(), standardOpenOption);
    }
}
