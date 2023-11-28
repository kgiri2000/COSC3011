package label;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Main {
	public static void main(String[] args) {
		//JLabel = a GUI display area for a string of text, an image or both
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(500, 500);
		frame.setVisible(true);
		//frame.setLayout(null);
		
		
		
		
		//Label
		JLabel label = new JLabel(); //create the instance of Jlabel
		frame.add(label);
		
		//Adding to label
		label.setText("MY NAME IS KAMAL GIRI");
		 
		//Adding picture to the label 
		
		ImageIcon image = new ImageIcon("cosmos.jpeg");
		Border border = BorderFactory.createLineBorder(Color.green,3);
		label.setIcon(image);
		
		//set Text LEFT, CENTER , RIGHT of the Image icon
		label.setHorizontalTextPosition(JLabel.CENTER); 
		
		label.setVerticalTextPosition(JLabel.TOP); //set Text Top, Center, Bottom of image icon
		
		//text color
		label.setForeground(new Color(0x00FFFF)); //set font color
		label.setFont(new Font("MV Boli", Font.PLAIN, 20)); //set font
		label.setIconTextGap(20); // set gap of the text and image
		label.setBackground(Color.black); //set background color
		//the entire jframe is filled with color
		label.setOpaque(true); // display background color is needed with the above code
		label.setBorder(border);
		
		//Icon Alignment
		label.setVerticalAlignment(JLabel.TOP); // set vertical position of icon+text within label
		label.setHorizontalAlignment(JLabel.LEFT); //set Horizontal Position of the icon+text within the label
		
		//managing the label
		//label.setBounds(100,100,350,350); // it will set x,y position within frame as well as dimension
		
		//This will made the frame to accommodate all the label and features inside the frame.
		frame.pack();
		
		
	}
	

}
