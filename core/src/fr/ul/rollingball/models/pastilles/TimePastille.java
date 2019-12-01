package fr.ul.rollingball.models.pastilles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class TimePastille extends Pastille {
    /**
     * Pastille donnant un temps supplémentaire
     * @param position position de la pastille
     * @param game monde dans lequel on joue
     */
    public TimePastille(Vector2 position, GameWorld game) {
        super(position, game);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(TextureFactory.getInstance().getPastilleTemps(),this.getPosition().x-getRayon(), this.getPosition().y-getRayon(), getRayon()*2,getRayon()*2);
    }

    @Override
    public void effet() {
        SoundFactory.getInstance().playPastilleTemps(20);
    }
}