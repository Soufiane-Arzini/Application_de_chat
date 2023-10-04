package com.chat.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Public_key implements Serializable{
	@Id
	private String userName;
	
	private String PublicKey;
	
	
	
	
	public Public_key(String userName, String publicKey) {
		this.userName = userName;
		PublicKey = publicKey;
	}

	public Public_key() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPublicKey() {
		return PublicKey;
	}

	public void setPublicKey(String publicKey) {
		PublicKey = publicKey;
	}
	
	
}
