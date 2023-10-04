package com.chat.model;

import java.io.Serializable;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class _AES implements Serializable{
	@Id
	private String userName;
	private SecretKey AES_KEY;
	
	public _AES(String userName, SecretKey publicKey) {
		this.userName = userName;
		AES_KEY = publicKey;
	}

	public _AES() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public SecretKey getAESKey() {
		return AES_KEY;
	}

	public void setAESKey(SecretKey publicKey) {
		AES_KEY = publicKey;
	}
	
	
}
