package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Pastille {
    private static int rayon = 1;
    private GameWorld gameWorld;
    private Body body;
    private boolean ramassee;
    private Vector2 position;

    /**
     * Petite boule représentant un bonus à ramasser
     */
    public Pastille(Vector2 position, GameWorld game){
        this.gameWorld = game;
        this.position = position;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = gameWorld.getWorld().createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(rayon);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = circle;
        fixtureDef.density = 1;
        fixtureDef.restitution = (float) 0.25;
        body.createFixture(fixtureDef);

        circle.dispose();
    }

    /**
     * @return le corps physique de la pastille
     */
    public Body getBody() {
        return body;
    }

    /**
     * @return le rayon de la pastille
     */
    public float getRayon() {
        return rayon;
    }

    /**
     * @return la position de la pastille
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * @return true si la pastille a été ramassée
     */
    public boolean getPicked(){
        return ramassee;
    }

    /**
     * Permets de dire si la pastille a été ramassée
     * @param picked ramassée ou non
     */
    public void setPicked(boolean picked) {
        this.ramassee = picked;
    }

    /**
     * Dessine la pastille
     */
    public abstract void draw(SpriteBatch spriteBatch);

    /**
     * Applique l'effet de la pastille
     */
    public abstract void effet();
}
