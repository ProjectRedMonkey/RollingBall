package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.pastilles.Pastille;
import fr.ul.rollingball.models.pastilles.ScorePastille;
import fr.ul.rollingball.models.pastilles.SizePastille;
import fr.ul.rollingball.models.pastilles.TimePastille;

import java.util.ArrayList;

public class Maze {
    private GameWorld gameWorld;
    private int numLabyrinthe;
    private Pixmap pixmap;
    private Texture textureLabyrinthe;
    private Texture decor;
    private Vector2 positionInitialeBille;
    private ArrayList<Body> listeBriques;
    private boolean billeTrouvee = false;
    private Texture piste = TextureFactory.getInstance().getTexturePiste();

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
        spriteBatch.draw(TextureFactory.getInstance().getMurs(),0,0, gameWorld.getWidth(),gameWorld.getHeight());
        spriteBatch.draw(decor, 0, 0, gameWorld.getWidth(), gameWorld.getHeight());
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
        textureLabyrinthe = TextureFactory.getInstance().getLaby(numLabyrinthe);
        readObjects(textureLabyrinthe, listePastilles);
        buildTexLaby();
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
        int niveauGris;
        for(int i = 0 ; i < pixmap.getWidth() ; i++){
            for(int j = 0 ; j< pixmap.getHeight() ; j++){
                niveauGris = pixmap.getPixel(i, j)&255;

                //Mur
                if(niveauGris == 0) {
                    ajoutBrique(pixmap,i ,j);
                }
                //Bille
                else if(niveauGris == 100 && !billeTrouvee){
                    positionInitialeBille = new Vector2(j/(12.8f),gameWorld.getHeight()-(i/(12f)));
                    billeTrouvee = true;
                }
                //Pastille normale
                else if(niveauGris == 128){
                    listePastilles.add(new ScorePastille(new Vector2(i/12.8f,gameWorld.getHeight()-(j/(12f))),gameWorld));
                    //On colorie en blanc le reste de la pastille pour ne pas la compter plusieurs fois
                    pixmap.setColor(Color.WHITE);
                    pixmap.fillCircle(i+5, j+5, 9);
                }
                //Pastille taille
                else if(niveauGris == 200){
                    listePastilles.add(new SizePastille(new Vector2(i/(12.8f),gameWorld.getHeight()-j/12f),gameWorld));
                    pixmap.setColor(Color.WHITE);
                    pixmap.fillCircle(i+5, j+5, 9);
                }
                //Pastille temps
                else if(niveauGris == 225){
                    listePastilles.add(new TimePastille(new Vector2(i/(12.8f),gameWorld.getHeight()-j/(12f)),gameWorld));
                    pixmap.setColor(Color.WHITE);
                    pixmap.fillCircle(i+5, j+5, 9);
                }
            }
        }
        pixmap.dispose();
    }

    /**
     * Ajoute une brique si elle est près du vide
     * @param pixmap la pixmap utilisé sur le masque
     * @param i l'abscisse où l'on se trouve
     * @param j l'ordonnée où l'on se trouve
     */
    private void ajoutBrique(Pixmap pixmap, int i, int j){
        int couleurVoisin1 = pixmap.getPixel(i+1, j)&255;
        int couleurVoisin2 = pixmap.getPixel(i-1, j)&255;
        int couleurVoisin3 = pixmap.getPixel(i, j+1)&255;
        int couleurVoisin4 = pixmap.getPixel(i, j-1)&255;
        //Si l'un des voisins du mur est vide
        if(couleurVoisin1 != 0 || couleurVoisin2 != 0 || couleurVoisin3 != 0 || couleurVoisin4 != 0){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set((float) i/(12.8f),gameWorld.getHeight()-j/12f);
            bodyDef.type = BodyDef.BodyType.StaticBody;

            Body body = gameWorld.getWorld().createBody(bodyDef);
            body.setUserData("M");

            CircleShape circle = new CircleShape();
            circle.setRadius(0.1f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circle;
            fixtureDef.density = 1;
            fixtureDef.restitution = (float) 0.25;

            body.createFixture(fixtureDef);
            listeBriques.add(body);
            circle.dispose();
        }
    }

    /**
     * Construit graphiquement le labyrinthe
     */
    public void buildTexLaby(){
        if (!textureLabyrinthe.getTextureData().isPrepared()) {
            textureLabyrinthe.getTextureData().prepare();
        }
        Pixmap pixmapLaby = textureLabyrinthe.getTextureData().consumePixmap();

        if (!piste.getTextureData().isPrepared()) {
            piste.getTextureData().prepare();
        }
        Pixmap pixmapPiste = piste.getTextureData().consumePixmap();

        Texture murs= TextureFactory.getInstance().getMurs();
        if (!murs.getTextureData().isPrepared()) {
            murs.getTextureData().prepare();
        }
        Pixmap pixmapDecor = murs.getTextureData().consumePixmap();
        int niveauGris;
        Color color;
        //Parcours le labyrinthe pour récupérer les murs et le vide
        for(int i = 0 ; i < pixmapLaby.getWidth() ; i++) {
            for (int j = 0; j < pixmapLaby.getHeight(); j++) {
                niveauGris = pixmapLaby.getPixel(i, j)&255;
                //Si c'est du vide
                if(niveauGris != 0){
                    color = new Color(pixmapPiste.getPixel(i,j));
                    color.r *= 0.25;
                    color.g *= 0.25;
                    color.b *= 0.25;
                    pixmapDecor.setColor(color);
                    pixmapDecor.drawPixel(i, j);
                }
            }
        }
        this.decor = new Texture(pixmapDecor);
        pixmapLaby.dispose();
        pixmapPiste.dispose();
        pixmapDecor.dispose();
    }

    /**
     * Passe au labyrinthe suivant
     */
    public void nextLaby(){
        if(numLabyrinthe < 5) {
            numLabyrinthe++;
        }else{
            gameWorld.jeuFini();
        }
    }

    /**
     * Change de labyrinthe
     */
    public void changeLaby(ArrayList <Pastille> listePastille){
        for (Pastille pastille: listePastille) {
            gameWorld.getWorld().destroyBody(pastille.getBody());
        }
        listePastille.clear();
        //On détruit toutes les briques
        for (Body brique: listeBriques) {
            gameWorld.getWorld().destroyBody(brique);
        }
        listeBriques.clear();

        nextLaby();
        billeTrouvee = false;
        loadLaby(listePastille);
        gameWorld.getBall2D().setPosition(positionInitialeBille);
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

    public ArrayList<Body> getListeBriques() {
        return listeBriques;
    }
}

