import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoAES {
	private static final int AES_KEY_SIZE = 256;
	private static final int GCM_IV_LENGTH = 12;
	private static final int GCM_TAG_LENGTH = 16;

	private static SecretKey secretKey;
	private static byte[] GCM_IV;
	private static Cipher cipher;
	private static SecretKeySpec keySpec;
	private static GCMParameterSpec gcmParameterSpec;

	static {
		// generate key AES algorithm
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(1);
		}
		keyGenerator.init(AES_KEY_SIZE);
		secretKey = keyGenerator.generateKey();

		// initialize GCM IV
		GCM_IV = new byte[GCM_IV_LENGTH];
		SecureRandom random = new SecureRandom();
		random.nextBytes(GCM_IV);

		// get cipher instance
		try {
			cipher = Cipher.getInstance("AES/GCM/NoPadding");
			keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
			gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, GCM_IV);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static byte[] encrypt(byte[] plainText)
			throws InvalidKeyException,
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException,
			BadPaddingException {

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
		byte[] encryptedText = cipher.doFinal(plainText);
		return encryptedText;
	}

	public static byte[] decrypt(byte[] plainText)
			throws InvalidKeyException,
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException,
			BadPaddingException {

		cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
		byte[] decryptedText = cipher.doFinal(plainText);
		return decryptedText;
	}
}
