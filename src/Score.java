import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Score extends Rectangle {
	
	Score(int x, int y, int width, int height) {
        super(x, y, width, height);
    }


    public void draw(Graphics g, Font atari, int GAME_WIDTH, int GAME_HEIGHT, int score) {
        int currentScore = score;
        String messageToDisplay = Integer.toString(currentScore);

        g.setFont(atari);
        g.setColor(Color.white);
        g.drawString("SCORE " + messageToDisplay, 15, (GAME_HEIGHT) - 15);
    }
	
}
