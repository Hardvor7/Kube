package kube.bootstrap;

import java.awt.Color;
import java.io.File;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;

public class KubeBootstrap
{

	private static SplashScreen splash;
	private static CustomProgressBar progressBar;
	private static Thread barUpdateThread;

	private static final File K_B_DIR = new File(GameDirGenerator.createGameDir("Kube"), "Launcher");

	private static final CrashReporter K_B_CRASH_REPORTER = new CrashReporter("Kube Bootstrap", K_B_DIR);

	private static String serverAddress = "robin-leclair.ovh";
	
	public static void main(String[] args)
	{
		if (args.length >= 1)
		{
			if (args[0].equals("local"))
			{
				serverAddress = "192.168.1.25";
			}
		}
		
		
		showSplash();
		
		try
		{
			Bootstrap.update();
		}
		catch (Exception e)
		{
			if (barUpdateThread != null)
				barUpdateThread.interrupt();
			
			K_B_CRASH_REPORTER.catchError(e, "Mise à jour du launcher impossible");
		}

		try
		{
			launch();
		}
		catch (LaunchException e)
		{
			K_B_CRASH_REPORTER.catchError(e, "Impossible de lancer le launcher");
		}
	}

	private static void showSplash()
	{
		splash = new SplashScreen("Kube", Swinger.getResource("splash.png"));
		splash.setIconImage(Swinger.getResource("icon.png"));
		splash.setLayout(null);

		progressBar = new SColoredBar(Color.gray, Swinger.getTransparentWhite(175));
		progressBar.setBounds(0, 105, 400, 20);
		splash.add(progressBar);

		splash.setVisible(true);
	}

	
}
