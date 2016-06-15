package com.worms.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.worms.game.Worms;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int fps = 60;
		config.width = 1280;
		config.height = 720;
		config.foregroundFPS = fps;
		config.backgroundFPS = fps;
		config.vSyncEnabled = false;
		new LwjglApplication(new Worms(), config);
	}
}
