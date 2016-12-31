package webIntegration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class loginForm {
	
	public static String getUsername(){
		  int reply = JOptionPane.showConfirmDialog(null, "Do you want to login, to save your score? You'll have to.", "GameID - Do you want to save your score?", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	          String uid = login();
	          return uid;
	        }
	        else {
	           System.exit(0);
	           return null;
	        }
	}
	
	public static String login(){
	    JFrame frame = new JFrame("GameID - Username");
	    frame.setSize(100, 400);
	    String usr = JOptionPane.showInputDialog(frame, "Please enter your username!");
		if ((usr != null) && (usr.length() > 0)) {
		  return usr;
		}
		return null;
	}
	
	
}
