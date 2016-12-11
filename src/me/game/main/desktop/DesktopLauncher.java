package me.game.main.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonValue;

import me.game.main.GameClass;
import me.game.util.json.JsonHandler;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.width = 213*4;
		config.height = 120*4;
		config.resizable = true;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		
		GameClass gameclass = new GameClass();

		new LwjglApplication(gameclass, config);
		
		
		try {
		JsonValue obj  = (new JsonHandler()).getObjectFromFile(Gdx.files.internal("scripts/game.json").read());
		if(obj.has("name"))
			config.title = obj.getString("name");
		String icon = "empty";
		if(obj.has("icon"))
			icon = obj.getString("icon");
		
		if(!icon.equalsIgnoreCase("empty"))
				config.addIcon(icon, FileType.Internal);
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}
