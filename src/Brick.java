//**********************************************************

import java.awt.*;
import java.awt.event.*;

public class Brick extends Rectangle {


	int id;
	int dy;
	int dx;
	int paddleSpeed = 6;

	Brick (int row, int column, int brickWidth, int brickHeight){
		super(2 + ((row * brickWidth) + 1 * (row + 1)), (column * brickHeight) + 1 * (column + 1), brickWidth, brickHeight); //costruttore di Rectangle		
	}

	public void setDeltaY(int yDirection) { 
		dy = yDirection*paddleSpeed;
	}
	
	public void setDeltaX(int xDirection) { 
		dx = xDirection*paddleSpeed;
	}

	public void move() {
		y = y + dy;
		x = x + dx;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white); //colore paddle1
		g.fillRect(x, y, width, height);
	}
	
}