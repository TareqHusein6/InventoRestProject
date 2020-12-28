package InvetoRestProject.classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import InvetoRestProject.Login;

//logout button used in several places
public class LogoutButton extends MyButton implements ActionListener{
	JFrame f;
	public LogoutButton(String string,JFrame f) {
		super(string);
		// TODO Auto-generated constructor stub
		this.setFont(new Font("Tahoma", Font.BOLD, 35));
		this.addActionListener(this);
		this.f=f;
		this.setBackground(Color.decode("#98d4ee"));
		this.setFont(new Font("Tahoma", Font.BOLD, 28));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Login.main(null);//enter the worker page
		f.dispose();
		
	}
	 @Override
		public void mouseEntered(MouseEvent arg0) {
		 this.setBackground(Color.decode("#f9f9f9"));
		 this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			this.setBackground(Color.decode("#98d4ee"));
		}
}
