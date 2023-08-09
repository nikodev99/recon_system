package who.reconsystem.app.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileContent {

    private String content;

    public FileContent(String content) {
        this.content = content;
    }

    public long fileContent(Path path) throws IOException {
        Path filePath = Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
        return Files.size(filePath);
    }
}
