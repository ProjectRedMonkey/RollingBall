package fr.ul.rollingball.models.pastilles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class ScorePastille extends Pastille {
    private float stateTime = 0f;
    private Animation animation;

    /**
     * Pastille augmentant le score
     * @param position position de la pastille
     * @param game monde dans lequel on joue
     */
    public ScorePastille(Vector2 position, GameWorld game) {
        super(position, game);
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/pastilleNormale.pack"));

        animation = new Animation(1f/8f, atlas.getRegions());
    }

    /**
     * Dessine la pastille
     * @param spriteBatch sur lequel on a déjà  la bille
     */
    @Override
    public void draw(SpriteBatch spriteBatch) {
        stateTime += Gdx.graphics.getDeltaTime();
        spriteBatch.begin();
        spriteBatch.draw((TextureRegion) animation.getKeyFrame(stateTime, true),this.getPosition().x-getRayon(),
                this.getPosition().y-getRayon(), getRayon()*2,getRayon()*2);
        spriteBatch.end();
    }

    /**
     * Joue une musique
     */
    @Override
    public void effet() {
        SoundFactory.getInstance().playPastille(20);
        getGameWorld().getGameScreen().augmenterScore();
        getGameWorld().getGameScreen().ajouterTemps(1);
    }
}