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
        return random(CHARACTERS + "-", index);
    }

    public static String generateRandomPassword(int index) {
        return random(CHARACTERS + "#$*!%€&@£µ:-_", index);
    }

    private static String random(String characters, int index) {
        StringBuilder sb = new StringBuilder(index);
        for (int i = 0; i < index; i++) {
            int n = (int) (characters.length() * Math.random());
            sb.append(characters.charAt(n));
        }
        return sb.toString();
    }
}
