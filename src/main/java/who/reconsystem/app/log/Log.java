package who.reconsystem.app.log;

import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileDetails;
import who.reconsystem.app.io.FileUtils;
import who.reconsystem.app.io.type.LOGFile;
import who.reconsystem.app.root.config.Functions;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Log {

    private static final String LOGFILE = "reconSystem.log";

    private static File file;

    public static Logger set(Object aClass) {
        PropertyConfigurator.configure(file);
        return Logger.getLogger(aClass);
    }

    public static synchronized void createLogFile() {
        Path logFilePath = getLogFile(false);
        file = LOGFile.getInstance(logFilePath);
        if (file.instance().isExists()) {
            boolean isMoved = checkOldFile();
            if (isMoved) {
                file.create();
            }
        }else {
            file.create();
        }
    }

    private static boolean checkOldFile() {
        FileDetails details = file.instance().getFiledetails();
        FileUtils fileUtils = file.instance().getFileUtils();;
        boolean renamed = false;

        boolean dateChecks = Functions.daysComparison(details.getCreationDate(), Functions.now());
        if (!dateChecks) {
            Path filePath = getLogFile(true);
            renamed = fileUtils.moveFile(filePath);
        }
        return renamed;
    }

    private static Path getLogFile(boolean old) {
        Path logFile = Paths.get(Functions.getLocalePath(LOGFILE));
        if(old) {
            String[] fileParts = LOGFILE.split("\\.");
            String ext = fileParts[1];
            logFile = Paths.get(Functions.getLocalePath("old/" + fileParts[0] + "-" + "old" + "-" +  Functions.yesterday() + "." + ext));
        }
        return logFile;
    }

}
