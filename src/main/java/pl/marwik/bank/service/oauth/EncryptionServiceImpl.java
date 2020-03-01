package pl.marwik.bank.service.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class EncryptionServiceImpl {
    @Value("${encryption.salt}")
    private String salt;

    @Value("${encryption.secret}")
    private String secret;

    @Value("${encryption.algorithm}")
    private String algorithm;

    public String encrypt(String pass)  {
        try {
            Cipher ecipher = initCipher();
            byte[] utf8 = pass.getBytes(StandardCharsets.UTF_8);
            byte[] enc = ecipher.doFinal(utf8);
            enc = Base64.getEncoder().encode(enc);
            return new String(enc);
        } catch (BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | InvalidKeySpecException e) {
            throw new RuntimeException();
        }
    }

    private Cipher initCipher() throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance(algorithm);
        KeySpec keySpec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 10);
        SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt.getBytes(), 10);
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        return cipher;
    }
}
