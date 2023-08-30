package who.reconsystem.app.root.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import who.reconsystem.app.guice.bidings.QueryBiding;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.io.FileReader;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.root.StageLuncher;
import who.reconsystem.app.root.StageViewer;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.root.config.StrongIdGenerator;
import who.reconsystem.app.user.UserBean;

import java.nio.file.StandardOpenOption;
import java.util.List;

public class Session {
    private final String SESSION_FILENAME;

    private final long lastActivityTime = System.currentTimeMillis();

    private File file;

    private final Auth auth;

    @Getter
    @Setter
    private boolean isLogged = false;

    @Getter
    private SessionBinding binding;

    public Session(Auth auth) {
        this.auth = auth;
        SESSION_FILENAME = StrongIdGenerator.generateRandomString(20);
    }

    public void sessionStart() {
        UserBean user = auth.loggedUser();
        String filePath = Functions.getLocalePath("");
        file = new FileGenerator(SESSION_FILENAME, filePath);
        String content = "{" +
                "\"code\":\""+ user.getUserId() +"\"," +
                "\"lastActivity\":"+ lastActivityTime +"," +
                "\"isLogged\":"+ isLogged +"," +
                "\"creationDate\":\"" + Functions.now() + "\"}";
        file.create().addContent(content, StandardOpenOption.TRUNCATE_EXISTING);
        //TODO adding log with the size of the file
    }

    public UserBean userLogged() {
        sessionLogger();
        Table table = QueryBiding.useUserTable();
        List<String> data = table.find(binding.getCode());
        return UserBean.populate(data);
    }

    public void setUpInactivity() {
        setLogged(false);
        file.remove();
        auth.logout();
        StageLuncher stageLuncher = new StageLuncher("login", null);
        StageViewer viewer = new StageViewer(stageLuncher);
        viewer.show();
    }

    private void sessionLogger() {
        FileReader fileReader = file.getContent();
        List<String[]> lines = fileReader.read();
        String content = Functions.arrayToString(lines.get(0));
        Gson gson = new GsonBuilder().create();
        this.binding = gson.fromJson(content, SessionBinding.class);
    }
}
