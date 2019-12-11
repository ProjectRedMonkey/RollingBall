package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.controllers.GestureListener;
import fr.ul.rollingball.controllers.KeyboardListener;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;
import fr.ul.rollingball.models.Maze;
import fr.ul.rollingball.models.balls.Ball2D;
import fr.ul.rollingball.models.pastilles.Pastille;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch affichageJeu;
    private GameWorld gameWorld;
    private KeyboardListener keyboardListener;
    private GestureListener gestureListener;
    private OrthographicCamera camera;
    private GameState gameState;
    private static int dureeDuJeu;
    private SpriteBatch affichageScore;
    private OrthographicCamera cameraTexte;
    private boolean createdTimer = false;
    private Texture texture;
    private String textPastilles;
    private Vector2 acc;

    private FreeTypeFontGenerator fontGen;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontCarac;
    private BitmapFont police;


    /**
     * Ecran principal du jeu
     */
    public GameScreen(){
        SoundFactory.getInstance().playFond(15);
        affichageJeu = new SpriteBatch();
        gameWorld = new GameWorld(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWorld.getWidth(),gameWorld.getHeight());
        camera.update();

        gameState = new GameState();
        dureeDuJeu = gameState.getTempsDispo();
        affichageScore =  new SpriteBatch();
        cameraTexte = new OrthographicCamera();
        cameraTexte.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraTexte.update();

        fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        fontCarac = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontCarac.size = 40;
        fontCarac.color = new Color(1,1,0,0.75f);
        fontCarac.borderColor = Color.BLACK;
        fontCarac.borderWidth = 0.5f;

        police = new BitmapFont();
        police = fontGen.generateFont(fontCarac);
        fontGen.dispose();

        keyboardListener = new KeyboardListener();
        gestureListener = new GestureListener();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(keyboardListener);
        GestureDetector gestureDetector = new GestureDetector(gestureListener);
        inputMultiplexer.addProcessor(gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Affiche l'écran de jeu
     */
    @Override
    public void render(float delta) {
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
            gameWorld.draw(affichageJeu);
            if(gameState.isVictory()) {
                texture = TextureFactory.getInstance().getVictoire();
                textPastilles = "Pastilles avalées : "+gameState.getNbPastillesAvalees();
            }else if(gameState.isLost()){
                texture = TextureFactory.getInstance().getPerdu();
                textPastilles = new String("Vous pouvez réessayer !");
            }

            affichageScore.setProjectionMatrix(cameraTexte.combined);
            affichageScore.begin();
            affichageScore.draw(texture,Gdx.graphics.getWidth()/2f-texture.getWidth()/2f, Gdx.graphics.getHeight()/2f-texture.getHeight()/2f);
            police.draw(affichageScore, textPastilles, Gdx.graphics.getWidth()/2-200, Gdx.graphics.getHeight()/3);
            affichageScore.end();
            if(!createdTimer) {
                createdTimer = true;
                if(gameState.isVictory()) {
                    SoundFactory.getInstance().playVictoire(20);
                }else{
                    SoundFactory.getInstance().playDefaite(20);
                }
                Timer timer = new Timer();
                Timer.Task task = new Timer.Task() {
                    @Override
                    public void run() {
                        changeLaby();
                        gameState.setState(GameState.etat.enCours);
                        createdTimer = false;
                    }
                };
                timer.scheduleTask(task, 3);
            }
        }


        //Utilisé pour voir la hitbox des bodies
        if(keyboardListener.isDebug()) {
            Gdx.gl.glClearColor(0.20f, 0.20f, 0.20f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
            box2DDebugRenderer.render(gameWorld.getWorld(), camera.combined);
        }
    }

    /**
     * Actualise graphiquement le monde, appelé dans GameScreen avant l'affichage
     */
    public void update(){
        acc = new Vector2(gestureListener.getAcceleration());
        //Si on est sur téléphone
        if(Gdx.input.isPeripheralAvailable( Input.Peripheral.Accelerometer )) {
            gameWorld.getBall2D().applyForce(new Vector2(Gdx.input.getAccelerometerY()*100f+acc.x,
                    -(Gdx.input.getAccelerometerX()*100f)+acc.y));
            gameWorld.setBall(gestureListener.isBall());
        }else {
            gameWorld.getBall2D().applyForce(new Vector2(keyboardListener.getAcceleration().x + acc.x, keyboardListener.getAcceleration().y + acc.y));
            gameWorld.setBall(keyboardListener.isBall2D());
        }
        gameWorld.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
        gameWorld.updatePastilles();
        if (gameWorld.isVictory()){
            gameState.setState(GameState.etat.victoire);
        }
    }


    /**
     * Relance le jeu à zéro
     */
    public void reset(){
        for (Pastille pastille:gameWorld.getListePastilles()) {
            gameWorld.getWorld().destroyBody(pastille.getBody());
        }
        for (Body brique: gameWorld.getMaze().getListeBriques()) {
            gameWorld.getWorld().destroyBody(brique);
        }
        gameWorld.getMaze().getListeBriques().clear();
        gameWorld.getListePastilles().clear();
        gameWorld.setMaze(new Maze(gameWorld));
        gameWorld.getWorld().destroyBody(gameWorld.getBall2D().getBody());
        gameWorld.getMaze().loadLaby(gameWorld.getListePastilles());
        resetScore();
        ajouterTemps(dureeDuJeu);
        gameWorld.setBall2D(new Ball2D(gameWorld, gameWorld.getMaze().getPositionInitialeBille()));
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

    /**
     * Ajoute une portion de temps au temps restant
     * @param sec temps à ajouter
     */
    public void ajouterTemps(int sec){
        gameState.setTempsRestant(gameState.getTempsRestant()+sec);
    }

    /**
     * Augmente le score de 1
     */
    public void augmenterScore(){
        gameState.setScore(gameState.getScore()+1);
        augmenterPastillesAvalees();
    }

    public void augmenterPastillesAvalees(){
        gameState.setNbPastillesAvalees(gameState.getNbPastillesAvalees()+1);
    }

    /**
     * Remet le score à 0
     */
    public void resetScore(){
        gameState.setScore(0);
    }


    /**
     * Préviens que l'écran de jeu se lance
     */
    @Override
    public void show() {
        super.show();
        gameState.setState(GameState.etat.enCours);
    }

    /**
     * Change de labyrinthe
     */
    public void changeLaby(){
        keyboardListener.resetAcceleration();
        if(gameState.isVictory()){
            if(gameWorld.estJeuFini()){
                gameWorld.setJeuFini(false);
                reset();
            }else {
                ajouterTemps(20);
                gameWorld.getMaze().changeLaby(gameWorld.getListePastilles());
            }
        }else{
            reset();
        }
    }

    public GestureListener getGestureListener() {
        return gestureListener;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}