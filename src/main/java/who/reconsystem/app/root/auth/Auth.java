package who.reconsystem.app.root.auth;

import who.reconsystem.app.guice.QueryBiding;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.root.config.PBKDF2WithHmacSHA1;
import who.reconsystem.app.user.UserBean;

public class Auth {

    private UserBean loggedUser;

    public Auth(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean login(String username, String email, String password) {
        Table table = QueryBiding.useUserTable();
        loggedUser = table.loggedUser(username, email);
        boolean loggerStatus = false;
        if (loggedUser != null) {
            String loggedPassword = loggedUser.getPassword();
            loggerStatus = PBKDF2WithHmacSHA1.verifyPassword(password, loggedPassword);
        }
        return loggerStatus;
    }

    public void logout() {
        loggedUser.setUserId(null);
        loggedUser.setPassword(null);
    }

    public UserBean loggedUser() {
        return loggedUser;
    }


}
