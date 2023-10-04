package com.chat.controller;

import java.io.*;
import java.util.Base64;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.chat.model.*;

public class Controller {
	public SessionFactory sf;
	public SessionFactory osf;
		
		private static Controller instance;
		
		public static Controller getInstance() {
			if(instance==null) {
				instance = new Controller();
			}
			return instance;
		}
		
		private Controller() {
			openDatabaseSession();
		}
		private void openDatabaseSession() {
				Configuration conf= new Configuration().configure("hibernate2.xml")
						.addAnnotatedClass(Prv.class).addAnnotatedClass(_AES.class);
				ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
				sf = conf.buildSessionFactory(sr);
				Configuration oconf= new Configuration().configure("hibernate.xml")
						.addAnnotatedClass(Pbk.class);
				
				ServiceRegistry osr = new ServiceRegistryBuilder().applySettings(oconf.getProperties()).buildServiceRegistry();
				osf = oconf.buildSessionFactory(osr);
		}

		
		public boolean checkUserNameExistance(final String username) {
			Session s = sf.openSession();
			_AES aesKey = (_AES )s.get(_AES.class,username);
			new Thread() {
				public void run() {
					checkEmail(username);
				};
			}.start();
			
			if(aesKey==null) 
				return createNewUser(username);
			else 
				Keys.AES = aesKey;
			
			return true;
		}
		
		
		public boolean createNewUser(String userName) {
			try {
					AES A = new AES();
					_AES aes = new _AES(userName,A.getKey());
					
					Keys.AES = aes;
					setAESKeyToDataBase(aes);
					return true;
		
				}catch(Exception e) {
					e.printStackTrace();
					return false;
				}
		
		}
		private void setAESKeyToDataBase(_AES aes) {
			Session session = sf.openSession();
			session.beginTransaction();
			session.save(aes);
			session.getTransaction().commit();
			session.close();
	}

		
		public Object convertStringToObject(String serObject) {
			Object fetchedObject = null;
			//convertir le string a un tableaux de bit
			byte []ByteArray = Base64.getDecoder().decode(serObject);
			try(
					ByteArrayInputStream bais = new ByteArrayInputStream(ByteArray);
					ObjectInputStream oos = new ObjectInputStream(bais);){
					//desirialization
					fetchedObject = oos.readObject();
				}catch(Exception e) {e.printStackTrace();}
			return fetchedObject;
		}

		public String convertObjectToString(Object obj) {
			String serObject = null;
			try(
				//tableaux de bit 'bais'
				ByteArrayOutputStream bais = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bais);)
			{
				//serializer l'object 
				oos.writeObject(obj);
				//convertir le tableaux de bit a un string
				serObject = Base64.getEncoder().encodeToString(bais.toByteArray());
			}catch(Exception e) {e.printStackTrace();}
			return serObject;
		}

		public void setPrivateKeyToDataBase(Prv pr) {
			Session session = sf.openSession();
			session.beginTransaction();
			session.save(pr);
			session.getTransaction().commit();
			
			session.close();
		}

		public void setPublicKeyToDataBase(Pbk pu) {
			Session session = osf.openSession();
			session.beginTransaction();
			session.save(pu);
			session.getTransaction().commit();
			session.close();
			
		}
		
		
		public void checkEmail(String username) {
			Prv p1 = fetchPrivateKey(username);
			Pbk p2 = fetchPublicKey(username);
			if( p2==null || p1==null) {
				new Generation(username);
			}else {
				Keys.pv = p1;
				System.out.println("the private key of "+p1.getGmail()+" is set to key class");
			}
			
		}
		public Prv fetchPrivateKey(String username) {
			Session session = Controller.getInstance().sf.openSession();
			Prv p1 = (Prv) session.get(Prv.class,username);
			return p1;
		}
		
		public Pbk fetchPublicKey(String username) {
			Session session = Controller.getInstance().osf.openSession();
			Pbk p1 = (Pbk) session.get(Pbk.class,username);
			Keys.pk = p1;
			
//			System.out.println("setting the public key of "+p1.getGmail()+" in Key class to crypt the message");
			return p1;
			
		}

		public void writeMessageForClient(String msg, String TO) throws Exception {
			ClientSide.write(msg,TO);
		}

		public void openSocketForClient(String username) throws Exception {
			ClientSide.openSocket(username);
		}
		
		
		
		
		
		
}
