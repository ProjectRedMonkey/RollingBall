package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class ScorePastille extends Pastille {
    /**
     * Petite boule représentant un bonus à ramasser
     * @param position position de la pastille
     * @param game monde dans lequel on joue
     */
    public ScorePastille(Vector2 position, GameWorld game) {
        super(position, game);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(TextureFactory.getInstance().getPastilleNormale(),this.getPosition().x-getRayon(), this.getPosition().y-getRayon(), getRayon()*2,getRayon()*2);
        spriteBatch.end();
    }

    @Override
    public void effet() {
        SoundFactory.getInstance().playPastille(20);
    }
}