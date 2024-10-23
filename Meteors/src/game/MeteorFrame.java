package game;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MeteorFrame extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MeteorFrame frame = new MeteorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MeteorFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/bubble-tea.png"));
		this.setTitle("Meteor Shower");
		this.setLocation(200,0);
		this.setResizable(false);
		setContentPane(new MeteorPanel());
		this.pack();
	}

}
