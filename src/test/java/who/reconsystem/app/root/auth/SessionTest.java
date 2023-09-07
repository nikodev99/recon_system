package who.reconsystem.app.root.auth;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {
    private final Session session;
    private User user;
    private final Auth auth;

    public SessionTest() {
        user = User.builder().build();
        auth = new Auth(user);
        this.session = new Session(auth);
    }

    @Test
    void sessionLifeTimeTest() {
        boolean login = auth.login("nikhe", "nikhe", "toto");
        if (login) {
            this.session.sessionStart();
            user = this.session.userLogged();
        }
        assertEquals("Nikhe", user.getFirstName());
        assertEquals("Niama", user.getLastName());
        assertEquals("nikhe", user.getUsername());

        assertEquals(session.getBinding().getCode(), user.getUserId());
    }
}
