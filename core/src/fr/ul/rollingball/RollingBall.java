package fr.ul.rollingball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;
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
		SoundFactory.getInstance().playVictoire(10);
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
		gameScreen = new GameScreen();
		splashScreen.dispose();
		setScreen(gameScreen);
	}
}
