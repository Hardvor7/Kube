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
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

public class KubeBootstrap
{

	private static SplashScreen splash;
	private static SColoredBar progressBar;
	private static Thread barUpdateThread;

	private static final File K_B_DIR = new File(GameDirGenerator.createGameDir("Kube"), "Launcher");

	private static final CrashReporter K_B_CRASH_REPORTER = new CrashReporter("Kube Bootstrap", K_B_DIR);

	public static void main(String[] args)
	{
		Swinger.setResourcePath("/kube/bootstrap/resources/");
		showSplash();
		
		try
		{
			update();
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

	private static void update() throws Exception
	{
		SUpdate su = new SUpdate("http://robin-leclair.ovh/KubeLauncher/bootstrap/", K_B_DIR);
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());

		barUpdateThread = new Thread()
		{
			@Override
			public void run()
			{
				while (!this.isInterrupted())
				{
					progressBar.setValue((int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000));
					progressBar.setMaximum((int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000));
				}
			}
		};
		barUpdateThread.start();

		su.start();
		barUpdateThread.interrupt();

	}

	private static void launch() throws LaunchException
	{
		ClasspathConstructor constructor = new ClasspathConstructor();

		ExploredDirectory gameDir = Explorer.dir(K_B_DIR);
		constructor.add(gameDir.sub("launcher_lib").allRecursive().files().match(".*\\.((jar)$)*$"));
		constructor.add(gameDir.get("launcher.jar"));

		ExternalLaunchProfile profile = new ExternalLaunchProfile("kube.launcher.LauncherFrame",
				constructor.make());
		ExternalLauncher launcher = new ExternalLauncher(profile);

		Process p = launcher.launch();
		splash.setVisible(false);

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
