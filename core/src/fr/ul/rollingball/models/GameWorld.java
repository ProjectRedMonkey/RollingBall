package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Iterator;

public class GameWorld {
    private int width = 80;
    private int height = 60;
    private Ball2D ball2D;
    private World world;
    private ArrayList<Pastille> listePastilles;
    private ContactListener contact;
    private Maze maze;

    /**
     * Représente le monde dans lequel on joue
     */
    public GameWorld(){
        world = new World(new Vector2(0, 0), true);
        ball2D = new Ball2D(this, new Vector2((float)width/2.0f, (float)height/2.0f));
        listePastilles  = new ArrayList<>();
        listePastilles.add(new ScorePastille(new Vector2(width/2+10,height/2+10), this));
        listePastilles.add(new ScorePastille(new Vector2(width/2-10,height/2-10), this));
        listePastilles.add(new SizePastille(new Vector2(10,15), this));
        listePastilles.add(new SizePastille(new Vector2(width/2-3,height/2+12), this));
        listePastilles.add(new TimePastille(new Vector2(width/2+14,height/2-13), this));
        listePastilles.add(new TimePastille(new Vector2(width/2+17,height/2+13), this));
        contact = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture pastilleFixture;
                if(contact.getFixtureA().getBody().getType() == BodyDef.BodyType.DynamicBody){
                    pastilleFixture = contact.getFixtureB();
                }else{
                    pastilleFixture = contact.getFixtureA();
                }
                if(pastilleFixture.getBody().getUserData() instanceof Pastille){
                    ((Pastille) pastilleFixture.getBody().getUserData()).setPicked(true);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };
        world.setContactListener(contact);
        maze = new Maze(this);
        maze.loadLaby(listePastilles);
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


    public void updatePastilles(){
        Iterator<Pastille> it = listePastilles.iterator();
        while(it.hasNext()) {
            Pastille pastille = it.next();
            if (pastille.getPicked()) {
                pastille.effet();
                world.destroyBody(pastille.getBody());
                it.remove();
            }
        }
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

    public ContactListener getContact() {
        return contact;
    }
}

