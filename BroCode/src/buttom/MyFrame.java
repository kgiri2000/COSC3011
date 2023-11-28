package buttom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyFrame extends JFrame implements ActionListener {
	
	
	JButton button;
	JLabel label;

	MyFrame(){
		
		//Creating the buttom
		ImageIcon icon = new ImageIcon("point.png");
		ImageIcon icon2 =  new ImageIcon("lotus1.png");
		
		label = new JLabel();
		label.setIcon(icon2);
		label.setBounds(0,400,1500,800);
		label.setVisible(false);
		
		
		button = new JButton();
		button.setBounds(0,0,800,400);
		button.addActionListener(this);
		button.setText("LOTUS");
		button.setFocusable(false);
		
		button.setIcon(icon);
		
		button.setHorizontalTextPosition(JButton.LEFT);
		button.setVerticalTextPosition(JButton.CENTER);
		button.setFont(new Font("Comic Sans", Font.BOLD, 25));
		button.setIconTextGap(10);
		button.setForeground(Color.cyan);
		button.setBackground(Color.lightGray);
		button.setBorder(BorderFactory.createEtchedBorder());
		
	
		
		
		
		
		//lambda expression, more advanced concept
		//button.addActionListiner(e -> System.out.println("LOTUS"));
		
		
		//Creating my frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500,500);
		this.setVisible(true);
		this.add(button);
		this.add(label);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== button) {
			//System.out.println("Lotus");
			label.setVisible(true);
		}
		
	}

}
