package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch affichageJeu;
    private GameWorld gameWorld;
    private OrthographicCamera camera;

    /**
     * Ecran de jeu
     */
    public GameScreen(){
        affichageJeu = new SpriteBatch();
        gameWorld = new GameWorld();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,gameWorld.getWidth(),gameWorld.getHeight());
        camera.update();
    }

    /**
     * Affiche l'écran de jeu
     */
    @Override
    public void render (float delta) {
        update();
        affichageJeu.setProjectionMatrix(camera.combined);
        affichageJeu.begin();
        affichageJeu.draw(TextureFactory.getInstance().getTexturePiste(), 0, 0,gameWorld.getWidth(),gameWorld.getHeight());
        affichageJeu.end();
        gameWorld.draw(affichageJeu);
    }

    /**
     * Actualise graphiquement le monde, appelé dans GameScreen avant l'affichage
     */
    public void update(){
        Vector2 force = new Vector2((Gdx.input.getAccelerometerY()*5f),-(Gdx.input.getAccelerometerX()*5f));
        gameWorld.getBall2D().applyForce(force);
        gameWorld.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    /**
     * Efface l'écran de jeu
     */
    public void dispose(){
        affichageJeu.dispose();
    }


}