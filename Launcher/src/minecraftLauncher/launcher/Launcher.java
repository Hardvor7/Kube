package minecraftLauncher.launcher;

import java.io.File;

import fr.theshark34.openlauncherlib.launcher.GameInfos;
import fr.theshark34.openlauncherlib.launcher.GameTweak;
import fr.theshark34.openlauncherlib.launcher.GameType;
import fr.theshark34.openlauncherlib.launcher.GameVersion;

public class Launcher
{
	public static final GameVersion ML_VESRION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
	public static final GameInfos ML_INFOS = new GameInfos("Minecraft Launcher 1", ML_VESRION, true, new GameTweak[] { GameTweak.FORGE });
	public static final File ML_DIR = ML_INFOS.getGameDir();
}
