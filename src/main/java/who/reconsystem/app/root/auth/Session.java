package who.reconsystem.app.root.auth;

import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.guice.QueryBiding;
import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.root.config.StrongIdGenerator;
import who.reconsystem.app.user.UserBean;

public class Session {
    private final String SESSION_FILENAME;

    private final long lastActivityTime = System.currentTimeMillis();

    public Session() {
        SESSION_FILENAME = StrongIdGenerator.generateRandomString(20);
    }

    public void sessionStart(Auth auth, boolean isLogged) {
        UserBean user = auth.loggedUser();
        String filePath = Functions.getLocalePath("");
        FileGenerator fileGenerator = FileGenerator.getInstance(SESSION_FILENAME, filePath);
        try {
            String content = "{code: "+ user.getUserId() +", lastActivity: "+ lastActivityTime +", isLogged: "+ isLogged +"}";
            long size = fileGenerator
                    .create()
                    .addContent(content);
            //TODO adding log with the size of the file
        }catch (FileGeneratorException fge) {
            //TODO adding log
            fge.printStackTrace();
        }
    }

    private void createFile() {

    }

}
