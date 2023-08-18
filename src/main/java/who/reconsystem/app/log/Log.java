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

    private static FileUtils fileUtils;

    private static FileDetails details;

    public static Logger set(Object aClass) {
        createLogFile();
        PropertyConfigurator.configure(fileGenerator);
        return Logger.getLogger(aClass);
    }

    public static synchronized void createLogFile() {
        Path logFilePath = getLogFile(false);
        fileGenerator = FileGenerator.getInstance(logFilePath);

        boolean shouldCreateNewFile = false;

        if (fileGenerator.isExists()) {
            if (checkOldFile()) {
                shouldCreateNewFile = true;
            }
        }else {
            fileGenerator.create();
        }
        if (shouldCreateNewFile) {
            fileGenerator.create();
        }
    }

    private static boolean checkOldFile() {
        boolean renamed = false;
        if (fileGenerator.isExists()) {
            fileUtils = fileGenerator.getFileUtils();
            details = fileGenerator.getFiledetails();
            boolean dateChecks = Functions.daysComparison(details.getCreationDate(), Functions.now());
            if (!dateChecks) {
                Path filePath = getLogFile(true);
                renamed = fileUtils.moveFile(filePath);
            }
        }
        System.out.println("renamed: " + renamed);
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
