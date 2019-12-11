package fr.ul.rollingball.models.balls;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.models.GameWorld;

public abstract class Ball {
    private float rayonGrand = 80/50.0f;
    private float rayonPetit = 80/100.0f;
    private float rayon;
    private Body body;
    private FixtureDef fixtureDef;
    private BodyDef bodyDef;
    private GameWorld gameWorld;

    /**
     * Classe abstraire utilisée pour créer des billes en 2D et 3D
     * @param gameWorld monde de la bille
     * @param position position de la bille
     */
    public Ball(GameWorld gameWorld,Vector2 position){
        this.gameWorld = gameWorld;
        bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = gameWorld.getWorld().createBody(bodyDef);
        rayon = rayonGrand;

        CircleShape circle = new CircleShape();
        circle.setRadius(rayon);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);

        circle.dispose();
    }

    /**
     * @return si la bille est sortie de l'écran
     */
    public boolean isOut(){
        boolean isOut = false;
        if(getPosition().x > gameWorld.getWidth() || getPosition().y > gameWorld.getHeight() || getPosition().x < 0 || getPosition().y < 0){
            isOut = true;
        }
        return isOut;
    }

    /**
     * Applique la gravité à  notre bille
     * @param vector gravité à  appliquer
     */
    public void applyForce(Vector2 vector){
        body.applyForceToCenter(vector, true);
    }

    /**
     * @return le rayon de la bille
     */
    public float getRayon(){
        return rayon;
    }

    /**
     * @return la position de la bille
     */
    public Vector2 getPosition(){
        return body.getPosition();
    }

    /**
     * @return le body de la bille, utilisée pour les collisions
     */
    public Body getBody() {
        return body;
    }

    /**
     * Change la position de la bille
     * @param position
     */
    public void setPosition(Vector2 position){
        rayon = rayonGrand;
        createBody(rayon, position);
    }

    /**
     * Change la taille de la bille selon sa taille actuelle
     */
    public void changeSize(){
        if(rayon == rayonGrand){
            rayon = rayonPetit;
        }else{
            rayon = rayonGrand;
        }
        Shape shape = body.getFixtureList().first().getShape();
        shape.setRadius(rayon);

    }

    /**
     * Recréé un body pour la bille
     * @param rayon de la bille
     * @param position de la bille
     */
    private void createBody(float rayon, Vector2 position){
        gameWorld.getWorld().destroyBody(this.getBody());
        bodyDef = new BodyDef();
        bodyDef.position.set(this.getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        body = gameWorld.getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(rayon);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);

        circle.dispose();
    }

    public void draw(SpriteBatch spriteBatch) {
    }

    public abstract void draw(ModelBatch modelBatch);
}

