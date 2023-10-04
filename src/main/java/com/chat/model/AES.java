package com.chat.model;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

import java.util.Base64;

public class AES {
	//key size VALUES : 128,192,256
	//T_LEN VALUES :128,120,112,104,96 
	private static final int size = 128;
	private static final int T_LEN = 96;
	private Cipher encCiph;
	public AES() throws NoSuchAlgorithmException {
		keyGeneration();
	}
	
	private  SecretKey key ;
	
	public void keyGeneration() throws NoSuchAlgorithmException{
		
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(size);
		setKey(generator.generateKey());
		
	}
	
	public static String encrypt(final String strToEncrypt, SecretKey secretKey) {
	    try {
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	      return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	    } catch (Exception e) {
	      System.out.println("Error while encrypting: " + e.toString());
	    }
	    return null;
	  }

	  public static String decrypt(final String strToDecrypt,  SecretKey secretKey) {
	    try {
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	      cipher.init(Cipher.DECRYPT_MODE, secretKey);
	      return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(strToDecrypt)),"UTF-8");
	      
	    } catch (Exception e) {
	      System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	  }

	
	
	private static String encode(byte[] doFinal) {return java.util.Base64.getEncoder().encodeToString(doFinal);}
	private static byte[] decode(String Cmsg) {return java.util.Base64.getDecoder().decode(Cmsg);}
	
	public static void main(String[] args){
		try {
			AES a = new AES();
			String d = "admi zakaryae don't give up";
			String msg = a.encrypt(d,a.getKey());
			System.out.println(msg);
			System.out.println(a.decrypt(msg,a.getKey()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	public SecretKey getKey() {
		return key;
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}
	
	

}





























