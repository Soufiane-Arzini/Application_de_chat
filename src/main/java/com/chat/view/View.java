package com.chat.view;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import com.chat.controller.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.chat.controller.Controller;
import com.chat.model.ClientSide;
import com.chat.model.Keys;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.FlowLayout;

public class View extends JFrame {
	private JLabel target ;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField username_field;
	private JPasswordField password_field;
	private JPanel login;
	private JLayeredPane BigContainer;
	private JPanel chat;
	public static JTextArea messageArea ;
	private JTextField input;
	private JTextField TO_target;
	private String username;
	private static String TO="";
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				Controller DBO = Controller.getInstance();
				System.out.println("hibernate done ");
			}
		}.start();
		try {
			FlatLightLaf.setup();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public View() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 597, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		BigContainer = new JLayeredPane();
		BigContainer.setBounds(0, 0, 581, 583);

		
		contentPane.add(BigContainer);
		
		chat = new JPanel();

		chat.setLayout(null);
		
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				messageArea.append("admi : "+input.getText().toString()+"\n");
				sendToServer();
			}
		});
		send.setBounds(482, 547, 89, 31);
		chat.add(send);
		
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		messageArea.setBounds(143, 48, 428, 498);
		chat.add(messageArea);
		messageArea.setFont(new Font("Serif",Font.PLAIN,17));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 153, 102));
		panel.setBounds(0, 0, 143, 578);
		chat.add(panel);
		panel.setLayout(null);
		
		JLabel open = new JLabel("chat with");
		open.setHorizontalAlignment(SwingConstants.CENTER);
		open.setForeground(Color.WHITE);
		open.setFont(new Font("Roboto", Font.BOLD, 15));
		open.setBounds(3, 3, 143, 43);
		open.setAlignmentX(CENTER_ALIGNMENT);
		open.setAlignmentY(CENTER_ALIGNMENT);
		panel.add(open);
		
		JButton btnNewButton = new JButton("exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnNewButton.setBounds(12, 251, 121, 31);
		panel.add(btnNewButton);
		
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setVgap(10);
		panel_1.setBackground(new Color(0, 153, 102));
		panel_1.setBounds(143, 0, 428, 50);
		chat.add(panel_1);
		
		TO_target = new JTextField();
		TO_target.setForeground(Color.BLACK);
		panel_1.add(TO_target);
		TO_target.setBackground(new Color(255, 255, 255));
		TO_target.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
		TO_target.setColumns(10);
		
		JButton confirm = new JButton("Confirm");
		panel_1.add(confirm);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
		
		target = new JLabel("(Not Selected Yet)");
		target.setForeground(Color.WHITE);
		target.setFont(new Font("Monospaced", Font.BOLD, 20));
		panel_1.add(target);
		
		input = new JTextField();
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToServer();
			}	
		});
		input.setBounds(143, 547, 341, 31);
		chat.add(input);
		input.setColumns(10);
		BigContainer.setLayout(new CardLayout(0, 0));
		
		
		
		login = new JPanel();
		login.setBackground(new Color(0, 153, 102));
		BigContainer.add(login, "name_42299968219300");
		login.setLayout(null);
		BigContainer.add(chat, "name_42300143883000");
		
		username_field = new JTextField();
		username_field.setText("");
		username_field.setForeground(Color.BLACK);
		username_field.setColumns(10);
		username_field.setBorder(new LineBorder(Color.WHITE, 2));
		username_field.setBackground(new Color(255, 255, 255));
		username_field.setBounds(121, 297, 318, 28);
		login.add(username_field);
		
		password_field = new JPasswordField();
		password_field.setForeground(Color.BLACK);
		password_field.setColumns(10);
		password_field.setBorder(new LineBorder(Color.WHITE, 2));
		password_field.setBackground(new Color(255, 255, 255));
		password_field.setBounds(121, 379, 318, 28);
		login.add(password_field);
		
		JButton logIn = new JButton("Log in");
		logIn.setBackground(Color.WHITE);
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = username_field.getText().toString();
				String password = password_field.getText().toString();
				if(!username.isEmpty() && !password.isEmpty()) {
					//check with sha256
					Controller operation = Controller.getInstance();
					boolean accepted = operation.checkUserNameExistance(username);
					if(accepted) {					
						openTheSocket();
						
					}else {
						 JOptionPane.showMessageDialog(View.this,"Something went wrong !","error",JOptionPane.WARNING_MESSAGE);
					}
				}else {
					 JOptionPane.showMessageDialog(View.this,"please fill all fields","error",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		logIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			System.out.println("235 rja3 dkchi fin kan bla sda3 ras");	
			}
		});
		logIn.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		logIn.setBounds(189, 454, 187, 28);
		login.add(logIn);
		
		JLabel lblNewLabel_2 = new JLabel("username");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
		lblNewLabel_2.setBounds(121, 280, 86, 14);
		login.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_2 = new JLabel("Password");
		lblNewLabel_2_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 13));
		lblNewLabel_2_2.setBounds(121, 357, 73, 14);
		login.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("");
		lblNewLabel_2_1_2.setIcon(new ImageIcon(View.class.getResource("/icons/icons8_customer_100px_5.png")));
		lblNewLabel_2_1_2.setBounds(261, 128, 56, 86);
		login.add(lblNewLabel_2_1_2);
		
		
		
		
	}
	
	protected void confirm() {
		String tar = TO_target.getText().toString();
		if(!tar.isEmpty()) {
			
			TO=tar;
			TO_target.setText("");
			Controller.getInstance().fetchPublicKey(tar);
			if(Keys.pk==null) {
				 JOptionPane.showMessageDialog(this,"Oops, look like this user doesn't have the App","partagi l'app ashbi",JOptionPane.WARNING_MESSAGE);
				 TO = "";
			}else 
				target.setText(tar);
			
				
		}else 
			 JOptionPane.showMessageDialog(this,"Enter a user name to chat with !","wa si mohamed",JOptionPane.ERROR_MESSAGE);
		
	}

	protected void sendToServer() {
		if(!TO.isEmpty()) {
			try {
				String msg = input.getText().toString();
				if(!msg.isEmpty()) {
//					ClientSide.write(msg,TO);
					Controller.getInstance().writeMessageForClient(msg,TO);
					messageArea.append("You : "+input.getText().toString()+"\n");
					input.setText("");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				
			}
		}else 
			 JOptionPane.showMessageDialog(this,"Enter a valide user name to chat with !","error",JOptionPane.ERROR_MESSAGE);
		
	}

	protected void openTheSocket() {
		try {
			Controller.getInstance().openSocketForClient(username);
			SwitchBig(chat);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,"Server issue !, please try again later !","error",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	public void SwitchBig(JPanel p) {
		BigContainer.removeAll();
		BigContainer.add(p);
		BigContainer.repaint();
		BigContainer.revalidate();
	}
}
