package kube.bootstrap;

import fr.theshark34.openlauncherlib.bootstrap.LauncherInfos;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

public class KubeBootstrap 
{
	
	private static SplashScreen splash;
	private static SColoredBar bar;
	
	private static final LauncherInfos K_B_INFOS = new LauncherInfos("Kube", "kube.bootstrap.launcher.LauncherFrame");
	
	public static void main(String[] args)
	{
		Swinger.setResourcePath("/kube/bootstrap/resources/");
		displaySplash();
		doUpdate();
		launchLauncher();
	}
	
	private static void displaySplash()
	{
		splash = new SplashScreen("Kube", Swinger.getResource("splash.png"));
		splash.setBackground(Swinger.TRANSPARENT);
		splash.getContentPane().setBackground(Swinger.TRANSPARENT);
		
		bar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentWhite(175));
		bar.setBounds(0, 200, 50, 20);
		splash.add(bar);
		
		
		splash.setVisible(true);
	}
	
	private static void doUpdate()
	{
		
	}
	private static void launchLauncher()
	{
		
	}
}
