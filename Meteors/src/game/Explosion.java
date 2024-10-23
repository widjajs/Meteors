package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explosion 
{
	public BufferedImage image;
	
	Explosion(int numImage)
	{
		try 
		{
			image = ImageIO.read(new File("src/images/Explosion" + numImage + ".png"));
		} 
		catch (IOException e) {e.printStackTrace();}
	}

	public void draw(Graphics2D g2d, int x) 
	{
		g2d.drawImage(image, x, 600, null);
	}

}
