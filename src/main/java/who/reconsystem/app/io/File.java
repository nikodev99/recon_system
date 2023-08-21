package who.reconsystem.app.io;

import java.nio.file.StandardOpenOption;

public interface File {
    File create();

    void addContent(String content);

    void addContent(String content, StandardOpenOption ...standardOpenOptions);

    FileReader getContent();

    void remove();
}
