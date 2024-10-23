package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship 
{
	public BufferedImage ship;
	public int x, y, width, height, speed = 5, xVelocity = 0;
	
	Ship(int width, int height)
	{
		try
		{
			ship = ImageIO.read(new File("src/images/hero_ship.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		this.width = width;
		this.height = height;
		x = width/2 - ship.getWidth()/2;
		y = height - 10 - ship.getHeight();
	}
	
	public void draw(Graphics2D g2d)
	{
		g2d.drawImage(ship, x, y, null);
	}
	
	public void move() 
	{
		x += xVelocity;
		if(x < 0)
			x = 0;
		if(x > width - ship.getWidth())
			x = width - ship.getWidth();
	}
	
	public void setXDirection(int xDirection)
	{
		xVelocity = xDirection;
	}
	
	public void keyPressed(KeyEvent e)
	{
		/*
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			setXDirection(-speed);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			setXDirection(speed);
			*/
		
		if(e.getKeyCode() == KeyEvent.VK_A)
			setXDirection(-speed);
		if(e.getKeyCode() == KeyEvent.VK_D)
			setXDirection(speed);
			
	}
	public void keyReleased(KeyEvent e)
	{
		/*
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			setXDirection(0);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			setXDirection(0);
			*/
		
		if(e.getKeyCode() == KeyEvent.VK_A)
			setXDirection(0);
		if(e.getKeyCode() == KeyEvent.VK_D)
			setXDirection(0);
			
	}
	
}
