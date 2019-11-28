package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
        camera.setToOrtho(false, gameWorld.getWidth(),gameWorld.getHeight());
        camera.update();
    }

    /**
     * Affiche l'écran de jeu
     */
    @Override
    public void render (float delta) {
        update();
        affichageJeu.setProjectionMatrix(camera.combined);
        gameWorld.draw(affichageJeu);
        /*
        //Utilisé pour voir la hitbox des bodies
        Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.render(gameWorld.getWorld(), camera.combined);
        */

    }

    /**
     * Actualise graphiquement le monde, appelé dans GameScreen avant l'affichage
     */
    public void update(){
        Vector2 force = new Vector2((Gdx.input.getAccelerometerY()*4f),-(Gdx.input.getAccelerometerX()*4f));
        gameWorld.getBall2D().applyForce(force);
        gameWorld.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
        gameWorld.updatePastilles();
    }

    /**
     * Efface l'écran de jeu
     */
    public void dispose(){
        affichageJeu.dispose();
    }


}