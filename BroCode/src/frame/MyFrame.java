package frame;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
	
	MyFrame(){
		
		JFrame frame = new JFrame(); //create the this
		this.setVisible(true);  //make this visible
		this.setSize(420,420); // sets x-dimension and y-dimension of the this
		this.setTitle("KAMAL GIRI"); // set the title for the this
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of the application
		this.setResizable(false);  //prevent this from being resized
		
		
		//Changing the logo
		
		ImageIcon image = new ImageIcon("lotus1.png");
		this.setIconImage(image.getImage());
		
		//background color of the this
		//little bit of methods chaining
		
		this.getContentPane().setBackground(new Color(123,50,250)); 
	}
	
	

}
