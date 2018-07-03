package kube.bootstrap;

import java.io.File;

import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
//import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import kube.utils.ResourcesUtil;

public class Bootstrap {
	
	private static BootstrapView bootStrapView;
	private static Thread barUpdateThread;
	private static final File K_B_DIR = new File(GameDirGenerator.createGameDir("Kube"), "Launcher");
	private static String serverAddress = "robin-leclair.ovh";
	
	public static void setServerAddress(String string) {
		serverAddress = string;
	}
	
	public static void setBootstrapView(BootstrapView view) {
		bootStrapView = view;
	}
	
	public static void update()
	{
		SUpdate su = new SUpdate("http://" + serverAddress + "/KubeLauncher/bootstrap/", K_B_DIR);
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());
		
		
		barUpdateThread = new Thread()
		{
			//int val = 0;
			//int max = 0;
			
			@Override
			public void run()
			{
				
				while (!this.isInterrupted())
				{
					//val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					//max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					
					//Platform.runLater(()->bootStrapView.getProgressbar().setValue((float)val/max));
					try {
						sleep(50);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		try 
		{
			barUpdateThread.start();
			su.start();
			barUpdateThread.interrupt();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		

	}

	public static void launch()
	{
		try 
		{
			new ResourcesUtil(System.getenv("APPDATA") + "/.Kube/Launcher", null);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		ClasspathConstructor constructor = new ClasspathConstructor();

		ExploredDirectory gameDir = Explorer.dir(K_B_DIR);
		constructor.add(gameDir.sub("launcher_lib").allRecursive().files().match(".*\\.((jar)$)*$"));
		constructor.add(gameDir.get("launcher.jar"));
		
		ExternalLaunchProfile profile = new ExternalLaunchProfile("kube.launcher.LauncherView",
				constructor.make());
		ExternalLauncher launcher = new ExternalLauncher(profile);

		Process p = null;
		try {
			p = launcher.launch();
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Platform.runLater(()->{
			Timeline timeline = new Timeline();
	        KeyFrame key = new KeyFrame(Duration.millis(500),
	                       new KeyValue (bootStrapView.getPrimaryStage().getScene().getRoot().opacityProperty(), 0));
	        timeline.getKeyFrames().add(key);
	        timeline.setOnFinished(ea->bootStrapView.getPrimaryStage().close());
	        timeline.play();
		});
		
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
