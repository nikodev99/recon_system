package who.reconsystem.app.root.config;

import java.security.SecureRandom;

public class StrongIdGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateSecureRandomId(int id_length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(id_length);
        for (int i = 0; i < id_length; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static String generateRandomString(int index) {
        StringBuilder sb = new StringBuilder(index);
        for (int i = 0; i < index; i++) {
            int n = (int) (CHARACTERS.length() * Math.random());
            sb.append(CHARACTERS.charAt(n));
        }
        return sb.toString();
    }
}
