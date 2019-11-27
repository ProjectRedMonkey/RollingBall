package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundFactory {
    private Music victoire;
    private Music pastille;

    /**
     * Regroupe tous les sons
     */
    private SoundFactory(){
        victoire = Gdx.audio.newMusic(Gdx.files.internal("sounds/victoire.mp3"));
        pastille = Gdx.audio.newMusic(Gdx.files.internal("sounds/pastille.wav"));
    }

    /**
     * Singleton
     */
    private static SoundFactory INSTANCE = new SoundFactory();

    public static SoundFactory getInstance(){
        return INSTANCE;
    }

    /**
     * Permets de jouer le thème "victoire"
     * @param volume volume choisi
     */
    public void playVictoire(float volume) {
        victoire.setVolume(volume/100);
        victoire.play();
    }

    /**
     * Bruit lorsque l'on ramasse une pastille normale
     * @param volume volume choisi
     */
    public void playPastille(float volume){
        pastille.setVolume(volume);
        pastille.play();
    }
}
