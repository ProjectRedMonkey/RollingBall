package fr.ul.rollingball.models.pastilles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class SizePastille extends Pastille {
    /**
     * Pastille qui change la taille de la bille
     * @param position position de la pastille
     * @param game monde dans lequel on joue
     */
    public SizePastille(Vector2 position, GameWorld game) {
        super(position, game);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(TextureFactory.getInstance().getPastilleTaille(),this.getPosition().x-getRayon(),
                this.getPosition().y-getRayon(), getRayon()*2,getRayon()*2);
        spriteBatch.end();
    }

    @Override
    public void effet() {
        SoundFactory.getInstance().playPastilleTaille(20);
        getGameWorld().getBall2D().changeSize();
    }
}