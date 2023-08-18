package who.reconsystem.app.log;

import who.reconsystem.app.exception.ExceptionDialog;

public class Logger {

    private final Object className;

    public Logger(Object className) {
        this.className = className;
    }

    public static Logger getLogger(Object aClass) {
        return new Logger(aClass);
    }

    public void debug(String message) {
        PropertyConfigurator.write("DEBUG", className, message);
    }

    public void info(String message) {
        PropertyConfigurator.write("INFO", className, message);
    }

    public void error(String message) {
        PropertyConfigurator.write("ERROR", className, message);
    }

    public void warn(String message) {
        PropertyConfigurator.write("WARN", className, message);
    }

    public void fatal(String message) {
        PropertyConfigurator.write("FATAL", className, message);
    }

    public void critical(String message) {
        PropertyConfigurator.write("CRITICAL", className, message);
    }

    public void trace(Exception e) {
        String message = ExceptionDialog.getExceptionTrackTrace(e);
        PropertyConfigurator.write("EXCEPTION", className, message);
    }

}
