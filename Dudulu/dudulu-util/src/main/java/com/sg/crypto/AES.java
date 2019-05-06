package com.sg.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AES {

    public final static String AES_KEY = "nbybt";
    // private final String KEY_GENERATION_ALG = "PBEWITHSHAANDTWOFISH-CBC";

    private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

    // private final int HASH_ITERATIONS = 10000;
    private final int HASH_ITERATIONS = 10;
    // private final int KEY_LENGTH = 256;
    private final int KEY_LENGTH = 128;

    private char[] humanPassphrase;

    // char[] humanPassphrase = { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38
    // };
    private byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD,
            0xE, 0xF }; // must save this for next time we want the key

    // private final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";
    private final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";

    private SecretKeyFactory keyfactory = null;
    private SecretKey sk = null;
    private SecretKeySpec skforAES = null;
    private byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC,
            0xD, 91 };

    private IvParameterSpec IV;

    public AES(char[] key) {

        try {
            keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
            humanPassphrase = key;
            PBEKeySpec myKeyspec = new PBEKeySpec(humanPassphrase, salt,
                    HASH_ITERATIONS, KEY_LENGTH);
            sk = keyfactory.generateSecret(myKeyspec);

        } catch (NoSuchAlgorithmException nsae) {
        } catch (InvalidKeySpecException ikse) {
        }

        byte[] skAsByteArray = sk.getEncoded();
        skforAES = new SecretKeySpec(skAsByteArray, "AES");
        IV = new IvParameterSpec(iv);

    }

    public String encrypt(byte[] plaintext) {

        byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
        String base64_ciphertext = Base64Encoder.encode(ciphertext);
        return base64_ciphertext;
    }

//	public String decrypt(String ciphertext_base64) {
//		byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
//		String decrypted = "";
//		try {
//			decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV,
//					s),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return decrypted;
//	}

    // Use this method if you want to add the padding manually
    // AES deals with messages in blocks of 16 bytes.
    // This method looks at the length of the message, and adds bytes at the end
    // so that the entire message is a multiple of 16 bytes.
    // the padding is a series of bytes, each set to the total bytes added (a
    // number in range 1..16).
    @SuppressWarnings("unused")
    private byte[] addPadding(byte[] plain) {
        byte plainpad[] = null;
        int shortage = 16 - (plain.length % 16);
        // if already an exact multiple of 16, need to add another block of 16
        // bytes
        if (shortage == 0)
            shortage = 16;

        // reallocate array bigger to be exact multiple, adding shortage bits.
        plainpad = new byte[plain.length + shortage];
        for (int i = 0; i < plain.length; i++) {
            plainpad[i] = plain[i];
        }
        for (int i = plain.length; i < plain.length + shortage; i++) {
            plainpad[i] = (byte) shortage;
        }
        return plainpad;
    }

    // Use this method if you want to remove the padding manually
    // This method removes the padding bytes
    @SuppressWarnings("unused")
    private byte[] dropPadding(byte[] plainpad) {
        byte plain[] = null;
        int drop = plainpad[plainpad.length - 1]; // last byte gives number of
        // bytes to drop

        // reallocate array smaller, dropping the pad bytes.
        plain = new byte[plainpad.length - drop];
        for (int i = 0; i < plain.length; i++) {
            plain[i] = plainpad[i];
            plainpad[i] = 0; // don't keep a copy of the decrypt
        }
        return plain;
    }

    private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                           byte[] msg) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.ENCRYPT_MODE, sk, IV);
            return c.doFinal(msg);
        } catch (NoSuchAlgorithmException nsae) {
            // Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException nspe) {
            // Log.e("AESdemo", "no cipher getinstance support for padding " +
            // cmp);
        } catch (InvalidKeyException e) {
            // Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException e) {
            // Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            // Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException e) {
            // Log.e("AESdemo", "bad padding exception");
        }
        return null;
    }

    private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                           byte[] ciphertext) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.DECRYPT_MODE, sk, IV);
            return c.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException nsae) {
            // Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException nspe) {
            // Log.e("AESdemo", "no cipher getinstance support for padding " +
            // cmp);
        } catch (InvalidKeyException e) {
            // Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException e) {
            // Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            // Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException e) {
            // Log.e("AESdemo", "bad padding exception");
            e.printStackTrace();
        }
        return null;
    }

    //	public static void main(String args[]) {
//		String key="2930a074d6c34888a1bd173d0fd2a62d";
////		String miwen=getMiwen(key,"新海");
//		String miwen="R8dbSsjBbKARmPTgnf+26A==";
//		System.out.println("密文："+miwen);
//		String mingwen=getMingwen(key,miwen);
//		System.out.println("明文："+mingwen);
//	}
    public static String getMiwen(String key,String pureStr){
        String str="";
        AES aes = new AES(key.toCharArray());
        try {
            str=aes.encrypt(pureStr.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return str;
    }
//	public static String getMingwen(String key,String secStr){
//		AES aes = new AES(key.toCharArray());
//		String str = aes.decrypt(secStr);
//		System.out.println(str);
//		return str;
//	}
}
