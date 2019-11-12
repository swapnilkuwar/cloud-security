package com.cloud.security.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	public static boolean encrypt(String enc_type, MyFile currentFile) {
		if(enc_type.equals(new String("AES"))) {
			return aes_fileProccesser(Cipher.ENCRYPT_MODE, new File(currentFile.getFilepath()));
		}else if(enc_type.equals("RSA")) {
			return rsa_encrypt(currentFile);
		}else if(enc_type.equals("CPABE")) {
			return cpabe_encrypt(currentFile);
		}
		return false;
	}

	public static boolean decrypt(MyFile inputFile) {
		if(inputFile.getEnc_type().equals(new String("AES"))) {
			return aes_fileProccesser(Cipher.DECRYPT_MODE, new File(inputFile.getFilepath()));
		}else if(inputFile.getEnc_type().equals(new String("RSA"))) {
			return rsa_decrypt(inputFile);			
		}else if(inputFile.getEnc_type().equals(new String("CPABE"))) {
			return cpabe_decrypt(inputFile);			
		}
		return false;
	}


	private static boolean cpabe_encrypt(MyFile currentFile) {
		String input = currentFile.getFilepath();
		String output = currentFile.getFilepath() + "_enc";
		return CPABE.cpabeEncryt(input,output);
	}

	private static boolean cpabe_decrypt(MyFile currentFile) {
		String input = currentFile.getFilepath();
		String output = currentFile.getFilepath().substring(0, currentFile.getFilepath().length() - 4);
		return CPABE.cpabeDecryt(input,output);
	}
	
	public static boolean aes_fileProccesser(int cipherMode, File file){
		try {
			   String key = "this is a secret";
		       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
		       Cipher cipher = Cipher.getInstance("AES");
		       cipher.init(cipherMode, secretKey);
		       
		       String outputName;
		       if(cipherMode == 1) {
		    	   outputName = file.getAbsolutePath() + "_enc";
		       }else {
		    	   outputName = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
		       }
		       
		       FileInputStream fis = new FileInputStream(file);
		       FileOutputStream fos = new FileOutputStream(outputName);

		       byte[] in = new byte[100000];
				int read;
				while ((read = fis.read(in)) != -1) {
					byte[] output = cipher.update(in, 0, read);
					if (output != null)
						fos.write(output);
				}

				byte[] output = cipher.doFinal();
				if (output != null) {
					fos.write(output);
				}
				file.delete();
				fis.close();
		       fos.close();
		       return true;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	return false;
            }
	}

	private static byte[] EncryptSecretKey (Key pubKey, String key2)
	{
	    Cipher cipher = null;
	    byte[] key = null;
	    try
	    {
	        // initialize the cipher with the user's public key
	        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, pubKey );
	        key = cipher.doFinal(key2.getBytes());
	    }
	    catch(Exception e )
	    {
	        System.out.println ( "exception encoding key: " + e.getMessage() );
	        e.printStackTrace();
	    }
	    System.out.println (key);
	    return key;
	}
	
	private static SecretKey decryptAESKey(byte[] data, PrivateKey privateKey )
	{
	    SecretKey key = null;
	    Cipher cipher = null;

	    try
	    {
	        // initialize the cipher...
	        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey );

	        // generate the aes key!
	        key = new SecretKeySpec ( cipher.doFinal(data), "AES" );
	    }
	    catch(Exception e)
	    {
	        System.out.println ( "exception decrypting the aes key: " 
	                                               + e.getMessage() );
	        return null;
	    }

	    return key;
	}
	public static boolean rsa_encrypt(MyFile myFile){
		int cipherMode = Cipher.ENCRYPT_MODE;
		File file = new File(myFile.getFilepath());
		try {
			   String key = random();
		       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
		       Cipher cipher = Cipher.getInstance("AES");
		       cipher.init(cipherMode, secretKey);
		       
		       String outputName;
		       if(cipherMode == 1) {
		    	   outputName = file.getAbsolutePath() + "_enc";
		       }else {
		    	   outputName = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
		       }
		       
		       FileInputStream fis = new FileInputStream(file);
		       FileOutputStream fos = new FileOutputStream(outputName);

		       byte[] in = new byte[100000];
				int read;
				while ((read = fis.read(in)) != -1) {
					byte[] output = cipher.update(in, 0, read);
					if (output != null)
						fos.write(output);
				}

				byte[] output = cipher.doFinal();
				if (output != null) {
					fos.write(output);
				}
				file.delete();
				fis.close();
		       fos.close();

		        KeyPair keyPair = buildKeyPair();
		        Key pubKey = keyPair.getPublic();
		        PrivateKey privateKey = keyPair.getPrivate();
		        
		        myFile.setAes_Secretkey(EncryptSecretKey(pubKey, key));
		        myFile.setRSAprivateKey(privateKey);
		       return true;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	return false;
         }
	}
	
	public static boolean rsa_decrypt(MyFile myFile){
		try {

				File file = new File(myFile.getFilepath());
				Key secretKey = decryptAESKey(myFile.getAes_Secretkey(),myFile.getRSAprivateKey());
			   Cipher cipher = Cipher.getInstance("AES");
			   cipher.init(Cipher.DECRYPT_MODE, secretKey);
			   
			   String outputName;
			   outputName = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
			   
			   FileInputStream fis = new FileInputStream(file);
			   FileOutputStream fos = new FileOutputStream(outputName);
			
		       byte[] in = new byte[100000];
				int read;
				while ((read = fis.read(in)) != -1) {
					byte[] output = cipher.update(in, 0, read);
					if (output != null)
						fos.write(output);
				}

				byte[] output = cipher.doFinal();
				if (output != null) {
					fos.write(output);
				}
				file.delete();
				fis.close();
		       fos.close();
		       myFile.setAes_Secretkey(null);
		       myFile.setRSAprivateKey(null);
		       return true;
		       
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	return false;
            }
	}
	

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);      
        return keyPairGenerator.genKeyPair();
    }
    
    public static String random() {
    	char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    	StringBuilder sb = new StringBuilder(20);
    	Random random = new Random();
    	for (int i = 0; i < 16; i++) {
    	    char c = chars[random.nextInt(chars.length)];
    	    sb.append(c);
    	}
    	return sb.toString();
    }
}
