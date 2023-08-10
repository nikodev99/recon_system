package who.reconsystem.app.io.type;

import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileReader;

public class XLSXFile implements File {
    @Override
    public File create() throws FileGeneratorException {
        return null;
    }

    @Override
    public long addContent(String content) {
        return 0;
    }

    @Override
    public FileReader getContent() {
        return null;
    }

    @Override
    public void remove() {

    }
}
