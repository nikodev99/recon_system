package who.reconsystem.app.root.auth;

import who.reconsystem.app.root.config.StrongIdGenerator;

public class Session {

    private Auth auth;

    private final String SESSION_FILENAME = StrongIdGenerator.generateRandomString(20);

    public Session(Auth auth) {
        this.auth = auth;
    }

    public void sessionStart() {

    }

    private void createFile() {

    }

}
