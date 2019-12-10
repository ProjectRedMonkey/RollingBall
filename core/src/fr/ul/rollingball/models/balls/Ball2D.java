package fr.ul.rollingball.models.balls;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;
import fr.ul.rollingball.models.balls.Ball;

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
                        ,this.getPosition().x-getRayon(), this.getPosition().y-getRayon(),
                    (float)getRayon()*2,(float)getRayon()*2);
        spriteBatch.end();
    }
}
