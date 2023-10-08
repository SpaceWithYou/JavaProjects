package pack;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MakeHash {

    public MakeHash() {
    }

    private String convertToHex(byte[] arr) {
        BigInteger bigint = new BigInteger(1, arr);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    public String makeHash256(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(text.getBytes(StandardCharsets.UTF_8));
        return convertToHex(messageDigest);
    }

    public String makeHashMd5(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = md.digest(text.getBytes(StandardCharsets.UTF_8));
        return convertToHex(messageDigest);
    }
}
