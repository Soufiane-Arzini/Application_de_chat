package com.chat.model;

import java.math.BigInteger;

public class Chiffr_Dechiffr {

	private String StringToInt(String TheMsg ) {
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < TheMsg.length(); i++) {
			int num = (int)TheMsg.charAt(i);
			if(num<100) {
				msg.append("0"+num);
				
			}else {
				msg.append(num);
			}
		}
		
		return msg.toString();
	}
	private String IntToString(String msg) {
		StringBuilder new_Msg = new StringBuilder();
		String Msg = new String("");
		int myMsg;
		if(msg.length()%3!=0) {
			msg="0"+msg;
		}
		for (int i = 0; i < msg.length(); i+=3) {
			
			for(int j=i;j<i+3;j++) {
				System.out.println("i = "+i+",j = "+j);
				Msg+=msg.charAt(j);
			}
			myMsg = Integer.parseInt(Msg);
			new_Msg.append((char)myMsg);
			Msg="";
		}
		return new_Msg.toString();
	}
	
	public String Chiffrement(String Msg,Pbk pub) {
		
		Msg = this.StringToInt(Msg);
		BigInteger Pub = new BigInteger(pub.getPublicKey()); 
		BigInteger n = new BigInteger(pub.getN());
		BigInteger MSG = new BigInteger(Msg);
		
		BigInteger cipherMsg = MSG.modPow(Pub,n);
		return cipherMsg.toString();
		
	}	
	public String Dechiffrement(String Msg,Prv prv) {
		BigInteger PrvKey = new BigInteger(prv.getPrivateKey());
		BigInteger N = new BigInteger(prv.getN());
		
		BigInteger the_Msg = new BigInteger(Msg);
		
		BigInteger ClearMsg = the_Msg.modPow(PrvKey,N);
		String cleanMsg = this.IntToString(ClearMsg.toString());
		return cleanMsg;
	}
	
	
}
