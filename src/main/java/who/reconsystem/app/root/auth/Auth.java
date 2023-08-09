package who.reconsystem.app.root.auth;

import who.reconsystem.app.models.tables.UserTable;
import who.reconsystem.app.root.config.PBKDF2WithHmacSHA1;
import who.reconsystem.app.user.UserBean;

public class Auth {

    private UserBean loggedUser;

    private final UserTable table;

    public Auth(UserBean loggedUser, UserTable table) {
        this.loggedUser = loggedUser;
        this.table = table;
    }

    public boolean login(String username, String email, String password) {
        loggedUser = table.loggedUser(username, email);
        boolean loggerStatus = false;
        if (loggedUser != null) {
            String loggedPassword = loggedUser.getPassword();
            loggerStatus = PBKDF2WithHmacSHA1.verifyPassword(password, loggedPassword);
        }
        return loggerStatus;
    }

    public UserBean loggedUser() {
        return loggedUser;
    }
}
