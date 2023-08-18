package who.reconsystem.app.log;

import who.reconsystem.app.io.FileDetails;
import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.io.FileUtils;
import who.reconsystem.app.root.config.Functions;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Log {

    private static final String LOGFILE = "reconSystem.log";

    private static FileGenerator fileGenerator;

    public static Logger set(Object aClass) {
        PropertyConfigurator.configure(fileGenerator);
        return Logger.getLogger(aClass);
    }

    public static synchronized void createLogFile() {
        Path logFilePath = getLogFile(false);
        fileGenerator = FileGenerator.getInstance(logFilePath);
        if (fileGenerator.isExists()) {
            boolean isMoved = checkOldFile();
            if (isMoved) {
                fileGenerator.create();
            }
        }else {
            fileGenerator.create();
        }
    }

    private static boolean checkOldFile() {
        FileDetails details = fileGenerator.getFiledetails();;
        FileUtils fileUtils = fileGenerator.getFileUtils();;
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
