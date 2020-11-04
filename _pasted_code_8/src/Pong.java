// filler code for pong provided by Mr. David
// by Jimmy Liu, recieved help from Mr. David
// extra feature a bit confusing, is a beatable AI system which is set to 0.6 currently, chances are random so have to keep playing to see that AI occasionally miss a ball or two.
// or just change difficulty to 0.5, but that would probably glitch the paddle a lot. (This is not the best AI, somehow the paddle glitches and flash when going down and normal when up).
// also have to note that I added a simple color flash that activate once single player mode is activated, and does not shut down after. sorry if it hurts eye I only had 10 minutes to
// do it. I could have created another random to make it flash slower but yeah.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pong extends JPanel implements KeyListener {
	
	// constants that are predefined and won't change as the program runs
	private final int WIDTH = 600, HEIGHT = 600, WINDOW_HEIGHT = 650;
	private final int PADDLE_WIDTH = 20, DIAM = 8, PADDLE_HEIGHT = 100;
	private final int PADDLE_SPEED = 4;

	
	// your instance variables here, I've given you a few 
	private boolean up1, down1, up2, down2; 		// booleans to keep track of paddle movement
	private boolean solo = false;
	// solo variable that toggles on solo mode and direction variables that toggle on paddle movement.
	
	// here we have ball position x and y and paddle positions rect1xpos/rect1ypos and also the location where the paddle start after every goal or reset.
	private int x = WIDTH/2;
	private int y = HEIGHT/2, speedX = 4, speedY = 2;
	private int rect1ypos = 200;
	private int rect2ypos = 200;
	private int rect1xpos = 0;
	private int rect2xpos = WIDTH-20;
	private int paddle_startpoint = 200;
	
	// here we define the uplimit and the downlimit so the ball does not go out of bounds.
	private int uplimit = 0;
	private int downlimit= HEIGHT-150;
	
	// here we have the score system
	private int p1score = 0;
	private int p2score = 0;
	
	// this is used to disable ballmove for after every goal or when reset happens.
	private boolean ballmove = false;
	
	//rect1height and rect2height are the height dimention of the paddles, and the random and difficulty is used to make beatable AI
	private int rect1height = 200;
	private int rect2height = 200;
	private double random = Math.random();
	private double difficulty = 0.6;

	// simple color flash, did not have time to perfect it, sorry if it hurts eye.
	private int firstcolor = 1;
	private int secondcolor = 1;
	private int thirdcolor = 1;
	
	

	
	
	
	// this method moves the ball at each timestep
	public void move_ball() {

		// your code here //
		// if ballmove is true then move the ball.
		if (ballmove)
			x += speedX;
		if (ballmove)
			y += speedY;
	}
	
	// this method moves the paddles at each timestep
	public void move_paddles() {
	
		// your code here // 
		//if up1 is true then move paddle up to uplimit. A bit confusing here as uplimit is defined as 0 so it's if rect1ypos > 0.
		if (up1) 
			if (rect1ypos > uplimit)
				rect1ypos -= PADDLE_SPEED;
		
		//if down1 is true then move paddle down to downlimit 
		if (down1)
			if (rect1ypos < downlimit)
				rect1ypos += PADDLE_SPEED;
		
		//if up2 is true then move second paddle up to uplimit, will not activate if solo
		if (up2 && solo == false) 
			if (rect2ypos > uplimit)
				rect2ypos -= PADDLE_SPEED;
		
		//if down2 is true then move second paddle down to downlimit, will not activate if solo
		if (down2 && solo == false)
			if (rect2ypos < downlimit)
				rect2ypos += PADDLE_SPEED;
		
		// solo ai decisions
		if (solo == true) {
			random = Math.random();

			// get a random double between 0 and 1 to adjust difficulty, if greater than the double then make error
			if (random > difficulty) {
				if ( y > rect2ypos) {
					rect2ypos -= PADDLE_SPEED;
			}
				else if (y < rect2ypos) {
					rect2ypos -= PADDLE_SPEED;
			}
			}
			// if smaller than double then do not make error(not a perfect percentage AI system but works pretty well)
			else if (random < difficulty) {
				if ( y > rect2ypos) {
					rect2ypos += PADDLE_SPEED;
			}
				else if (y < rect2ypos) {
					rect2ypos -= PADDLE_SPEED;
			}	
			}
			}
			
				
				
			}
		
	
	// this method checks if there are any bounces to take care of,
	// and if the ball has reached a left/right wall it adds to 
	// the corresponding player's score
	public void check_collisions() {
		
		// your code here			
	// this ensures that ball only bounces off the two paddles and not the walls
	if (x >= rect2xpos && y >= rect2ypos && y <= rect2ypos + rect2height)
		
		speedX = -speedX;
	
	// this ensures that ball only bounces off the two paddles and not the walls
	if (x < PADDLE_WIDTH && y >= rect1ypos && y <= rect1ypos + rect1height)
		
		speedX = -speedX;
	
	// score system, if ball hit right wall give point to left player, then reset location
	if (x >= WIDTH - DIAM) {
		p1score ++;
		rect1ypos = paddle_startpoint;
		rect2ypos = paddle_startpoint;
		
		ballmove = false;
		
		x = WIDTH/2;
		y = HEIGHT/2;
	}

	// score system, if ball hit left wall give point to right player, then reset location
	if (x < rect1xpos) {
		p2score ++;
		rect1ypos = paddle_startpoint;
		rect2ypos = paddle_startpoint;
		
		ballmove = false;
		
		x = WIDTH/2;
		y = HEIGHT/2;
	}
	
	// original ball statement for bouncing off height wall.
	if (y >= HEIGHT-DIAM || y <= 0) {
			speedY = -speedY;
	}
			
	}
	

	// defines what we want to happen anytime we draw the game
	// you simply need to fill in a few parameters here
	public void paint(Graphics g) {
		
		// background color is gray
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// draw your rectangles and circles here
		// the color, paddles, and the ball drawn here.
		
		// color that flash at you, maybe a bit extreme, please do not blame me for this I have this idea and i have like 10 minutes to do it so I did a simple color flash.
		if (random > 0.3 && random < 0.5) {
			firstcolor = 176;
			secondcolor = 224;
			thirdcolor= 230;
		
		}
		if (random > 0.5 && random < 0.7) {
			firstcolor = 124;
			secondcolor = 252;
			thirdcolor = 0;
		}
		if (random > 0.7) {
			firstcolor = 255;
			secondcolor = 0;
			thirdcolor = 0;
		}
		g.setColor(new Color(firstcolor,secondcolor,thirdcolor));
		//g.setColor(new Color(70,100,200));
		g.fillRect(rect1xpos,rect1ypos,PADDLE_WIDTH,rect1height);
		g.fillRect(rect2xpos,rect2ypos,PADDLE_WIDTH,rect2height);
		g.fillOval(x, y, DIAM, DIAM);
		// .......
		
		// writes the score of the game - you just need to fill the scores in
		Font f = new Font("Arial", Font.BOLD, 14);
		g.setFont(f);
		g.setColor(Color.red);
		// score system are printed out here
		g.drawString("P1 Score: " + p1score, WIDTH/5, 20);
		g.drawString("P2 Score: " + p2score, WIDTH*3/5, 20);
	}

	// defines what we want to happen if a keyboard button has 
	// been pressed - you need to complete this
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// changes paddle direction based on what button is pressed
		// if down key then activate down1 and enable ballmove
		if (keyCode == KeyEvent.VK_DOWN) { 
			// fill this in
			down1 = true;
			ballmove = true;
		
		}
		
		// if up key then activate up1 and enable ballmove
		if (keyCode == KeyEvent.VK_UP) {
			// fill this in
			up1 = true;
			ballmove = true;
		
		}
		
		// if w key then enable up2 and ballmove
		if (e.getKeyChar() == 'w') {
			// move paddle down 
			up2 = true;
			ballmove = true;
		
		}

		// if s key then activate down2 and enable ballmove
		if (e.getKeyChar() =='s') {
			// fill this in
			down2 = true;
			ballmove = true;
			
		}
			
		// turn 1-player mode on
		if (e.getKeyChar() == '1') {
		// fill this in
			// put solo mode to true
			solo = true;
			
			
		}
		
		
		
		// turn 2-player mode on
		if (e.getKeyChar() == '2') {
			// fill this in
			// put solo mode to false 
			solo = false;
			
		}
		
			
	}

	// defines what we want to happen if a keyboard button
	// has been released - you need to complete this
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// stops paddle motion based on which button is released
		if (keyCode == KeyEvent.VK_UP) 
			// shut down up1 
			up1 = false;
			

		if (keyCode == KeyEvent.VK_DOWN) 
			// fill this in
			//shut down down1
			down1 = false;
  
		if (e.getKeyChar() == 'w')
			// fill this in
			// shut down up2
			up2 = false;
			
			
		
		if (e.getKeyChar() == 's')
			// fill this in
			// shut down down2
			down2 = false;
			
			
	}
	
	// restarts the game, including scores
	public void restart() {

		// your code here
		
		// put every score to 0 and move everything back to the starting position
		rect1ypos = paddle_startpoint;
		rect2ypos = paddle_startpoint;
		
		ballmove = false;
		
		x = WIDTH/2;
		y = HEIGHT/2;
		p1score = 0;
		p2score = 0;
		
		
		
	}

	//////////////////////////////////////
	//////////////////////////////////////
	
	// DON'T TOUCH THE BELOW CODE
	
	
	// this method runs the actual game.
	public void run() {

		// while(true) should rarely be used to avoid infinite loops, but here it is ok because
		// closing the graphics window will end the program
		while (true) {
	
			// draws the game
			repaint();
			
			// we move the ball, paddle, and check for collisions
			// every hundredth of a second
			move_ball();
			move_paddles();
			check_collisions();
			
			//rests for a hundredth of a second
			try {
				Thread.sleep(10);
			} catch (Exception ex) {}
		}
	}
	
	// very simple main method to get the game going
	public static void main(String[] args) {
		new Pong();
	}
 
	// does complicated stuff to initialize the graphics and key listeners
	// DO NOT CHANGE THIS CODE UNLESS YOU ARE EXPERIENCED WITH JAVA
	// GRAPHICS!
	public Pong() {
		JFrame frame = new JFrame();
		JButton button = new JButton("restart");
		frame.setSize(WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.add(button, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart();
				Pong.this.requestFocus();
			}
		});
		this.addKeyListener(this);
		this.setFocusable(true);
		
		run();
	}
	
	// method does nothing
	public void keyTyped(KeyEvent e) {}
}