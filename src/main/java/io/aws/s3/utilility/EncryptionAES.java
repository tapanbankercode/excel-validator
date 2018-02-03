package io.aws.s3.utilility;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/*
 * Created by Tapan N. Banker on 2/3/18.
 * @Author Tapan N. Banker
 */
public class EncryptionAES {

    private static final String ALGORITHM = "AES";
    private static final byte[] secretKeyValue =
            new byte[]{'B', 'o', 'o', 'Z', 'a', 'L', 'L', 'e', 'N', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) throws Exception {
        Key key = generateSecretKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateSecretKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateSecretKey() throws Exception {
        return new SecretKeySpec(secretKeyValue, ALGORITHM);
    }

}