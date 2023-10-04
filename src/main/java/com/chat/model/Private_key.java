package com.chat.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Private_key implements Serializable{
	@Id
	private String userName;
	@Column(length=200)
	private String PrivateKey;
	
	public Private_key() {}
	public String getGmail() {
		return userName;
	}
	public void setGmail(String gmail) {
		userName = gmail;
	}
	public String getPrivateKey() {
		return PrivateKey;
	}
	public void setPrivateKey(String privateKey) {
		PrivateKey = privateKey;
	}
	public Private_key(String gmail, String privateKey) {
		
		userName = gmail;
		PrivateKey = privateKey;
	}
	@Override
	public String toString() {
		return "Private [ Gmail=" + userName + ", PrivateKey=" + PrivateKey +" ]";
	}
	
	

}
