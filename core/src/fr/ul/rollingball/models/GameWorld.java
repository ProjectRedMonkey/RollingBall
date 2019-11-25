package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
    private int width = 80;
    private int height = 60;
    private Ball2D ball2D;
    private World world;

    /**
     * Représente le monde dans lequel on joue
     */
    public GameWorld(){
        world = new World(new Vector2(0, 0), true);
        ball2D = new Ball2D(this, new Vector2((float)width/2.0f, (float)height/2.0f));
    }

    /**
     * @return le World créé pour le jeu
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return notre boule
     */
    public Ball2D getBall2D() {
        return ball2D;
    }

    /**
     * Dessine la boule
     * @param spriteBatch spriteBatch de GameScreen
     */
    public void draw(SpriteBatch spriteBatch){
        ball2D.draw(spriteBatch);
    }

    /**
     * @return la largeur de notre monde
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return la hauteur de notre monde
     */
    public int getHeight() {
        return height;
    }
}
