package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundFactory {
    private Music victoire;
    private Music pastille;
    private Music pastilleTemps;
    private Music pastilleTaille;

    /**
     * Regroupe tous les sons
     */
    private SoundFactory(){
        victoire = Gdx.audio.newMusic(Gdx.files.internal("sounds/victoire.mp3"));
        pastille = Gdx.audio.newMusic(Gdx.files.internal("sounds/pastille.wav"));
        pastilleTemps = Gdx.audio.newMusic(Gdx.files.internal("sounds/ptemps.wav"));
        pastilleTaille = Gdx.audio.newMusic(Gdx.files.internal("sounds/ptaille.wav"));
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
        pastille.setVolume(volume/100);
        pastille.play();
    }

    /**
     * Bruit lorsque l'on ramasse une pastille temps
     * @param volume volume choisi
     */
    public void playPastilleTemps(float volume){
        pastilleTemps.setVolume(volume/100);
        pastilleTemps.play();
    }

    /**
     * Bruit lorsque l'on ramasse une pastille taille
     * @param volume volume choisi
     */
    public void playPastilleTaille(float volume){
        pastilleTaille.setVolume(volume/100);
        pastilleTaille.play();
    }
}
