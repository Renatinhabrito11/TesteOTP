import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OTPGenerator {
  
  private static final String HMAC_ALGO = "HmacSHA1";
  private static final int OTP_LENGTH = 6;
  
  public static String generateOTP(String secretKey, long timeInMillis) throws InvalidKeyException, NoSuchAlgorithmException {
    long timeInSeconds = timeInMillis / 1000 / 30;
    byte[] data = new byte[8];
    long value = timeInSeconds;
    for (int i = 8; i-- > 0; value >>>= 8) {
      data[i] = (byte) value;
    }
    SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(secretKey), HMAC_ALGO);
    Mac mac = Mac.getInstance(HMAC_ALGO);
    mac.init(key);
    byte[] hash = mac.doFinal(data);
    int offset = hash[hash.length - 1] & 0xf;
    int truncatedHash = 0;
    for (int i = 0; i < 4; ++i) {
      truncatedHash <<= 8;
      truncatedHash |= (hash[offset + i] & 0xff);
    }
    truncatedHash &= 0x7FFFFFFF;
    truncatedHash %= (int) Math.pow(10, OTP_LENGTH);
    String otp = Integer.toString(truncatedHash);
    while (otp.length() < OTP_LENGTH) {
      otp = "0" + otp;
    }
    return otp;
  }
  
}
