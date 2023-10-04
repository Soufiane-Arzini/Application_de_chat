package com.chat.model;

import java.security.*;

import javax.crypto.Cipher;

public class RSA {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public RSA(){
		
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(512);
			KeyPair key = generator.generateKeyPair();
			privateKey = key.getPrivate();
			publicKey = key.getPublic();
		}catch(Exception e) {}
	}
	
	public String encrypt(String msg,PublicKey PkKey) throws Exception{
		
		byte[] message = msg.getBytes();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE,PkKey);
		byte[] cryptedMessage = cipher.doFinal(message);
		return encode(cryptedMessage);
	}
	public String decrypt(String msg,PrivateKey PrKey) throws Exception {
		byte[] message = decode(msg);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, PrKey);
		
		return new String(cipher.doFinal(message),"UTF8");
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}


	public PublicKey getPublicKey() {
		return publicKey;
	}

	private static String encode(byte[] doFinal) {return java.util.Base64.getEncoder().encodeToString(doFinal);}
	private static byte[] decode(String Cmsg) {return java.util.Base64.getDecoder().decode(Cmsg);}
	
	
	public static void main(String[] args)throws Exception {
//		 RSA r = new RSA();
//		 AES a = new AES();
//		_AES A = new _AES("ADMI",a.getKey());
//		
		String Aes = "rO0ABXNyABZjb20uY2hhdC5zZWN1cml0eS5fQUVTQIM6dE344QUCAAJMAAdBRVNfS0VZdAAYTGphdmF4L2NyeXB0by9TZWNyZXRLZXk7TAAIdXNlck5hbWV0ABJMamF2YS9sYW5nL1N0cmluZzt4cHNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJMAAlhbGdvcml0aG1xAH4AAlsAA2tleXQAAltCeHB0AANBRVN1cgACW0Ks8xf4BghU4AIAAHhwAAAAEDcA/s42LNps4+vkFl9gvWt0AARBRE1J";
		System.out.println(Aes);
		
		String aes1 = getPart(Aes,1);
		String aes2 = getPart(Aes,2);
		String aes3 = getPart(Aes,3);
		String aes4 = getPart(Aes,4);
//		System.out.println(aes1+aes2+aes3+aes4);
		
		
		String a = "admi@";
//		System.out.println(a.indexOf('@'));
//		System.out.println(a.substring(0,a.indexOf('@')));
		
		new Generation("ADMI");
		Chiffr_Dechiffr cd = new Chiffr_Dechiffr();
		String CipherAesObject = crypteAESOBJ(Aes,Keys.pk);
		System.out.println(CipherAesObject);
		String ClearAesObject = decryptAESObject(CipherAesObject, Keys.pv);
		System.out.println("\n"+Aes+"\n\n"+ClearAesObject);
		
//		_AES D = (_AES)DBOperation.convertStringToObject(ClearAesObject);
//		System.out.println(D.getUserName());
		
		
		
//		
//		String cipher1 = cd.Chiffrement(aes1,Keys.pk);
//		String cipher2 = cd.Chiffrement(aes2,Keys.pk);
//		String cipher3 = cd.Chiffrement(aes3,Keys.pk);
//		String cipher4 = cd.Chiffrement(aes4,Keys.pk);
//		//!@#$%^&*()_+=-/\||';
		
//		String clear1 = cd.Dechiffrement(cipher1, Keys.pv);
//		String clear2 = cd.Dechiffrement(cipher2, Keys.pv);
//		String clear3 = cd.Dechiffrement(cipher3, Keys.pv);
//		String clear4 = cd.Dechiffrement(cipher4, Keys.pv);
//		String clear = clear1+clear2+clear3+clear4;
//		System.out.println(clear);
//		_AES A = (_AES)DBOperation.convertStringToObject(clear);
//		System.out.println(A.getUserName());
//		
//		System.out.println("public key : \n"+r.getPublicKey());
//		System.out.println("private key : \n"+r.getPrivateKey());
//		System.out.println("AES key is : \n"+A.getAESKey());
//		System.out.println("serializing the object ...");
//		String StringObject = DBOperation.convertObjectToString(A);
//		System.out.println(StringObject);
//		String CryptedObecjt = r.encrypt(StringObject, r.getPublicKey());
//		System.out.println("the crypted Obecjt is : \n"+CryptedObecjt);
//		System.out.println("derypting the object ...");
//		String DecryptedObject = r.decrypt(CryptedObecjt, r.getPrivateKey());
//		System.out.println("the derypted Object is : \n"+DecryptedObject);
//		
//		_AES reborn = (_AES)DBOperation.convertStringToObject(DecryptedObject);
		
		//try to split aes key to two part and encrypt both of them .
		
//		System.out.println(AES.decrypt(AES.encrypt("heloooo", reborn.getAESKey()), reborn.getAESKey()));
		
//		_AES S = (_AES)DBOperation.convertStringToObject(aes1+aes2);
//		System.out.println(S.getUserName());
		
		
//		byte[]Aes = Base64.getDecoder().decode(aes);
//		System.out.println(Aes);
//		String A = "[B@5f9d02cb";
//		String ddd = Base64.getEncoder().encodeToString(Aes);
//		System.out.println(ddd);
		
		
	}

	public static String getPart(String name,int split) {
		int num = name.length()/4;
		String part = null;
		switch(split) {
		case 1:
			part = name.substring(0,num);
			break;
		case 2:
			part = name.substring(num,num*2);
			break;
		case 3:
			part = name.substring(num*2,num*3);
			break;
		case 4:
			part = name.substring(num*3,name.length());
			break;
		default :
			break;
		}
			return part;
		
	}
	
	private static String crypteAESOBJ(String AESOBJECT,Pbk publicKey) {
		String part1 = getPart(AESOBJECT,1);
		String part2 = getPart(AESOBJECT,2);
		String part3 = getPart(AESOBJECT,3);
		String part4 = getPart(AESOBJECT,4);
		
		Chiffr_Dechiffr cd = new Chiffr_Dechiffr();
		String cipher1 = cd.Chiffrement(part1,publicKey);
		String cipher2 = cd.Chiffrement(part2,publicKey);
		String cipher3 = cd.Chiffrement(part3,publicKey);
		String cipher4 = cd.Chiffrement(part4,publicKey);
		String cipher = cipher1+"@"+cipher2+"|"+cipher3+"*"+cipher4;
		return cipher;
	}
	private static String decryptAESObject(String CObj,Prv privateKey) {
		Chiffr_Dechiffr cd = new Chiffr_Dechiffr();
		String cipher1 = CObj.substring(0,CObj.indexOf('@'));
		String cipher2 = CObj.substring(CObj.indexOf('@')+1,CObj.indexOf('|'));
		String cipher3 = CObj.substring(CObj.indexOf('|')+1,CObj.indexOf('*'));
		String cipher4 = CObj.substring(CObj.indexOf('*')+1,CObj.length());
		String clear1 = cd.Dechiffrement(cipher1, Keys.pv);
		String clear2 = cd.Dechiffrement(cipher2, Keys.pv);
		String clear3 = cd.Dechiffrement(cipher3, Keys.pv);
		String clear4 = cd.Dechiffrement(cipher4, Keys.pv);
		String clear = clear1+clear2+clear3+clear4;
		
		return clear;
	}
	
}





























