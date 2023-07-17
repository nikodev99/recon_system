package who.reconsystem.app.root.config;

import java.security.SecureRandom;

public class StrongIdGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateId(int id_length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(id_length);
        for (int i = 0; i < id_length; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
