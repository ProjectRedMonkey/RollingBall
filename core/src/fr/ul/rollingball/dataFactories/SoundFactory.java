package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundFactory {
    private Music victoire;
    private Sound pastille;
    private Sound pastilleTemps;
    private Sound pastilleTaille;
    private Music alerte;
    private Music defaite;

    /**
     * Regroupe tous les sons
     */
    private SoundFactory(){
        victoire = Gdx.audio.newMusic(Gdx.files.internal("sounds/victoire.mp3"));
        pastille = Gdx.audio.newSound(Gdx.files.internal("sounds/pastille.wav"));
        pastilleTemps = Gdx.audio.newSound(Gdx.files.internal("sounds/ptemps.wav"));
        pastilleTaille = Gdx.audio.newSound(Gdx.files.internal("sounds/ptaille.wav"));
        alerte = Gdx.audio.newMusic(Gdx.files.internal("sounds/alerte.mp3"));
        defaite = Gdx.audio.newMusic(Gdx.files.internal("sounds/perte.mp3"));
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
        pastille.play(volume/100);
    }

    /**
     * Bruit lorsque l'on ramasse une pastille temps
     * @param volume volume choisi
     */
    public void playPastilleTemps(float volume){
        pastilleTemps.play(volume/100);
    }

    /**
     * Bruit lorsque l'on ramasse une pastille taille
     * @param volume volume choisi
     */
    public void playPastilleTaille(float volume){
        pastilleTaille.play(volume/100);
    }

    /**
     * Préviens le joueur qu'il reste peu de temps
     * @param volume volume choisi
     */
    public void playAlerte(float volume){
        alerte.setVolume(volume/100);
        alerte.play();
    }

    public void playDefaite(float volume){
        defaite.setVolume(volume/100);
        defaite.play();
    }
}
