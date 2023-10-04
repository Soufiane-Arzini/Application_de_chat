package com.chat.model;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

public class ServerSide extends Thread{
		private int ClientNumber = 0;
		private List<Conversation> socketsConversation = new ArrayList<Conversation>();
		@Override
		public void run() {	
			try {
				ServerSocket ss = new ServerSocket(1234);
				System.out.println("server Started !");
				while(true) {
					Socket s = ss.accept();
					ClientNumber++;
					Conversation conver = new Conversation(s,ClientNumber);
					socketsConversation.add(conver);
					conver.start();
				}
			} catch (IOException e) {
				e.printStackTrace();}
		}
		
		
		
		public static void main(String[] args) {
			new ServerSide().start();
		}

		class Conversation extends Thread{
			private final Socket s;
			private String clientName;
			
			
			
			private final int clientNumber;
			Conversation(Socket s,int clientNumber){
				this.s = s;
				this.clientNumber = clientNumber;
			}
			public Socket getS() {return s;}
			public int getClientNumber() {return clientNumber;}
			
			public void setClientName(String clientName) {
				this.clientName = clientName;
			}
			public String getClientName() {
				return this.clientName;
			}
			@Override
			public void run() {
				System.out.println("A new Client has Connected!");
				try {
					InputStream  is = s.getInputStream();
					OutputStream os = s.getOutputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					PrintWriter pt = new PrintWriter(os,true);
					
						pt.println("Enter Your User Name for the Group Chat : ");
						String username = br.readLine();
						if(username!=null) {
							this.setClientName(username);
							//debugging
							BroadCastTheMessage("SERVER : "+username+" has entered the chat!", this);
						}
						
						while(true){
							String request;
							while((request = br.readLine())!=null)
									UnicastTheMessage(this.getClientName()+"~"+request.trim(), this);
							//debugging
							BroadCastTheMessage("client number "+ClientNumber+" exit the conversation !",this);
							socketsConversation.remove(this);
						}
						
				}
					
				catch (Exception e) {
					//debugging
					System.err.println("maybe a client has left the chat !"+e.getMessage());
					BroadCastTheMessage("SERVER : "+this.getClientName()+" has left the conversation!", this);
				}
				
			} 
			
		}
		
		public void BroadCastTheMessage(String msg,Conversation or) {
			
			for(int i=0;i<socketsConversation.size();i++){
				try {	
					OutputStream os = socketsConversation.get(i).getS().getOutputStream();
					PrintWriter p = new PrintWriter(os,true);
					//u can activate the group chat by commenting the 103 105
					if(or.getClientNumber()!=socketsConversation.get(i).getClientNumber()) {
						p.println(msg);
					}
				}
					
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}



		public void UnicastTheMessage(String msg, Conversation conversation) {
			
			int pos_dollar = msg.indexOf('$');
			int pos_hashtag = msg.indexOf('#');
			String Username = msg.substring(pos_dollar+1,pos_hashtag);
			for(int i=0;i<socketsConversation.size();i++){
				try {	
					String client = socketsConversation.get(i).getClientName();
					if(client.equals(Username.trim())) {
						OutputStream os = socketsConversation.get(i).getS().getOutputStream();
						PrintWriter p = new PrintWriter(os,true);
						p.println(msg);
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	
}
