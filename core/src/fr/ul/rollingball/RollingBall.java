package fr.ul.rollingball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.controllers.KeyboardListener;
import fr.ul.rollingball.views.GameScreen;
import fr.ul.rollingball.views.SplashScreen;


public class RollingBall extends Game {
	private SplashScreen splashScreen;
	private GameScreen gameScreen;

	/**
	 * Classe principale, gère les différents écrans
	 */
	@Override
	public void create () {
		splashScreen = new SplashScreen();
		setScreen(splashScreen);
		Timer timer = new Timer();
		Timer.Task task = new Timer.Task() {
			@Override
			public void run() {
				changeScreen();
			}
		};
		timer.scheduleTask(task, 3);
	}

	/**
	 * Change d'écran après 3 secondes pour passer au jeu
	 */
	private void changeScreen(){
		splashScreen.dispose();
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}
}
