package kube.launcher;

import java.io.File;
import java.util.Arrays;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class Launcher
{
	
	public static float PROGRESSION = 0;
	
	public static final GameVersion K_VESRION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
	public static final GameInfos K_INFOS = new GameInfos("Kube", K_VESRION, new GameTweak[] { GameTweak.FORGE });
	public static final File K_DIR = K_INFOS.getGameDir();
	public static final File K_CRASH_DIR = new File(K_DIR, "crashes");

	private static AuthInfos authInfos;
	private static Thread updateThread;

	private static String serverAddress = "robin-leclair.ovh";
	
	private static LauncherView launcherView = null;
	
	
	public static Thread getUpdateThread() {
		return updateThread;
	}
	
	public static void setLauncherView(LauncherView lView) 
	{
		launcherView = lView;
	}
	
	public static void auth(String username, String password) throws AuthenticationException
	{
		Platform.runLater(()->launcherView.infoLabel.setText("Connexion à Minecraft.net..."));
		Authenticator authentificator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = authentificator.authenticate(AuthAgent.MINECRAFT, username, password, "");
		authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(),
				response.getSelectedProfile().getId());
	}

	public static void update() throws Exception
	{
		Platform.runLater(()->launcherView.infoLabel.setText("Connexion à Kube..."));
		SUpdate su = new SUpdate("http://" + serverAddress + "/KubeLauncher/", K_DIR);
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());
		
		updateThread = new Thread() {
			
			int val = 0;
			int max = 2;
			
			@Override 
			public void run() {
		    	
		    	boolean progressBarisShowed = false;
				
				while(!isInterrupted()) {
					if (BarAPI.getNumberOfFileToDownload() == 0)
					{
						Platform.runLater(()->launcherView.infoLabel.setText("Vérification des fichiers..."));
						try {
							sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}else if(!progressBarisShowed)
					{
						Platform.runLater(()->launcherView.progressBar.show());
					}
					

					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					try
					{
						
						Platform.runLater(()->launcherView.progressBar.setValue((float)val/max));
						Platform.runLater(()->launcherView.infoLabel.setText("Téléchargement des fichiers : " + BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload()));
						
						sleep(50);
						
						
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
					
					
				}
			}
		};
		updateThread.start();
		su.start();
		interruptThread();
	}

	public static void launch() throws LaunchException
	{	
		Platform.runLater(()->launcherView.infoLabel.setText("Lancement du jeu..."));
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(K_INFOS, GameFolder.BASIC, authInfos);
		profile.getVmArgs().addAll(Arrays.asList("-Xms" + launcherView.getRamChoice(), "-Xmx" + launcherView.getRamChoice()));
		ExternalLauncher launcher = new ExternalLauncher(profile);

		Process p = launcher.launch();
		
/*
 *		ProcessLogManager manager = new ProcessLogManager(p.getInputStream(), new File(K_DIR, "logs.txt"));
 *		manager.start();
 */
		
		try
		{
			Thread.sleep(10000L);
			
			Platform.runLater(()-> {
				Timeline timeline = new Timeline();
	            KeyFrame key = new KeyFrame(Duration.millis(500),
	                           new KeyValue (launcherView.primaryStage.getScene().getRoot().opacityProperty(), 0));
	            KeyFrame key2 = new KeyFrame(Duration.millis(500),
	                    		new KeyValue (launcherView.getSoundSlider().valueProperty(), 0));
	            timeline.getKeyFrames().add(key);
	            timeline.getKeyFrames().add(key2);  
	           timeline.setOnFinished(e->launcherView.primaryStage.close());
	            timeline.play();	
			});
			
			p.waitFor();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.exit(0);
	}

	public static void interruptThread()
	{
		updateThread.interrupt();
	}
	
	public static void setServerAddress(String address)
	{
		serverAddress = address;
	}
}
