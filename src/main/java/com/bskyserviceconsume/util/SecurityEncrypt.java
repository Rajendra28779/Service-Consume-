package com.bskyserviceconsume.util;

import java.util.Base64;
import java.util.Random;

/**
 * @Project : BSKY Backend
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 20/03/2023 - 3:57 PM
 */

public class SecurityEncrypt {

    public static String encryptSecurityKey(String key) {
        if (key != null && !key.isEmpty())
            return makeRandom() + Base64.getEncoder().encodeToString(key.getBytes()) + makeRandom();
        else
            return key;
    }

    public static String decryptSecurityKey(String key) {
        if (key != null && !key.isEmpty()) {
            key = key.substring(5, key.length() - 5);
            return new String(Base64.getDecoder().decode(key));
        } else
            return key;
    }

    private static String makeRandom() {
        StringBuilder text = new StringBuilder();
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            text.append(possible.charAt(random.nextInt(possible.length())));
        }
        return text.toString();
    }

    public static boolean checkEncryptedSecurityKey(String encryptedSecurityKey) {
        try {
            return decryptSecurityKey(encryptedSecurityKey).equalsIgnoreCase("BSKY-KSIRL-OELOE-OEURE-KLGUR");
        } catch (Exception e) {
            return false;
        }
    }
}
