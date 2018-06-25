package minecraftLauncher.launcher;

import javax.swing.JFrame;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

@SuppressWarnings("serial")
public class LauncherFrame extends JFrame
{
	private static LauncherFrame instance;
	private static LauncherPanel launcherPanel;
	
	public LauncherFrame()
	{
		this.setTitle("Minecraft Launcher 1");
		this.setSize(1365, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(Swinger.getResource("Logo v1.0_partial.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());
		
		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/minecraftLauncher/resources/");
		
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
