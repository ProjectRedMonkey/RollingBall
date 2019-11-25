package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class Ball2D extends Ball {

    /**
     * Créé une balle en 2D
     * @param gameWorld monde de la bille
     * @param position position de la bille
     */
    public Ball2D(GameWorld gameWorld, Vector2 position) {
        super(gameWorld, position);
    }

    /**
     * Dessine la boule
     * @param spriteBatch sur lequel on l'affiche
     */
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(TextureFactory.getInstance().getTextureBille2D()
                        ,this.getPosition().x, this.getPosition().y,
                    (float)getRayon(),(float)getRayon());
        spriteBatch.end();
    }
}
