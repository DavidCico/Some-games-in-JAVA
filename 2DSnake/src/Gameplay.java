import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	private ImageIcon titleImage;
	
	private int[] snakeXlength = new int[750];
	private int[] snakeYlength = new int[750];
	private int moves = 0;
	
	// booleans for move direction
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	// images for snake representation
	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;
	private ImageIcon snakeImage;
	
	private int lengthofsnake = 3;
	
	// timer
	private Timer timer;
	private int delay = 100;
	
	
	// arrays (X,Y) for enemy position 
	private int[] enemyXpos={25,50,75,100,125,150,175,200,225,250,275,300,325,
			350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,
			750,775,800,825,850};
	
	private int[] enemyYpos={75,100,125,150,175,200,225,250,275,300,325,
			350,375,400,425,450,475,500,525,550,575,600,625};
	
	private ImageIcon enemyImage;
	
	// random index of enemy position
	private Random random = new Random();
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	
	private int scores =0;
	
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		
		
		//snake initialization, only when no moves have been done
		if(moves == 0){
			snakeXlength[2] = 50;
			snakeXlength[1] = 75;
			snakeXlength[0] = 100;
			
			snakeYlength[2] = 100;
			snakeYlength[1] = 100;
			snakeYlength[0] = 100;
		}
		
		//draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		
		//draw the title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25,11);
		
		//draw border for gameplay
		g.setColor(Color.white);
		g.drawRect(24, 74, 851, 577);
		
		//draw background gameplay
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		//draw scores
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.PLAIN, 14));
		g.drawString("Scores: " + scores, 780, 30);
		
		//draw length of snake
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.PLAIN, 14));
		g.drawString("Length: " + lengthofsnake, 780, 50);
		
		// rightmouth of snake defined for head (first element of snakeLength arrays)
		rightMouth = new ImageIcon("rightmouth.png");
		rightMouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
		
		for (int a=0; a<lengthofsnake; a++){
			if(a==0 && right){
				rightMouth = new ImageIcon("rightmouth.png");
				rightMouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			if(a==0 && left){
				leftMouth = new ImageIcon("leftmouth.png");
				leftMouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			if(a==0 && up){
				upMouth = new ImageIcon("upmouth.png");
				upMouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			if(a==0 && down){
				downMouth = new ImageIcon("downmouth.png");
				downMouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			
			if(a!=0){
				snakeImage = new ImageIcon("snakeimage.png");
				snakeImage.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
		}
		
		// check intersection of enemy with snake's head
		enemyImage = new ImageIcon("enemy.png");
		enemyImage.paintIcon(this, g, enemyXpos[xpos], enemyYpos[ypos]);
		
		if((enemyXpos[xpos] == snakeXlength[0]) && enemyYpos[ypos] == snakeYlength[0]){
			scores++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos =random.nextInt(23);
		}
		
		// check if head of snake touches any part of its body -> game over
		for(int b = 1; b < lengthofsnake; b++){
			if(snakeXlength[b] == snakeXlength[0] && snakeYlength[b] == snakeYlength[0]){
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.RED);
				g.setFont(new Font("Serif", Font.BOLD, 50));
				g.drawString("Game Over", 300, 300);
				
				g.setFont(new Font("Serif", Font.BOLD, 20));
				g.drawString("Space to RESTART", 350, 340);
			}
		}
		
		
		
		
		g.dispose();
		
	}

	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// when snake moves to the right
		if(right){
			for(int r = lengthofsnake-1; r >= 0; r--){
				snakeYlength[r+1] = snakeYlength[r]; // Y height stays the same
			}
			for(int r = lengthofsnake; r>=0; r--){
				if(r==0){
					snakeXlength[r] = snakeXlength[r] + 25; // head defines movement
				}
				else{
					snakeXlength[r] = snakeXlength[r-1]; // indentation of each element
				}
				
				// condition to cross the border
				if(snakeXlength[r] > 850){
					snakeXlength[r] = 25;
				}
			}
			
			repaint();
			
		}
		if(left){
			for(int r = lengthofsnake-1; r >= 0; r--){
				snakeYlength[r+1] = snakeYlength[r];
			}
			for(int r = lengthofsnake; r>=0; r--){
				if(r==0){
					snakeXlength[r] = snakeXlength[r] - 25;
				}
				else{
					snakeXlength[r] = snakeXlength[r-1]; 
				}
				
				if(snakeXlength[r] < 25){
					snakeXlength[r] = 850;
				}
			}
			
			repaint();
		}
		
		if(up){
			for(int r = lengthofsnake-1; r >= 0; r--){
				snakeXlength[r+1] = snakeXlength[r];
			}
			for(int r = lengthofsnake; r>=0; r--){
				if(r==0){
					snakeYlength[r] = snakeYlength[r] - 25;
				}
				else{
					snakeYlength[r] = snakeYlength[r-1]; 
				}
				
				if(snakeYlength[r] < 75){
					snakeYlength[r] = 625;
				}
			}
			
			repaint();
		}
		
		if(down){
			for(int r = lengthofsnake-1; r >= 0; r--){
				snakeXlength[r+1] = snakeXlength[r];
			}
			for(int r = lengthofsnake; r>=0; r--){
				if(r==0){
					snakeYlength[r] = snakeYlength[r] + 25;
				}
				else{
					snakeYlength[r] = snakeYlength[r-1]; 
				}
				
				if(snakeYlength[r] > 625){
					snakeYlength[r] = 75;
				}
			}
			
			repaint();
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			moves++;
			right = true;			
			if(!left){  // ensure that snake cannot reverse direction
				right = true;
			}
			else{
				right = false; 
				left = true;
			}
						
			up = false;
			down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			moves++;
			left = true;
			if(!right){
				left = true;
			}
			else{
				left = false;
				right = true;
			}
						
			up = false;
			down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			moves++;
			up = true;
			if(!down){
				up = true;
			}
			else{
				up = false;
				down = true;
			}
						
			left = false;
			right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			moves++;
			down = true;
			if(!up){
				down = true;
			}
			else{
				down = false;
				up = true;
			}
						
			left = false;
			right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			moves =0;
			scores =0;
			lengthofsnake=3;
			repaint();
		}
		
		
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
