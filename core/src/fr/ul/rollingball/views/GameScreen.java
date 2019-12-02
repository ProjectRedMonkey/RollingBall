package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;
import fr.ul.rollingball.models.Maze;
import fr.ul.rollingball.models.balls.Ball2D;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch affichageJeu;
    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private GameState gameState;
    private static int dureeDuJeu;
    private GameState.etat etatDuJeu;
    private SpriteBatch affichageScore;
    private OrthographicCamera cameraTexte;
    private Texture texture;
    private String textPastilles;

    private FreeTypeFontGenerator fontGen;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontCarac;
    private BitmapFont police;


    /**
     * Ecran de jeu
     */
    public GameScreen(){
        affichageJeu = new SpriteBatch();
        gameWorld = new GameWorld(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWorld.getWidth(),gameWorld.getHeight());
        camera.update();

        gameState = new GameState();
        etatDuJeu = gameState.getEtatActuel();
        dureeDuJeu = gameState.getTempsDispo();
        affichageScore =  new SpriteBatch();
        cameraTexte = new OrthographicCamera();
        cameraTexte.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraTexte.update();

        fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        fontCarac = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontCarac.size = 50;
        fontCarac.color = new Color(1,1,0,0.75f);
        fontCarac.borderColor = Color.BLACK;
        fontCarac.borderWidth = 0.5f;

        police = new BitmapFont();
        police = fontGen.generateFont(fontCarac);
        fontGen.dispose();
    }

    /**
     * Affiche l'écran de jeu
     */
    @Override
    public void render (float delta) {
        update();
        affichageJeu.setProjectionMatrix(camera.combined);

        if(gameState.isInProgress()) {
            gameWorld.draw(affichageJeu);
            affichageScore.setProjectionMatrix(cameraTexte.combined);
            affichageScore.begin();
            police.draw(affichageScore,"Score : "+gameState.getScore(),Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
            police.draw(affichageScore,"Temps : "+gameState.getTempsRestant(), Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight());
            affichageScore.end();
        }else if (gameState.isStop()){
            Gdx.app.exit();
        }else{
            changeLaby();
            //affichageJeu.setProjectionMatrix(camera.combined);
            //affichageJeu.draw(texture, 30, 30);
            //police.draw(affichageJeu, textPastilles, 20, 20);
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
            //gameState.setState(GameState.etat.victoire);
            gameState.setState(GameState.etat.victoire);
        }
    }


    public void reset(){
        gameWorld.setMaze(new Maze(gameWorld));
        gameWorld.getMaze().loadLaby(gameWorld.getListePastilles());
        resetScore();
        ajouterTemps(dureeDuJeu);
        gameWorld.setBall2D(new Ball2D(gameWorld, gameWorld.getMaze().getPositionInitialeBille()));
        gameState.setState(GameState.etat.enCours);
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
        return gameState.getEtatActuel();
    }

    public void ajouterTemps(int sec){
        gameState.setTempsRestant(gameState.getTempsRestant()+sec);
    }

    public void augmenterScore(){
        gameState.setScore(gameState.getScore()+1);
        augmenterPastillesAvalees();
    }

    public void augmenterPastillesAvalees(){
        gameState.setNbPastillesAvalees(gameState.getNbPastillesAvalees()+1);
    }

    public void resetScore(){
        gameState.setScore(0);
    }

    @Override
    public void show() {
        super.show();
        gameState.setState(GameState.etat.enCours);
    }

    public void changeLaby(){
        if(gameState.isVictory()){
            texture = TextureFactory.getInstance().getVictoire();
            textPastilles = new String("Pastilles avalées : "+gameState.getNbPastillesAvalees());
            ajouterTemps(dureeDuJeu);
            SoundFactory.getInstance().playVictoire(20);
            gameWorld.getMaze().changeLaby(gameWorld.getListePastilles());
            gameState.setState(GameState.etat.enCours);
        }else{
            texture = TextureFactory.getInstance().getPerdu();
            textPastilles = new String("Vous pouvez réessayer !");
            reset();
        }
    }
}