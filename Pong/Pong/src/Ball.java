import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle{
	
	Random random;
	int xVel; // velocity in X direction
	int yVel; // velocity in Y direction
	int initSpeed = 3; //initial velocity of Ball
	
	Ball(int ballX, int ballY, int ballWidth, int ballHeight){
		super(ballX, ballY, ballWidth, ballHeight);
		
		random = new Random();
		
		// random movement of ball along X-axis
		int randomDirectionX = random.nextInt(2);
		if(randomDirectionX == 0) randomDirectionX--;
		setX(randomDirectionX * initSpeed);
		
		// random movement of ball along Y-axis
		int randomDirectionY = random.nextInt(2);
		if(randomDirectionY == 0) randomDirectionY--;
		setY(randomDirectionY * initSpeed);
		
	}
	
	public void setX(int randomDirectionX) {
		xVel = randomDirectionX;
	}
	public void setY(int randomDirectionY) {
		yVel = randomDirectionY;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
	}
	
	public void move() {
		x += xVel;
		y += yVel;
	}		
	
}
