package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound 
{
	Clip clip;
	AudioInputStream AIS;
	
	Sound(String file)
	{
		try 
		{
			clip = AudioSystem.getClip();
			AIS = AudioSystem.getAudioInputStream(new File(file));
			clip.open(AIS);
		} 
		catch (LineUnavailableException e) {e.printStackTrace();}
		catch (UnsupportedAudioFileException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void play(int volumeControl)
	{
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(volumeControl);
		clip.loop(1);
	}

}
