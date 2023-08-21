package who.reconsystem.app.io.type;

import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileReader;

import java.nio.file.StandardOpenOption;

public class XLSXFile implements File {
    @Override
    public File create() {
        return null;
    }

    @Override
    public void addContent(String content) {
    }

    @Override
    public void addContent(String content, StandardOpenOption... standardOpenOptions) {

    }

    @Override
    public FileReader getContent() {
        return null;
    }

    @Override
    public void remove() {

    }
}
