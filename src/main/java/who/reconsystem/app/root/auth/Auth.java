package who.reconsystem.app.root.auth;

import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.guice.bidings.QueryBiding;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.root.config.PBKDF2WithHmacSHA1;
import who.reconsystem.app.user.User;

public class Auth {

    private User loggedUser;

    public Auth(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean login(String username, String email, String password) {
        Table table = QueryBiding.useUserTable();
        loggedUser = table.loggedUser(username, email);
        boolean loggerStatus = false;
        if (loggedUser != null) {
            String loggedPassword = loggedUser.getPassword();
            loggerStatus = PBKDF2WithHmacSHA1.verifyPassword(password, loggedPassword);
        }else {
            String message = "L'utilisateurs non trouvé";
            Log.set(Auth.class).error(message);
            DialogMessage.errorDialog("Fatal Error", message);
        }
        return loggerStatus;
    }

    public void logout() {
        loggedUser.setUserId(null);
        loggedUser.setPassword(null);
    }

    public User loggedUser() {
        return loggedUser;
    }
}
