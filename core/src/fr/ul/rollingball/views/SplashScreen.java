package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import fr.ul.rollingball.RollingBall;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class SplashScreen extends ScreenAdapter {
    private SpriteBatch intro;
    private OrthographicCamera camera;

    /**
     * Ecran d'acceuil de notre jeu
     */
    public SplashScreen(){
        intro = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024,720);
        camera.update();
    }

    /**
     * Affiche le SpriteBatch de l'intro
     */
    @Override
    public void render (float delta) {
        intro.setProjectionMatrix(camera.combined);
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
