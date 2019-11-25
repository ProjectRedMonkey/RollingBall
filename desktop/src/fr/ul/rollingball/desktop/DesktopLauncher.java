package fr.ul.rollingball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.ul.rollingball.RollingBall;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.a = 8; // Indique le nombre de bits de codage du canal alpha
		config.width = 1024; // Change les dimensions de la fenêtre d'affichage
		config.height = 720;
		new LwjglApplication(new RollingBall(), config);
	}
}
