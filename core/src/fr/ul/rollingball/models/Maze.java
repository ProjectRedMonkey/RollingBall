package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Maze {
    private GameWorld gameWorld;
    private int numLabyrinthe;
    private Pixmap pixmap;
    private Texture textureLabyrinthe;
    private Vector2 positionInitialeBille;
    private ArrayList<Body> listeBriques;

    /**
     * Représente un labyrinthe dans lequel se déplace la bille
     * @param gameWorld le monde dans lequel on joue
     */
    public Maze(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        numLabyrinthe = 0;
        positionInitialeBille = new Vector2();
        listeBriques = new ArrayList<Body>();
    }

    /**
     * Affiche le labyrinthe
     * @param spriteBatch la liste d'affichage
     */
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(textureLabyrinthe,0,0);
        spriteBatch.end();
    }

    /**
     * Détruit les images internes
     */
    public void dispose(){
        textureLabyrinthe.dispose();
        pixmap.dispose();
    }

    /**
     * Charge un nouveau labyrinthe
     * @param listePastilles pastilles à  ajouter au nouveau labyrinthe
     */
    public void loadLaby(ArrayList<Pastille> listePastilles){
        //On détruit toutes les briques
        for (Body brique: listeBriques) {
            gameWorld.getWorld().destroyBody(brique);
        }

        Texture masque = new Texture(Gdx.files.internal("images/Laby"+numLabyrinthe+".png"));
        readObjects(masque, listePastilles);

    }

    /**
     * Récupère les pastilles, murs et la bille dans un masque
     * @param masque du labyrinthe à  utiliser
     */
    private void readObjects(Texture masque, ArrayList<Pastille> listePastilles){
        if (!masque.getTextureData().isPrepared()) {
            masque.getTextureData().prepare();
        }
        pixmap = masque.getTextureData().consumePixmap();
        Color color = new Color();
        for(int i = 0 ; i < gameWorld.getWidth() ; i++){
            for(int j = 0 ; j < gameWorld.getHeight() ; j++){
                int val = pixmap.getPixel(i, j);
                Color.rgba8888ToColor(color, val);
                int R = (int)(color.r * 255f);
                int G = (int)(color.g * 255f);
                int B = (int)(color.b * 255f);
                int A = (int)(color.a * 255f);
                int niveauGris = (R+G+B)/3;
                //System.out.println(niveauGris);
            }
        }
    }

    /**
     * @return la position initiale de la bille dans le labyrinthe
     */
    public Vector2 getPositionInitialeBille() {
        return positionInitialeBille;
    }

    /**
     * @return la texture du labyrinthe
     */
    public Texture getTextureLabyrinthe() {
        return textureLabyrinthe;
    }
}

