package flowlayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	public static void main(String[] args) {
		
		
		
		//Layout Manager : Defines the natural layout for components within a container
		
		//FlowLayout = places component in a row, sized at their preferred sixe.
		//IF the horizontal space in the container is too small. the FlowLayout class uses next available row.
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		//Deafault is BorderLayout
		//We can use FLowlayout to make the flow effect
		
		frame.setLayout(new FlowLayout(FlowLayout.LEADING, 10,10)); // horizontal spacig and vertical
		
		//We will create panel to fit all the buttons
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 200));
		panel.setBackground(Color.lightGray);
		panel.setLayout(new FlowLayout());
		

		//Creating buttons
		
		panel.add(new JButton("1"));
		panel.add(new JButton("2"));
		panel.add(new JButton("3"));
		panel.add(new JButton("4"));
		panel.add(new JButton("5"));
		panel.add(new JButton("6"));
		panel.add(new JButton("7"));
		panel.add(new JButton("8"));
		panel.add(new JButton("9"));
		
		frame.add(panel);
		frame.setVisible(true);
		
	}

}
