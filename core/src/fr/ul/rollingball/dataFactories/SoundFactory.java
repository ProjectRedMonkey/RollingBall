package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundFactory {
    private Music sound;

    private SoundFactory(){
        sound = Gdx.audio.newMusic(Gdx.files.internal("sounds/victoire.mp3"));
    }

    /**
     * Singleton
     */
    private static SoundFactory INSTANCE = new SoundFactory();

    public static SoundFactory getInstance(){
        return INSTANCE;
    }

    public void playSound(float volume) {
        sound.setVolume(volume/100);
        sound.play();
    }
}
