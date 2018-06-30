package kube.bootstrap;

import java.io.File;

import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import javafx.application.Platform;

public class Bootstrap {
	
	private static BootstrapView bootStrapView;
	private static Thread barUpdateThread;
	private static final File K_B_DIR = new File(GameDirGenerator.createGameDir("Kube"), "Launcher");
	private static String serverAddress = "";
	
	public static void setServerAddress(String string) {
		serverAddress = string;
	}
	
	public static void update()
	{
		SUpdate su = new SUpdate("http://" + serverAddress + "/KubeLauncher/bootstrap/", K_B_DIR);
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());
		
		
		barUpdateThread = new Thread()
		{
			int val = 0;
			int max = 0;
			
			@Override
			public void run()
			{
				
				while (!this.isInterrupted())
				{
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					
					//Platform.runLater(()->bootStrapView.getProgressbar().setValue((float)val/max));
					try {
						sleep(50);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		try {
			barUpdateThread.start();
			su.start();
			barUpdateThread.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

	public static void launch()
	{
		ClasspathConstructor constructor = new ClasspathConstructor();

		ExploredDirectory gameDir = Explorer.dir(K_B_DIR);
		constructor.add(gameDir.sub("launcher_lib").allRecursive().files().match(".*\\.((jar)$)*$"));
		constructor.add(gameDir.get("launcher.jar"));

		ExternalLaunchProfile profile = new ExternalLaunchProfile("kube.launcher.LauncherFrame",
				constructor.make());
		ExternalLauncher launcher = new ExternalLauncher(profile);

		Process p = null;
		try {
			p = launcher.launch();
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// Hide window
		
		try
		{
			p.waitFor();
		}
		catch (InterruptedException e)
		{
		}
		System.exit(0);
	}
	
}
