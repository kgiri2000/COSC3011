package panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		
		
		
		//Creating Labels
		JLabel label = new JLabel();
		ImageIcon image = new ImageIcon("lotus.png");
		label.setIcon(image);
		label.setText("Lotus");
		//label.setVerticalAlignment(JLabel.BOTTOM);
		//label.setHorizontalAlignment(JLabel.RIGHT);
		label.setBounds(0,0,200,200);  // if we are using null setLayout
		
		//JPanel =  a GUI component that functions as a container to hold other components
		
		//Creating redPanel
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);
		redPanel.setBounds(0,0,500,500);
		redPanel.setLayout(null);
		
		//Creating bluePanel
		JPanel bluePanel = new JPanel();
		bluePanel.setBackground(Color.blue);
		bluePanel.setBounds(500,0,500,500);
		bluePanel.setLayout(null);
		
		//Creating greenPane;
		
		JPanel greenPanel = new JPanel();
		greenPanel.setBackground(Color.green);
		greenPanel.setBounds(0, 500, 1000, 500);
		greenPanel.setLayout(null);
		
		
		
		//JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(1000,1000);
		frame.setVisible(true);
		greenPanel.add(label);
		frame.add(redPanel);
		frame.add(bluePanel);
		frame.add(greenPanel);
		//Adding the labels to the panels
		
		
		
		
		

	}

}
