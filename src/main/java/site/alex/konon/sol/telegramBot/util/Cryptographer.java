package site.alex.konon.sol.telegramBot.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Cryptographer {

    public static String encrypt(final String encrypting) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(encrypting));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        }
        catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}

