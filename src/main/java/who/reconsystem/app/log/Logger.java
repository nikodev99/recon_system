package who.reconsystem.app.log;

public class Logger {

    private final Object className;

    public Logger(Object className) {
        this.className = className;
    }

    public static Logger getLogger(Object aClass) {
        return new Logger(aClass);
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

}
