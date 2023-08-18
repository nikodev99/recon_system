package who.reconsystem.app.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDialog {

    public static synchronized String getExceptionTrackTrace(Exception e) {
        StackTraceElement[] classInfo = e.getStackTrace();
        List<String> contents = new ArrayList<>();
        contents.add(e.getMessage());
        contents.add("----- Track Trace -----");
        for (StackTraceElement element : classInfo) {
            contents.add(element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber());
            contents.add("-----");
        }
        return String.join(System.lineSeparator(), contents);
    }
}
