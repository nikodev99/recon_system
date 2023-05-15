package who.reconsystem.app.dialog;

public class ExceptionDialog {
    private final String message;

    public ExceptionDialog(String message) {
        this.message = message;
    }

    public String getException() {
        StackTraceElement[] classInfo = Thread.currentThread().getStackTrace();
        String className = "Object"; int line = 0;
        for (StackTraceElement trackClass : classInfo) {
            String[] tracks = trackClass.getClassName().split("\\.");
            className = tracks[tracks.length - 1];
            line = trackClass.getLineNumber();
        }
        return className +":" + line + " " + message;
    }
}
