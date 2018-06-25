package minecraftLauncher.launcher;

import java.awt.Color;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame
{
	private static LauncherFrame instance;
	private static LauncherPanel launcherPanel;
	
	public LauncherFrame()
	{
		this.setTitle("Minecraft Launcher 1");
		
		// 1/4 ratio (1920 x 1080)
		this.setSize(480, 270);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,0));
		
		this.setIconImage(Swinger.getResource("Logo v1.0_partial.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());
		AWTUtilities.setWindowOpacity(this, 0.0f);
		
		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		
		this.setVisible(true);
		
		Animator.fadeInFrame(this, 2);
	}
	
	public static void main(String[] args) 
	{
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/minecraftLauncher/resources/");
		Launcher.ML_CRASH_DIR.mkdir();
		
		instance = new LauncherFrame();
	}
	
	public static LauncherFrame getInstance()
	{
		return instance;
	}

	public LauncherPanel getLauncherPanel() 
	{
		return launcherPanel;
	}

}
