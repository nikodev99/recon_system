package who.reconsystem.app.root.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PBKDF2WithHmacSHA1Test {

    @Test
    public void testIsVerifiedCase() {
        String password = "PBKDF2WithHmacSHA512";
        String hashedPassword = PBKDF2WithHmacSHA1.hashPassword(password);
        boolean isVerified = PBKDF2WithHmacSHA1.verifyPassword(password, hashedPassword);

        assertTrue(isVerified);
    }

    @Test
    public void testIsNotVerifiedCase() {
        String hashedPassword = PBKDF2WithHmacSHA1.hashPassword("toto");
        boolean isNotVerified = PBKDF2WithHmacSHA1.verifyPassword("cecile", hashedPassword);

        assertFalse(isNotVerified);
    }

}
