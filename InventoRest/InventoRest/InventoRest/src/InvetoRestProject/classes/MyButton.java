package InvetoRestProject.classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

//Designed button class
public class MyButton extends JButton implements MouseListener{
	
	
	 public MyButton(String string) {
		// TODO Auto-generated constructor stub
		 super(string);
		 this.addMouseListener(this);
		 this.setBackground(new Color(220, 220, 220));
		 this.setFont(new Font("Tahoma", Font.BOLD, 35));
		 
	}
	@Override protected void paintComponent(Graphics g) {
		    if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
		      Graphics2D g2 = (Graphics2D) g.create();
		      g2.setPaint(getBackground());
		      g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
		          0, 0, getWidth() - 1, getHeight() - 1));
		      g2.dispose();
		    }
		    super.paintComponent(g);
		  }
		  @Override public void updateUI() {
		    super.updateUI();
		    setOpaque(false);
		    setBorder(new RoundedCornerBorder());
		  }
		  @Override
			public void mouseEntered(MouseEvent arg0) {
				this.setBackground(Color.decode("#98d4ee"));
				this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				this.setBackground(new Color(220, 220, 220));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				

			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

}
