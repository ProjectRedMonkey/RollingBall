package fr.ul.rollingball;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fr.ul.rollingball.RollingBall;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.a = 8; // Indique le nombre de bits de codage du canal alpha
		config.useAccelerometer = true; //Active l'accéleromètre
		initialize(new RollingBall(), config);
	}
}
