package who.reconsystem.app.io;

import who.reconsystem.app.exception.FileGeneratorException;

import java.util.List;

public interface File {
    File create() throws FileGeneratorException;

    long addContent(String content);

    FileReader getContent();

    void remove();
}
