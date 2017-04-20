package com.shikhar.mario.core;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

// This is the main entry point..

public class GameFrame extends JFrame implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GamePanel panel;
	public GameFrame(String mapDir) {	
		
		int w = 512;//566;//420;
		int h = 256;//320;//330;               
		setSize(w, h+32);
		//setResizable(false);
		setTitle("Game Frame"); 
		 panel = new GamePanel(w, h,mapDir);
		add(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		this.addWindowListener(this);
		setVisible(true);	
	}
	
	public static void main(String[] args) {
		new GameFrame(null);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
	
		this.panel.stopGame();
		//this.panel.
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
