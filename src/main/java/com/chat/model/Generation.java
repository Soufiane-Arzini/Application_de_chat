package com.chat.model;


import java. math.*;
import java.util.*;


import com.chat.controller.Controller;

public class Generation {
	
	
	private BigInteger PublicKey,PrivateKey,N,PHI,p,q;
	private final BigInteger One = new BigInteger("1");
	
public BigInteger getPublicKey() {
		return PublicKey;
	}

	public BigInteger getPrivateKey() {
		return PrivateKey;
	}

	public BigInteger getN() {
		return N;
	}

	public Generation() {
	GenerateKey();
		}
	
	public Generation(String username) {
		
		GenerateKey();
		
		Pbk pu = new Pbk(username,this.PublicKey.toString(),this.N.toString());
		Prv pr = new Prv(username,this.PrivateKey.toString(),this.N.toString());
		Keys.pv = pr;
		System.out.println("private key is set to Key class");
		Controller.getInstance().setPrivateKeyToDataBase(pr);
		Controller.getInstance().setPublicKeyToDataBase(pu);
		
	}

	private void GenerateKey(){
		  this.p = BigInteger.probablePrime(420, new Random());
		  this.q = BigInteger.probablePrime(420, new Random());
		  this.N =(this.p).multiply(this.q);
		  this.PHI = ((this.p).subtract(One)).multiply(((this.q).subtract(One)));
		  this.PublicKey = BigInteger.probablePrime(128, new Random());
		  
		  while(PHI.gcd(PublicKey).compareTo(BigInteger.ONE)>0 && PublicKey.compareTo(PHI)<0) {
			  PublicKey.add(BigInteger.ONE);
			}
		  this.PrivateKey=PublicKey.modInverse(PHI);  
		  
	}

	
	
}
