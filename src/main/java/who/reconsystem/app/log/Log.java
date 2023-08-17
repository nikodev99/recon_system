package who.reconsystem.app.log;

import who.reconsystem.app.exception.FileGeneratorException;
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
        createLogFile();
        PropertyConfigurator.configure(fileGenerator);
        return Logger.getLogger(aClass);
    }

    public static void createLogFile() {
        Path logFilePath = getLogFile(true);
        fileGenerator = FileGenerator.getInstance(logFilePath);
        if (fileGenerator.isExists()) {
            if (checkOldFile()) {
                create();
            }
        }else {
            create();
        }
    }

    private static boolean checkOldFile() {
        FileUtils fileUtils = fileGenerator.getFileUtils();
        FileDetails details = fileGenerator.getFiledetails();
        boolean dateChecks = Functions.daysComparison(details.getCreationDate(), Functions.now());
        boolean renamed = false;
        if (!dateChecks) {
            Path filePath = getLogFile(true);
            renamed = fileUtils.moveFile(filePath);
        }
        return renamed;
    }

    private static void create() {
        try {
            fileGenerator.create();
        }catch (FileGeneratorException fi) {
            //TODO log
            fi.printStackTrace();
        }
    }

    private static Path getLogFile(boolean old) {
        Path logFile = Paths.get(Functions.getLocalePath(LOGFILE));
        if(!old) {
            String[] fileParts = LOGFILE.split("\\.");
            String ext = fileParts[1];
            logFile = Paths.get(fileParts[0] + "-" + "old" + "-" +  Functions.yesterday() + "." + ext);
        }
        return logFile;
    }

}
