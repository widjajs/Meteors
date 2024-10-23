package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Score 
{
	public int score = 0, lives = 6;
	private BufferedImage heart;
	
	Score()
	{
		try 
		{
			heart = ImageIO.read(new File("src/images/heart.png"));
		} 
		catch (IOException e) {e.printStackTrace();}
	}

	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Consolas", Font.PLAIN, 60));
		g2d.drawString(String.valueOf(score/10000) + 
				String.valueOf(score/1000) +
				String.valueOf(score/100) +
				String.valueOf(score/10) +
				String.valueOf(score % 10), 10, 50);
		for(int i = 0; i < lives; i++)
		{
			g2d.drawImage(heart, (625 - (i * heart.getWidth())), 5, null);
		}
	}
}
