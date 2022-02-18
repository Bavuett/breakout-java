import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Lives extends Rectangle {
	
	Lives(int x, int y, int width, int height) {
        super(x, y, width, height);
    }


    public void draw(Graphics g, Font atari, int GAME_WIDTH, int GAME_HEIGHT, int lives) {
        int livesRemaining = lives;
        String messageToDisplay = Integer.toString(livesRemaining);
        g.setFont(atari);
        g.setColor(Color.white);
        g.drawString(messageToDisplay, (GAME_WIDTH) - 30, GAME_HEIGHT - 15);
    }
	
}
