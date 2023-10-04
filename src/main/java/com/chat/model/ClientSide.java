package com.chat.model;

import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.*;

import javax.crypto.SecretKey;
import javax.swing.JLabel;

import com.chat.controller.Controller;
import com.chat.view.View;

public class ClientSide {
	static Controller DBO = Controller.getInstance();
	final static String TO = "imda";
	private static Socket socket;
	private static OutputStream o;
	private static PrintWriter p=null;
	private static InputStream i;
	private static BufferedReader br;
	private static String hash;

	public static void write(String msg,String To) throws Exception{
		String CM = encryptMessage(msg,To);
		if(CM!=null)
			p.println(CM);


	}

	public static void read() {
		Thread read = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						String input = br.readLine();
						if(!input.contains("SERVER")) {
							if(input!=null) {
								String name = input.substring(0,input.indexOf('~'));
								System.out.println(name);
								System.out.println("the encrypted message is : "+input);
								hash = getHashedMessage(input);
								String cipher = fixInput(input);
								String cipherMessage = extractCipherMessage(cipher);
								System.out.println("the cipher message is : "+cipher);
								_AES AESObject = extractAESObject(cipher);
								System.out.println("the aes object is extracted successfully !");
								String clearMessage = decryptMessage(cipherMessage.trim(),AESObject.getAESKey());
								if(clearMessage!=null) {
									if(isHashWrong(clearMessage)) {
										System.out.println("the hash is correct !!");
										View.messageArea.append(name+" : "+clearMessage+"\n");


									}

									else
										System.err.println("someone changed the message");
									//try to show up a dialogue
								}
								else
									//debugging
									System.err.println("error while crypting the msg");
							}

						}else
							View.messageArea.append(input+"\n");


					} catch (IOException e) {
						//show dialogu
						System.err.println("server is closed !");
						System.exit(-1);
						break;
					}
				}
			}

			private boolean isHashWrong(String clearMessage) {
				System.out.println("\n\n\n\nthe old hash is : "+hash);
				HashFunction hashfunction = new HashFunction();
				String hashedMessage = hashfunction.ToHashFormat(clearMessage);
				return hashfunction.compareHash(hashedMessage, hash);
			}

			private String getHashedMessage(String input) {

				StringBuilder hashMsg = new StringBuilder("");
				for(int i=input.length();i>0;i--) {
					char ca = input.charAt(i-1);
					if(ca != ';')
						hashMsg.append(ca);
					else break;
				}
				System.err.println("\n\n\n\nthe has is : "+hashMsg.toString()+"\n\n\n\n");
				return hashMsg.reverse().toString();
			}

			private String decryptMessage(String cipher, SecretKey aesKey) {
				return AES.decrypt(cipher, aesKey);
			}
		};
		read.start();
	}

	public static void openSocket(String username) throws Exception {
		String serverHost = "localhost";
		int serverPort = 1234;
		socket = new Socket(serverHost,serverPort);

		o = socket.getOutputStream();
		i = socket.getInputStream();
		p = new PrintWriter(o,true);
		br = new BufferedReader(new InputStreamReader(i));

		String input = br.readLine();
		p.println(username);
		read();

	}


	public static String fixInput(String input) {
		String fixed = input.substring(input.indexOf('~')+1);
		return  fixed;
	}

	public static _AES extractAESObject(String input) {
		String obj = input.substring(input.indexOf('#')+1,input.length());
//		String obj = input.substring(input.indexOf('#')+1,input.charAt(';'));	
		System.out.println("the index of the character + is : "+input.charAt(';'));
		System.out.println("(inside extractAESobject)the object is : "+obj);
		String clearObject = decryptAESObject(obj,Keys.pv);
		_AES O = (_AES)Controller.getInstance().convertStringToObject(clearObject.trim());
		return O;
	}



	public static String extractCipherMessage(String input) {
		String cMsg = input.substring(0,input.indexOf('$'));
		return cMsg;
	}

	public static String encryptMessage(String msg,String to) throws Exception {
		_AES aeskey = Keys.AES;
		if(aeskey!=null) {
			String cipherText = AES.encrypt(msg,aeskey.getAESKey());
			StringBuilder sb = new StringBuilder(cipherText);
			String AESObj = Controller.getInstance().convertObjectToString(aeskey);
			if(Keys.pk==null) System.out.println("the public key is null");
			String encryptedAES = crypteAESOBJ(AESObj,Keys.pk);
			sb.append("$"+to+"#"+encryptedAES);
			sb = addHashFunction(sb,msg);
			return sb.toString();
		}
		return null;
	}

	private static StringBuilder addHashFunction(StringBuilder sb,String msg) {
		HashFunction hf = new HashFunction();
		String hashedMessage = hf.ToHashFormat(msg);
		sb.append(";"+hashedMessage);
		return sb;
	}

	private static String getPart(String name,int split) {
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
		System.out.println("the object is : \n"+CObj);
		Chiffr_Dechiffr cd = new Chiffr_Dechiffr();
		String cipher1 = CObj.substring(0,CObj.indexOf('@'));
		String cipher2 = CObj.substring(CObj.indexOf('@')+1,CObj.indexOf('|'));
		String cipher3 = CObj.substring(CObj.indexOf('|')+1,CObj.indexOf('*'));
//		String cipher4 = CObj.substring(CObj.indexOf('*')+1,CObj.length());
		String cipher4 = CObj.substring(CObj.indexOf('*')+1,CObj.indexOf(';'));
		System.out.println("cipher 4 is : "+cipher4);

		String clear1 = cd.Dechiffrement(cipher1.trim(), Keys.pv);
		String clear2 = cd.Dechiffrement(cipher2.trim(), Keys.pv);
		String clear3 = cd.Dechiffrement(cipher3.trim(), Keys.pv);
		String clear4 = cd.Dechiffrement(cipher4.trim(), Keys.pv);

		String clear = clear1+clear2+clear3+clear4;
		return clear;
	}








}
