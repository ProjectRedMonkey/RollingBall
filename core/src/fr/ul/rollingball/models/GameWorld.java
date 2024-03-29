package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.models.balls.Ball;
import fr.ul.rollingball.models.balls.Ball2D;
import fr.ul.rollingball.models.balls.Ball3D;
import fr.ul.rollingball.models.pastilles.Pastille;
import fr.ul.rollingball.views.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class GameWorld {
    private float width = 80;
    private float height = 60;
    private Ball ball2D;
    private World world;
    private ArrayList<Pastille> listePastilles;
    private ContactListener contact;
    private Maze maze;
    private boolean jeuFini;
    private GameScreen gameScreen;
    private boolean ball = false;
    private boolean tampon = true;
    private ModelBatch modelBatch;
    private boolean grand;

    /**
     * Représente le monde dans lequel on joue
     */
    public GameWorld(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        jeuFini = false;
        world = new World(new Vector2(0, 0), true);
        maze = new Maze(this);
        listePastilles  = new ArrayList<>();
        maze.loadLaby(listePastilles);
        ball2D = new Ball2D(this, maze.getPositionInitialeBille());
        modelBatch = new ModelBatch();

        //Gère les collisions
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
                if(pastilleFixture.getBody().getUserData() == "M"){
                    SoundFactory.getInstance().playRebond(2);
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
    public Ball getBall2D() {
        return ball2D;
    }

    /**
     * Dessine la boule
     * @param spriteBatch spriteBatch de GameScreen
     */
    public void draw(SpriteBatch spriteBatch){
        if(tampon != gameScreen.getGestureListener().isMur()) {
            maze.setMurDeBase(gameScreen.getGestureListener().isMur());
            maze.setChangementMurs(true);
            tampon = gameScreen.getGestureListener().isMur();
        }
        maze.draw(spriteBatch);
        if(ball) {
            if(ball2D instanceof Ball3D) {
                grand = ball2D.isGrand();
                world.destroyBody(ball2D.getBody());
                ball2D = new Ball2D(this, ball2D.getPosition());
                if(!grand){
                    ball2D.changeSize();
                }
            }
            ball2D.draw(spriteBatch);
        }else{
            if(ball2D instanceof Ball2D) {
                grand = ball2D.isGrand();
                world.destroyBody(ball2D.getBody());
                ball2D = new Ball3D(this, ball2D.getPosition(), grand);
                if(!grand){
                    ((Ball3D) ball2D).changeBody();
                }
            }
            modelBatch.begin(gameScreen.getCamera());
            ball2D.draw(modelBatch);
            modelBatch.end();
        }
        for(Pastille pastille : listePastilles){
            pastille.draw(spriteBatch);
        }
    }


    /**
     * Efface les pastilles ramassées
     */
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

    ///////////////////////////
    /////GETTERS et SETTERS////
    ///////////////////////////

    public boolean isVictory(){
        return ball2D.isOut();
    }


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setBall2D(Ball2D ball2D) {
        this.ball2D = ball2D;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public ArrayList<Pastille> getListePastilles() {
        return listePastilles;
    }

    void jeuFini(){
        jeuFini = true;
    }

    public boolean estJeuFini(){
        return jeuFini;
    }

    public void setJeuFini(boolean jeuFini) {
        this.jeuFini = jeuFini;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setBall(boolean ball) {
        this.ball = ball;
    }
}

