package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch affichageJeu;
    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private GameState gameState;
    private static int dureeDuJeu;
    private GameState.etat etatDuJeu;
    private SpriteBatch affichageScore;
    private OrthographicCamera cameraTexte;
    private BitmapFont police;
    private Timer.Task task;

    /**
     * Ecran de jeu
     */
    public GameScreen(){
        affichageJeu = new SpriteBatch();
        gameWorld = new GameWorld();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWorld.getWidth(),gameWorld.getHeight());
        camera.update();
        gameState = new GameState();
        etatDuJeu = gameState.getEtatActuel();
        affichageScore =  new SpriteBatch();
        task = new Timer.Task() {
            @Override
            public void run() {
                //changeLaby();
            }
        };
        cameraTexte = new OrthographicCamera();
        cameraTexte.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraTexte.update();
        police = new BitmapFont();
    }

    /**
     * Affiche l'écran de jeu
     */
    @Override
    public void render (float delta) {
        update();
        affichageJeu.setProjectionMatrix(camera.combined);
        affichageScore.setProjectionMatrix(cameraTexte.combined);
        if(gameState.isInProgress()) {
            gameWorld.draw(affichageJeu);
        }else if (gameState.isInProgress()){
            Gdx.app.exit();
        }else{
            changeLaby();
        }

        /*
        //Utilisé pour voir la hitbox des bodies
        Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.render(gameWorld.getWorld(), camera.combined);
        
         */
    }

    /**
     * Actualise graphiquement le monde, appelé dans GameScreen avant l'affichage
     */
    public void update(){
        Vector2 force = new Vector2((Gdx.input.getAccelerometerY()*5f),-(Gdx.input.getAccelerometerX()*5f));
        gameWorld.getBall2D().applyForce(force);
        gameWorld.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
        gameWorld.updatePastilles();
        if (gameWorld.isVictory()){
            gameState.setState(GameState.etat.victoire);
            //gameWorld.getMaze().changeLaby(gameWorld.getListePastilles());
        }
    }

    /**
     * Efface l'écran de jeu
     */
    public void dispose(){
        affichageJeu.dispose();
        police.dispose();
        affichageScore.dispose();
    }

    public GameState.etat getEtatDuJeu() {
        return etatDuJeu;
    }

    public void ajouterTemps(int sec){
        gameState.setTempsRestant(gameState.getTempsRestant()+sec);
    }

    public void augmenterScore(){
        gameState.setScore(gameState.getScore()+1);
    }

    public void augmenterPastillesAvalees(){
        gameState.setNbPastillesAvalees(gameState.getNbPastillesAvalees()+1);
    }

    @Override
    public void show() {
        super.show();
        gameState.setState(GameState.etat.enCours);
    }

    public void changeLaby(){
        affichageScore.begin();
        Texture texture;
        String textPastilles;
        if(gameState.isVictory()){
            texture = TextureFactory.getInstance().getVictoire();
            textPastilles = new String("Pastilles avalées : "+gameState.getNbPastillesAvalees());
        }else{
            texture = TextureFactory.getInstance().getPerdu();
            textPastilles = new String("Vous pouvez réessayer !");
        }
        affichageScore.draw(texture, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 );
        police.draw(affichageScore, textPastilles, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        affichageScore.end();
    }
}