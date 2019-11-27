package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class GameWorld {
    private int width = 80;
    private int height = 60;
    private Ball2D ball2D;
    private World world;
    private ArrayList<Pastille> listePastilles;
    private ContactListener contact;

    /**
     * Représente le monde dans lequel on joue
     */
    public GameWorld(){
        world = new World(new Vector2(0, 0), true);
        ball2D = new Ball2D(this, new Vector2((float)width/2.0f, (float)height/2.0f));
        listePastilles  = new ArrayList<>();
        listePastilles.add(new ScorePastille(new Vector2(width/2+10,height/2+10), this));
        listePastilles.add(new ScorePastille(new Vector2(width/2-10,height/2-10), this));
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
        for(Pastille pastille : listePastilles){
            pastille.draw(spriteBatch);
        }
    }

    public void beginContact(Contact contact){

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
