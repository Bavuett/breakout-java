//**********************************************************

import java.io.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;	//per utilizzare EventListner
import java.awt.image.*;  	//per utilizzare BufferedImage

import javax.swing.*; //per utilizzare JPanel

public class GamePanel extends JPanel implements Runnable {

	//definizione costanti
	static final int GAME_WIDTH = 461;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (1.25));
	static final Dimension SCREEN_SIZE1 = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	
	static final int PADDLE_WIDTH = 44;
	static final int PADDLE_HEIGHT = 10;  //orizzontale
	
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 15;  //palla rotonda

	static final int rows = 8;
	static final int columns = 14;
	
	static final int brickWidth = 56;
	static final int brickHeight = 10;
	
	//remarks from Mr. Alcorn:
	//The problem you noticed about the paddle not going all the way to the top 
	//was left in because without it good players could monopolize the game. 
	//Our motto was "if you can't fix it call it a feature."
	static final int BORDER_OFFSET = 20 ; 
	// il paddle non tocca i bordi superiore ed inferiore se OFFSET >0
	static final int DISTANZA = 20; // =0 i paddle sono sul bordo del campo;
	
	Thread gameThread; //Thread eseguibile
	BufferedImage buffer; //awg.image 
	Graphics graphics;
	
	Paddle paddle1;
	Ball ball;
	Brick[][] brick;
	// istanza "paddle1" dalla classe Paddle
	
	GamePanel(){ //costruttore
		Random random = new Random();
		//creo una istanza "ball" dalla classe Ball al centro dello schermo ma ad una altezza casuale
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
		brick = new Brick[rows][columns];
		
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE1);
		gameThread = new Thread(this);
		gameThread.start();
		
		newPaddles();
		newBricks();
		
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE1);
		
		//----- aggiungi al Panel un "listner", un ascoltatore di eventi da tastiera -----
		//
		this.addKeyListener(new AL());
		//
		//--------------------------------------------------------------------------------
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void newPaddles() {
		//creo una istanza "paddle1" dalla classe Paddle
		paddle1 = new Paddle((GAME_WIDTH - PADDLE_WIDTH) / 2, GAME_HEIGHT - (PADDLE_HEIGHT - DISTANZA / 2) - 50, PADDLE_WIDTH, PADDLE_HEIGHT);

	}

	public void newBricks() {
		for (int p = 0; p < rows; p++) {
			for (int l = 0; l < columns; l++) {
				brick[p][l] = new Brick(p, l, brickWidth, brickHeight);
			}
		}
	}
	
//------------------------------- non toccare -------------------------------
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		buffer = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = buffer.getGraphics();
		
		draw(graphics);
		
		g.drawImage(buffer,0,0,this);
		
	}
//----------------------------------------------------------------------------


	public void draw(Graphics g) {
		
		paddle1.draw(g);
		ball.draw(g); 

		for (int p = 0; p < rows; p++) {
			for (int l = 0; l < columns; l++) {
				if (brick[p][l] != null) {
					brick[p][l].draw(g);
				}
			}
		}
		
		// disegna altri oggetti qui
		
        // the following line helps with animation ---------------------------
        Toolkit.getDefaultToolkit().sync(); 
        // This method ensures that the display is up-to-date. 
        // It is useful for animation: timing the painting operation 
        // should be performed by calling Toolkit.sync() 
        // after each paint to ensure the drawing commands 
        // are flushed to the graphics card. ---------------------------------  
	}

	public void move() {
		
		paddle1.move();
		ball.move();
		
		// muovi altri oggetti qui
		
	}

	public void checkCollision() {
		
	
		//---- stops paddles at window edges ----------------
		if(paddle1.x <= 0)
			paddle1.x = 0;

		if(paddle1.x >= GAME_WIDTH-PADDLE_WIDTH) 
			paddle1.x = GAME_WIDTH-PADDLE_WIDTH; 
		
		
		if(ball.y <= 0) {
			ball.dy= -ball.dy;
		}

		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.dy= -ball.dy;
		
		}
		//----- la palla rimbalza quando tocca i margini destro e sinistro della finestra ------
		if(ball.x <=0) {
			ball.dx= -ball.dx;	
		}

		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			ball.dx= -ball.dx;	
		}
		
		
		
		if (ball.intersects(paddle1)) {
			ball.dy = -ball.dy;
		}
		//---------------------------------------------------

	
	} // end checkCollision()

	public void run() { //game loop

		long lastTime = System.nanoTime();
		double amountOfFPS =60.0; // frames in 1 second
		double duration = 1000000000/amountOfFPS; //interval (time in ns) beetween 2 frames
		double delta = 0;

		while(true) { //per sempre
			long now = System.nanoTime();
			delta += (now -lastTime)/duration; // tempo trascorso è > intervallo? se sì, incrementa delta
			lastTime = now;

			if(delta >=1) {
			
				move();  //calls move() method for paddle1...
				checkCollision(); //checks collisions of paddles and boundary
			
				repaint(); //is used to tell a component (gamepanel) to repaint itself.
				delta--;
			} //end if
		} //end while
		
	} //end run()
	
	
	public class AL extends KeyAdapter{  
		//l’Adapter è un Listner che implementa tutte le funzioni {}
		//
		//KeyAdapter implementa i 3 metodi:
		//KeyPressed, KeyTyped, KeyReleased 
		//di KeyLisner senza che l'utente debba ridefinirli tutti
		//l’utente implementa solo quelli che usa

		//questo metodo SPOSTA il paddle quando il tasto è premuto
		public void keyPressed(KeyEvent e) {
			
			//paddle1.keyPressed(e);
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				paddle1.setDeltaX(-1);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				paddle1.setDeltaX(+1);
			}
			
		}
		
		//questo metodo FERMA il paddle rilasciando il tasto, azzerando il DeltaX
		public void keyReleased(KeyEvent e) {
			
			//paddle1.keyReleased(e);
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				paddle1.setDeltaX(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				paddle1.setDeltaX(0);
			}
			
		
		}
	
	} //end public class AL
	
} //end GamePanel