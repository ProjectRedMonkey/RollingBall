package fr.ul.rollingball.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Ball {
    private double rayonGrand = 80/50.0f;
    private double rayonPetit = 80/100.0f;
    private double rayon;
    private Body body;

    /**
     * Classe abstraire utilisée pour créer des billes en 2D et 3D
     * @param gameWorld monde de la bille
     * @param position position de la bille
     */
    public Ball(GameWorld gameWorld,Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = gameWorld.getWorld().createBody(bodyDef);
        rayon = rayonGrand;
        CircleShape circle = new CircleShape();
        circle.setRadius((float) rayon);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1;
        fixtureDef.restitution = (float) 0.25;
        body.createFixture(fixtureDef);

        circle.dispose();
    }

    /**
     * Applique la gravité à notre bille
     * @param vector gravité à appliquer
     */
    public void applyForce(Vector2 vector){
        body.setLinearVelocity(vector);
    }

    /**
     * @return le rayon de la bille
     */
    public double getRayon(){
        return rayon;
    }

    /**
     * @return la position de la bille
     */
    public Vector2 getPosition(){
        return body.getPosition();
    }
}
