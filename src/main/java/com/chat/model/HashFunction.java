package com.chat.model;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class HashFunction {
	
	public String ToHashFormat(String password) {
		String hash = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		return hash;
	}
	public boolean compareHash(String pass1,String pass2) {
		System.out.println("comparing \n"+pass1+"\n"+pass2+"\n");
		
		return pass1.equals(pass2);
	}
	
}
