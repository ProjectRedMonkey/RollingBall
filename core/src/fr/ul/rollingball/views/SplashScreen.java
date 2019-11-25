package fr.ul.rollingball.views;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.rollingball.RollingBall;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class SplashScreen extends ScreenAdapter {
    private SpriteBatch intro;

    /**
     * Ecran d'acceuil de notre jeu
     */
    public SplashScreen(){
        intro = new SpriteBatch();
    }

    /**
     * Affiche le SpriteBatch de l'intro
     */
    @Override
    public void render (float delta) {
        intro.begin();
        intro.draw(TextureFactory.getInstance().getTextureIntro(), 0, 0);
        intro.end();
    }

    /**
     * Supprime le SpriteBatch
     */
    public void dispose(){
        intro.dispose();
    }
}
