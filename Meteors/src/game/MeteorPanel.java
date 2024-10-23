package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MeteorPanel extends JPanel implements ActionListener
{
	private int WIDTH = 700, HEIGHT = 700, numAsteroids = 7;
	private int numBooms = 9, boomsCount = 0, BGy = 0;
	private Timer timer;
	private Timer animation;
	private int delay = 16;
	private BufferedImage BG;
	private Ship boss;
	private Meteors[] asteroids;
	private Explosion[] boom;
	private Score score;
	private Sound laserSound, explode, alarm;
	private ArrayList<Laser> lasers;
	private boolean alive = true, paused = false;
	
	public MeteorPanel() 
	{
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		addKeyListener(new KL());
		setUp();
		
		timer = new Timer(delay, this);
		timer.start();
		
		animation = new Timer(250, null);
		animation.addActionListener(new TimeListener());
		
		
	}
	
	private void setUp()
	{
		try{BG =  ImageIO.read(new File("src/images/starfield.png"));}
		catch (IOException e){e.printStackTrace();}
		
		boss = new Ship(WIDTH, HEIGHT);
		asteroids = new Meteors[numAsteroids];
		boom = new Explosion[numBooms];
		lasers = new ArrayList<Laser>();
		score = new Score();
		explode = new Sound("src/images/rumble1.wav");
		laserSound = new Sound("src/images/Laser_Shoot.wav");
		alarm = new Sound("src/images/alarm.wav");
		
		for(int i = 0; i < numBooms; i++)
			boom[i] = new Explosion(i);
		for(int i = 0; i < numAsteroids; i++)
			asteroids[i] = new Meteors(i, WIDTH, HEIGHT);
	}
	
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)(g);
		if(BGy >= HEIGHT)
			BGy = 0;
		else
			BGy += 2;
		draw(g2d);
		if(alive)
			move();
		else
		{
			boss.y = 5000;
			boom[boomsCount].draw(g2d, boss.x);
			boomsCount++;
			animation.start();
		}
	}
	
	public void checkCollisions()
	{
		//ASTEROID
		for(int i = 0; i < numAsteroids; i++)
		{
			if(asteroids[i].x < -(asteroids[i].asteroid.getWidth()) ||
					asteroids[i].x > WIDTH ||
					asteroids[i].y > HEIGHT)
				asteroids[i] = new Meteors(i, WIDTH, HEIGHT);		
		}
		
		//ASTEROID AND LASERS
		for(int i = 0; i < numAsteroids; i++)
		{
			Rectangle r1 = new Rectangle(asteroids[i].x, asteroids[i].y, asteroids[i].asteroid.getWidth(), asteroids[i].asteroid.getHeight());
			for(int j = 0; j < lasers.size(); j++)
			{
				Rectangle r2 = new Rectangle(lasers.get(j).x, lasers.get(j).y, lasers.get(j).laser.getWidth(), lasers.get(j).laser.getHeight());
				if(r1.intersects(r2))
				{
					explode.play(-10);
					lasers.remove(j);
					asteroids[i] = new Meteors(i, WIDTH, HEIGHT);
					if(i == 0)
						score.score += 5;
					else if(i <= 3)
						score.score += 3;
					else
						score.score++;
				}
			}
		}
		
		//ASTEROID AND SHIP
		for(int i = 0; i < numAsteroids; i++)
		{
			Rectangle r1 = new Rectangle(asteroids[i].x, asteroids[i].y, asteroids[i].asteroid.getWidth(), asteroids[i].asteroid.getHeight());
			Rectangle r2 = new Rectangle(boss.x, boss.y, boss.ship.getWidth(), boss.ship.getHeight());
			
			if(r1.intersects(r2))
			{
				alarm.play(5);
				asteroids[i] = new Meteors(i, WIDTH, HEIGHT);
				score.lives--;
				if(score.lives <= 0)
					gameOver();
			}
		}

	}
	
	private void gameOver() 
	{
		explode.play(-10);
		alive = false;
		repaint();
		restart();
	}

	public void restart()
	{
		int restart = JOptionPane.showConfirmDialog(null, "Play Again?", "Game Over!", JOptionPane.YES_NO_OPTION);
		if(restart == 0)
		{
			boomsCount = 0;
			alive = true;
			score = new Score();
			boss = new Ship(WIDTH, HEIGHT);
			timer.start();
		}
		else
			System.exit(0);
	}
	
	public void draw(Graphics2D g2d)
	{
		
		g2d.drawImage(BG, 0, BGy, null);
		g2d.drawImage(BG, 0, BGy - HEIGHT, null);
		boss.draw(g2d);
		score.draw(g2d);
		for(int i = 0; i < numAsteroids; i++)
			asteroids[i].draw(g2d);	
		for(int i = 0; i < lasers.size(); i++)
			lasers.get(i).draw(g2d);
	}
	
	public void move() 
	{
		boss.move();
		for(int i = 0; i < numAsteroids; i++)
			asteroids[i].move();
		for(int i = 0; i < lasers.size(); i++)
			lasers.get(i).move();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		move();
		checkCollisions();
		repaint();
	}
	
	public class KL extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
				boss.keyPressed(e);
			if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				laserSound.play(-10);
				int xCoord = boss.x + boss.ship.getWidth()/2;
				int yCoord = boss.y;
				lasers.add(new Laser(xCoord, yCoord));
			}	

		}
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
				boss.keyReleased(e);
		}
	}
	
	public class TimeListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e) 
		{
			if(boomsCount < numBooms)
				repaint();
			else
				animation.stop();
		}
	}


}
