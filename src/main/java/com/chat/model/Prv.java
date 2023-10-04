package com.chat.model;
import javax.persistence.*;

@Entity
public class Prv {
	@Id
	private String username;
	
	private String PrivateKey;
	private String N;
	public Prv() {}
	public String getGmail() {
		return username;
	}
	public void setGmail(String gmail) {
		username = gmail;
	}
	public String getPrivateKey() {
		return PrivateKey;
	}
	public void setPrivateKey(String privateKey) {
		PrivateKey = privateKey;
	}
	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}
	public Prv(String gmail, String privateKey, String n) {
		
		username = gmail;
		PrivateKey = privateKey;
		N = n;
	}
	@Override
	public String toString() {
		return "Private [Gmail=" + username + ", PrivateKey=" + PrivateKey + ", N=" + N + "]";
	}
	
	

}
