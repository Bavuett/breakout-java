//**********************************************************

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
	
	GameFrame() { //costruttore	
		
		panel = new GamePanel();
		
		//this.add(panel);  //finestra AWT
		
		this.getContentPane().add(panel); //finestra Swing
		
		this.setTitle("Bricks Crusher: Break the Bricks");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	
	} //end costruttore

} 


//**********************************************************