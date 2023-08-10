package who.reconsystem.app.root.auth;

import com.google.gson.Gson;
import who.reconsystem.app.exception.FileGeneratorException;
import who.reconsystem.app.guice.QueryBiding;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.io.FileReader;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.tables.UserTable;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.root.config.StrongIdGenerator;
import who.reconsystem.app.user.UserBean;

import java.util.List;

public class Session {
    private final String SESSION_FILENAME;

    private final long INACTIVITY_TIMOUT = 30 * 60 * 100;

    private final long lastActivityTime = System.currentTimeMillis();

    private File file;

    private final Auth auth;

    boolean isLogged = false;

    public Session(Auth auth) {
        this.auth = auth;
        SESSION_FILENAME = StrongIdGenerator.generateRandomString(20);
    }

    public void sessionStart() {
        UserBean user = auth.loggedUser();
        String filePath = Functions.getLocalePath("");
        file = FileGenerator.getInstance(SESSION_FILENAME, filePath);
        try {
            String content = "{" +
                    "\"code\": \""+ user.getUserId() +"\", " +
                    "\"lastActivity\": \""+ lastActivityTime +"\", " +
                    "\"isLogged\": \""+ isLogged +"\", " +
                    "\"creationDate\": \"" + Functions.now() + "\"}";
            file.create().addContent(content);
            //TODO adding log with the size of the file
        }catch (FileGeneratorException fge) {
            //TODO adding log
            fge.printStackTrace();
        }
    }

    private SessionBinding sessionLogger() {
        FileReader fileReader = file.getContent();
        List<String[]> lines = fileReader.read();
        String content = Functions.arrayToString(lines.get(0));
        Gson gson = new Gson();
        return gson.fromJson(content, SessionBinding.class);
    }

    public UserBean userLogged(SessionBinding binding) {
        Table table = QueryBiding.useUserTable();
        List<String> data = table.find(binding.getCode());
        return UserBean.populate(data);
    }

    public void setUpInactivity(SessionBinding binding) {
        if (checkInactivity(binding)) {
            isLogged = false;
            file.remove();
            auth.logout();
        }
    }

    private boolean checkInactivity(SessionBinding binding) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - binding.getLastActivity();
        return  elapsedTime >= INACTIVITY_TIMOUT;
    }

}
