package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.TextureFactory;

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
        spriteBatch.draw(TextureFactory.getInstance().getMurs(),0,0,gameWorld.getWidth(),gameWorld.getHeight());
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
        //On détruit toutes les briques
        for (Body brique: listeBriques) {
            gameWorld.getWorld().destroyBody(brique);
        }

        textureLabyrinthe = new Texture(Gdx.files.internal("images/Laby"+numLabyrinthe+".png"));
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
        for(int i = 0 ; i < masque.getWidth() ; i++){
            for(int j = 0 ; j < masque.getHeight() ; j++){
                niveauGris = pixmap.getPixel(i, j)&255;

                //Mur
                if(niveauGris == 0) {
                    ajoutBrique(pixmap,i ,j);
                }
                //Bille
                else if(niveauGris == 100 && !billeTrouvee){
                    positionInitialeBille = new Vector2(i/(masque.getWidth()/gameWorld.getWidth()),gameWorld.getHeight()-(j/(masque.getHeight()/gameWorld.getHeight())));
                    billeTrouvee = true;
                    System.out.println(positionInitialeBille);
                }
                //Pastille normale
                else if(niveauGris == 128){
                    listePastilles.add(new ScorePastille(new Vector2(i/(masque.getWidth()/gameWorld.getWidth()),gameWorld.getHeight()-j/(masque.getHeight()/gameWorld.getHeight())),gameWorld));
                    //On colorie en blanc le reste de la pastille pour ne pas la compter plusieurs fois
                    pixmap.setColor(Color.WHITE);
                    pixmap.fillCircle(i+5, j+5, 9);
                }
                //Pastille taille
                else if(niveauGris == 200){
                    listePastilles.add(new SizePastille(new Vector2(i/(masque.getWidth()/gameWorld.getWidth()),gameWorld.getHeight()-j/(masque.getHeight()/gameWorld.getHeight())),gameWorld));
                    pixmap.setColor(Color.WHITE);
                    pixmap.fillCircle(i+5, j+5, 9);
                }
                //Pastille temps
                else if(niveauGris == 225){
                    listePastilles.add(new TimePastille(new Vector2(i/(masque.getWidth()/gameWorld.getWidth()),gameWorld.getHeight()-j/(masque.getHeight()/gameWorld.getHeight())),gameWorld));
                    pixmap.setColor(Color.WHITE);
                    pixmap.fillCircle(i+5, j+5, 9);
                }
                //Vide
                else if(niveauGris == 255){

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
        if(couleurVoisin1 == 255 || couleurVoisin2 == 255 || couleurVoisin3 == 255 || couleurVoisin4 == 255){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(i/(pixmap.getWidth()/gameWorld.getWidth()),gameWorld.getHeight()-j/(pixmap.getHeight()/gameWorld.getHeight()));
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

        Texture piste = TextureFactory.getInstance().getTexturePiste();
        if (!piste.getTextureData().isPrepared()) {
            piste.getTextureData().prepare();
        }
        Pixmap pixmapPiste = piste.getTextureData().consumePixmap();
        int niveauGris;

        for(int i = 0 ; i < pixmapLaby.getWidth() ; i++) {
            for (int j = 0; j < pixmapLaby.getHeight(); j++) {
                niveauGris = pixmapLaby.getPixel(i, j) & 255;
                if(niveauGris == 255){
                    pixmapLaby.setColor(pixmapPiste.getPixel(i,j));
                    pixmapLaby.drawPixel(i, j);
                }
            }
        }
        decor = new Texture(pixmapLaby);
        pixmapLaby.dispose();
        pixmapPiste.dispose();
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

