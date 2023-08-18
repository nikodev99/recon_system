package who.reconsystem.app.log;

import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.root.config.Functions;

import java.nio.file.StandardOpenOption;

public class PropertyConfigurator {

    private static final String NEWLINE = System.lineSeparator();

    private static FileGenerator fileGenerator;

    private PropertyConfigurator() {
    }

    public static void configure(FileGenerator fileGenerator) {
        PropertyConfigurator.fileGenerator = fileGenerator;
    }

    public static void write(String logInfo, Object object, String message) {
        String date = Functions.instantDatetime("yyyy-MM-dd HH:mm:ss");
        StackTraceElement[] classInfo = Thread.currentThread().getStackTrace();
        String className = "Object";
        int line = 0;
        String method = "";
        for (StackTraceElement trackClass : classInfo) {
            String[] tracks = trackClass.getClassName().split("\\.");
            String[] objects = object.toString().split("\\.");
            String trackName = tracks[tracks.length - 1];
            String objectName = objects[objects.length - 1];
            if (trackName.equals(objectName)) {
                className = trackName;
                line = trackClass.getLineNumber();
                method = trackClass.getMethodName();
            }
        }
        String addMethodName = !method.isEmpty() ? "." + method: method;
        String lineMessage = date + " " + logInfo + " " + className + addMethodName +":" + line + " " + message;
        System.out.println(lineMessage);
        fileGenerator.getFileContent().setStandardOpenOption(StandardOpenOption.APPEND);
        fileGenerator.addContent(lineMessage + NEWLINE);
    }
}
