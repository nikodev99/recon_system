package who.reconsystem.app.root.auth;

import org.junit.jupiter.api.Test;
import who.reconsystem.app.user.User;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

    private final Auth auth;
    private User user;

    public AuthTest() {
        user = User.builder().build();
        this.auth = new Auth(user);
    }

    @Test
    void loginTest() {
        boolean trueTest = auth.login("nikhe", "nikhe", "toto");
        boolean falseTest = auth.login("test", "test", "toto");
        boolean trueTest0 = auth.login("nikhe@test.com", "nikhe@test.com", "toto");

        assertTrue(trueTest);
        assertFalse(falseTest);
        assertTrue(trueTest0);
    }

    @Test
    void loggedUserTest() {
        boolean trueTest = auth.login("nikhe", "nikhe", "toto");
        if (trueTest) {
            user = auth.loggedUser();
        }
        assertEquals("ZB1IueHrk1BnrER", user.getUserId());
    }

    @Test
    void logoutTest() {
        boolean trueTest = auth.login("nikhe", "nikhe", "toto");
        if (trueTest) {
            user = auth.loggedUser();
        }
        assertEquals("ZB1IueHrk1BnrER", user.getUserId());
        auth.logout();
        assertNull(user.getUserId());
    }
}
