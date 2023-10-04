package com.chat.model;
import javax.persistence.*;

@Entity
public class Pbk {
	@Id
	private String username;
	
	private String PublicKey;
	private String N;
	public Pbk() {}
	public String getGmail() {
		return username;
	}
	public void setGmail(String gmail) {
		username = gmail;
	}
	public void setPublicKey(String publicKey) {
		PublicKey = publicKey;
	}
	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}
	public Pbk(String gmail, String PublicKey, String n) {
		username = gmail;
		this.PublicKey = PublicKey;
		N = n;
	}
	public String getPublicKey() {
		return PublicKey;
	}
	@Override
	public String toString() {
		return "Public [Gmail=" + username + ", PublicKey=" + PublicKey + ", N=" + N + "]";
	}
}
