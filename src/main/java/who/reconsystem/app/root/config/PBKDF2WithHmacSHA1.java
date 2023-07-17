package who.reconsystem.app.root.config;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;

public class PBKDF2WithHmacSHA1 {

    public static String hashPassword(String passwordToHash) {
        int iteration = 1000;
        char[] chars = passwordToHash.toCharArray();
        byte[] salt = passwordToHash.getBytes();
        String hashedPassword = "";

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iteration, 64 * 8);
        try {

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            hashedPassword = iteration + ":" + toHex(salt) + ":" + toHex(hash);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    public static boolean verifyPassword(String providedPassword, String storedPassword) {
        if (storedPassword.contains(":")) {
            String[] parts = storedPassword.split(":");
            int iteration = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);
            PBEKeySpec spec = new PBEKeySpec(providedPassword.toCharArray(), salt, iteration, hash.length * 8);
            try {

                SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
                byte[] testHash = skf.generateSecret(spec).getEncoded();
                int diff = hash.length ^ testHash.length;
                for(int i = 0; i < hash.length && i < testHash.length; i++) {
                    diff |= hash[i] ^ testHash[i];
                }
                return diff == 0;

            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static String toHex(byte[] bytes) {

        BigInteger bi = new BigInteger(1, bytes);
        String hex = bi.toString(16);
        int paddingLength = (bytes.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }else {
            return hex;
        }

    }

    private static byte[] fromHex(String hex) {

        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
