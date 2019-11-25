package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureFactory {
    private Texture intro;
    private Texture piste;
    private Texture ball;

    /**
     * Récupère les textures
     */
    private TextureFactory(){
        intro = new Texture(Gdx.files.internal("images/Intro.jpg"));
        piste = new Texture(Gdx.files.internal("images/Piste.jpg"));
        ball = new Texture(Gdx.files.internal("images/Bille2D.png"));
    }

    /**
     * Singleton
     */
    private static TextureFactory INSTANCE = new TextureFactory();

    public static TextureFactory getInstance(){
        return INSTANCE;
    }

    /**
     * @return la texture de l'écran d'acceuil
     */
    public Texture getTextureIntro(){
        return intro;
    }

    /**
     * @return la texture de l'arrière plan du jeu
     */
    public Texture getTexturePiste(){
        return piste;
    }

    /**
     * @return la texture de la bille
     */
    public Texture getTextureBille2D(){
        return ball;
    }
}
