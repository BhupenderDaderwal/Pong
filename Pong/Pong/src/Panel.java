import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Panel extends JPanel implements Runnable{
	
	static final int GAME_WIDTH = 1200;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread gt; // gt = Game Thread
	Image image;
	Graphics graphics;
	Random random;
	Paddle p1;
	Paddle p2;
	Ball ball;
	Score score;
	boolean running = true;
	
	
	Panel(){
		newPaddle();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new ActionListener());
		this.setPreferredSize(SCREEN_SIZE);
		
		gt = new Thread(this);
		gt.start();
		
	}
	
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddle() {
		p1 = new Paddle(0,(GAME_HEIGHT/2) - (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		p2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH,(GAME_HEIGHT/2) - (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
				
	}
	
	public void paint(Graphics g){
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0,0, this);
		
	}
	
	public void draw(Graphics g) {
		p1.draw(g);
		p2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	
	public void move() {
		p1.move();
		p2.move();
		ball.move();
	}
	
	public void checkCollision() {
		
		//stops paddles from passing the panel edges
		if(p1.y <= 0) p1.y = 0; //stops left paddle if reaches top edge of the panel
		if(p1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) p1.y = GAME_HEIGHT - PADDLE_HEIGHT; //stops left paddle if touches bottom edge of the panel
		if(p2.y <= 0) p2.y = 0; //stops right paddle if reaches top edge of the panel
		if(p2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) p2.y = GAME_HEIGHT - PADDLE_HEIGHT; //stops right paddle if touches bottom edge of the panel
		
		//new round
		// check if player 1 misses the ball
		if(ball.x <= 0) {
			score.player2++;
			newPaddle();
			newBall();
			System.out.println("Player 2 Scored a point!");
		}
		// check if player 2 misses the ball
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddle();
			newBall();
			System.out.println("Player 1 Scored a point!");
		}
		
		//to bounce ball of top & bottom window edges
		if(ball.y <= 0) ball.setY(-ball.yVel);// ball bounces if touches top edge of the panel
		if(ball.y >= (GAME_HEIGHT-BALL_DIAMETER)) ball.setY(-ball.yVel);// ball bounces if touches bottom edge of the panel
		
		// bounce ball off the paddles and increase the difficulty level
		// paddle 1
		if(ball.intersects(p1)) {
			ball.xVel = Math.abs(ball.xVel);
			ball.xVel++; // fastens the ball
			if(ball.yVel<0) ball.yVel--;
			else ball.yVel++;
			ball.setX(ball.xVel);
			ball.setY(ball.yVel);
		}
		// paddle 2
		if(ball.intersects(p2)) {
			ball.xVel = Math.abs(ball.xVel);
			ball.xVel++; // fastens the ball
			if(ball.yVel<0) ball.yVel--;
			else ball.yVel++;
			ball.setX(-ball.xVel);
			ball.setY(ball.yVel);			
		}
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double nanoSeconds = 1000000000/amountOfTicks;
		double delta = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/nanoSeconds;
			lastTime = now;
			
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
				
				//System.out.println("BADAAM BADAAM, Kacha BADAAM");				
			}
			
		}
	}
	
	public class ActionListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			p1.keyPressed(e);
			p2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			
			p1.keyReleased(e);
			p2.keyReleased(e);
		}
	}
}