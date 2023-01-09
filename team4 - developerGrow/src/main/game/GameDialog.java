package main.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import du.dugame;

public class GameDialog extends JDialog {
	public GameDialog(int x, int y) {
		
		ClassLoader classloader = getClass().getClassLoader();
		
		setUndecorated(true);
		setModal(true);
		setBounds(x + 7, y + 30, 1185, 762);
		
		setBackground(new Color(0, 0, 0, 80));
		getContentPane().setLayout(null);
		
		JButton firstGameBtn = new JButton();
		URL first = classloader.getResource("btn_image/dodoBtn.png");
		ImageIcon firstGame = new ImageIcon(first);
		URL firstPush = classloader.getResource("btn_image/dodoPush.png");
		ImageIcon firstPushGame = new ImageIcon(firstPush);
		firstGameBtn.setIcon(firstGame);
		firstGameBtn.setPressedIcon(firstPushGame);
		firstGameBtn.setBorderPainted(false);
		firstGameBtn.setContentAreaFilled(false);
		firstGameBtn.setBackground(new Color(255, 0, 0, 0));
		firstGameBtn.setBounds(400, 200, 100, 50);
		firstGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dugame dugame = new dugame("");
				dispose();
				dugame.showGUI();
			}
		});
		getContentPane().add(firstGameBtn);
		
		JButton secondGameBtn = new JButton("2");
		secondGameBtn.setBounds(700, 200, 100, 50);
		getContentPane().add(secondGameBtn);
		
		JButton thirdGameBtn = new JButton("3");
		thirdGameBtn.setBounds(400, 400, 100, 50);
		getContentPane().add(thirdGameBtn);
		
		JButton FourthGameBtn = new JButton("4");
		FourthGameBtn.setBounds(700, 400, 100, 50);
		getContentPane().add(FourthGameBtn);
		
		JButton closeButton = new JButton();
		URL close = classloader.getResource("btn_image/closeBtnImage.png");
		ImageIcon closeImage = new ImageIcon(close);
		URL closePush = classloader.getResource("btn_image/closeBtnPushImage.png");
		ImageIcon closePushImage = new ImageIcon(closePush);
		closeButton.setBounds(500, 550, 200, 50);
		closeButton.setBackground(new Color(255, 0, 0, 0));
		closeButton.setContentAreaFilled(false);
		closeButton.setBorderPainted(false);
		closeButton.setIcon(closeImage);
		closeButton.setPressedIcon(closePushImage);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(closeButton);
		
	}
	
	public void showGUI() {
		setVisible(true);
	}
}
