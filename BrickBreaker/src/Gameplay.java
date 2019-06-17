import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;

	private int totalBricks = 21;

	private Timer timer;
	private int delay = 8;

	private int playerX = 310;
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXdir = -1;
	private int ballYdir = -3;
	
	private MapGenerator map;

	public Gameplay() {
		
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

	}

	public void paint(Graphics g) {
		// background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		// draw map
		map.draw((Graphics2D) g);
		
		// borders
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		

		// the paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);

		// ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		// after winning game, reinitialize
		if(totalBricks <= 0){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("You won", 190,300);
			
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Press enter to restart", 230,350);
		}
		
		// implementation of game over
		if(ballPosY > 570){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Game over, Scores:", 190,300);
			
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Press enter to restart", 230,350);
		}
		

		g.dispose();

	}

	public void actionPerformed(ActionEvent event) {
		timer.start();

		if (play) {
			
			// create intersection between ball and paddle by creating rectangles
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100,8))){
				ballYdir = -ballYdir;
			}
			
			//intersection of bricks and ball
			A: for(int i=0; i<map.map.length;i++){
				for(int j=0; j<map.map[0].length; j++){
					if(map.map[i][j] > 0){
						int brickX  = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect =  new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)){
							map.setBrickValue(0, i, j); 
							totalBricks--;
							score += 5;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width){
								ballXdir = -ballXdir;
							}
							else{
								ballYdir = -ballYdir;
							}
							
							break A;
							
						}
					}
				}
			}
			
			// ball moving
			ballPosX += ballXdir;
			ballPosY += ballYdir;


			// modelling ball rebounds
			if (ballPosX < 0) {
				ballXdir = - ballXdir;
			}
			if (ballPosX > 670){
				ballXdir = -ballXdir;
			}
			if (ballPosY < 0){
				ballYdir = -ballYdir;
			}
		}

		repaint();
	}

	// keys pressed on the keyboard
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		if(event.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

}
