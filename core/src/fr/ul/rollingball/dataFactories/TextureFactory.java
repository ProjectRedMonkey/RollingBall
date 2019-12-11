package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureFactory {
    private Texture intro;
    private Texture piste;
    private Texture ball;
    private Texture pastilleTemps;
    private Texture pastilleTaille;
    private Texture pastilleNormale;
    private Texture murs;
    private Texture laby;
    private Texture victoire;
    private Texture perdu;
    private Texture mursCristal;

    /**
     * Regroupe toutes les textures
     */
    private TextureFactory(){
        intro = new Texture(Gdx.files.internal("images/Intro.jpg"));
        piste = new Texture(Gdx.files.internal("images/Piste.jpg"));
        ball = new Texture(Gdx.files.internal("images/Bille2D.png"));
        pastilleTemps = new Texture(Gdx.files.internal("images/pastilleTemps.bmp"));
        pastilleTaille = new Texture(Gdx.files.internal("images/pastilleTaille.bmp"));
        pastilleNormale = new Texture(Gdx.files.internal("images/pastilleNormale.bmp"));
        murs = new Texture(Gdx.files.internal("images/Murs.jpg"));
        victoire = new Texture(Gdx.files.internal("images/Bravo.bmp"));
        perdu = new Texture(Gdx.files.internal("images/Perte.bmp"));
        mursCristal = new Texture(Gdx.files.internal("images/Murs_Salih.jpg"));
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

    /**
     * @return la texture d'une pastille normale
     */
    public Texture getPastilleNormale() {
        return pastilleNormale;
    }

    /**
     * @return la texture d'une pastille qui augmente la taille
     */
    public Texture getPastilleTaille() {
        return pastilleTaille;
    }

    /**
     * @return la texture d'une pastille temps
     */
    public Texture getPastilleTemps() {
        return pastilleTemps;
    }

    /**
     * @return la textures des murs du labyrinthe
     */
    public Texture getMurs(){
        return murs;
    }

    public Texture getLaby(int num) {
        laby = new Texture(Gdx.files.internal("images/Laby"+num+".png"));
        return laby;
    }

    public Texture getVictoire() {
        return victoire;
    }

    public Texture getPerdu() {
        return perdu;
    }

    public Texture getMursCristal() {
        return mursCristal;
    }
}
