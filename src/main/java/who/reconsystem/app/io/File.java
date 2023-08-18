package who.reconsystem.app.io;

import who.reconsystem.app.exception.FileGeneratorException;

public interface File {
    File create();

    void addContent(String content);

    FileReader getContent();

    void remove();
}
