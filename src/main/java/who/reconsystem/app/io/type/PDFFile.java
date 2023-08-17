package who.reconsystem.app.io.type;

import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileReader;

public class PDFFile implements File {
    @Override
    public File create() throws FileGeneratorException {
        return null;
    }

    @Override
    public void addContent(String content) {
    }

    @Override
    public FileReader getContent() {
        return null;
    }

    @Override
    public void remove() {

    }
}
