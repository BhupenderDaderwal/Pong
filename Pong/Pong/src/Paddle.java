import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle{
	
	int id;
	int yVel; // velocity in Y direction
	int direction = 10; // direction of movement of paddle along Y-axis
	
	Paddle (int xPos, int yPos, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
		super(xPos, yPos, PADDLE_WIDTH, PADDLE_HEIGHT);
		this.id = id;		
	}
	

	public void keyPressed(KeyEvent e) {
		switch(id) {
		case 1:
			if(e.getKeyCode() == KeyEvent.VK_W){
				setY(-direction);
				move();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S) {
				setY(direction);
				move();
			}
			
			break;
			
		case 2:
			if(e.getKeyCode() == KeyEvent.VK_UP){
				setY(-direction);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				setY(direction);
				move();
			}
			
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(id) {
		case 1:
			if(e.getKeyCode() == KeyEvent.VK_W){
				setY(0);
				move();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S) {
				setY(0);
				move();
			}
			
			break;
			
		case 2:
			if(e.getKeyCode() == KeyEvent.VK_UP){
				setY(0);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				setY(0);
				move();
			}
			
			break;
		}
	}
	
	public void setY(int yDirection) {
		yVel = yDirection;
	}
	
	public void move() {
		y = y + yVel;
	}

	public void draw(Graphics g) {
		if(id == 1) g.setColor(Color.blue);
		else g.setColor(Color.red);
		
		g.fillRect(x, y, width, height);
	}
}
