package fr.ul.rollingball.models.balls;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class Ball3D extends Ball {
    private ModelBatch modelBatch = new ModelBatch();
    private ModelInstance boule3D;
    private Environment envnt = new Environment();
    private OrthographicCamera cam;
    private Quaternion rotation;
    private Timer.Task decompte;
    private int temps = 0;


    /**
     * Crée une bille en 3D
     *
     * @param gameWorld monde de la bille
     * @param position  position de la bille
     */
    public Ball3D(GameWorld gameWorld, Vector2 position, Ball2D ball, OrthographicCamera camera) {
        super(gameWorld, position);
        modelBatch = new ModelBatch();

        cam = camera;
        cam.update();

        ModelLoader loader = new ObjLoader();
        Model modele = loader.loadModel(Gdx.files.internal("models/sphere.obj"));
        boule3D = new ModelInstance(modele);

        //Lumière d’ambiance (blanche avec intensité moyenne)
        ColorAttribute ambiance = new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f);
        // Lumière spéculaire verte(réflection de la lumière sur l’objet)
        ColorAttribute speculaire= new ColorAttribute(ColorAttribute.Specular, 0.0f, 0.7f, 0.0f, 1f);
        // Affectation des attributs à l’environnement
        envnt.set(ambiance, speculaire);
        // Ajout d’une lumière directionnelle (couleur + direction)
        envnt.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, 1f, -0.2f));

        rotation = new Quaternion();

        Timer timer = new Timer();
        decompte = new Timer.Task() {
            @Override
            public void run() {
                countDown();
            }
        };
        timer.scheduleTask(decompte, 1f, 1f);
    }

    public void draw(SpriteBatch spriteBatch){
        cam.update();
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        Quaternion rotLoc= new Quaternion();
        rotLoc.set(new Vector3(0, 0, 0).nor(), new Vector3(getPosition().x, getPosition().y, 0).len() * temps/ getRayon());
        rotation.mulLeft(rotLoc);
        boule3D.transform.set(new Vector3(getPosition().x, getPosition().y, 0) , rotation);
        modelBatch.render(boule3D, envnt);
        modelBatch.end();

    }

    private void countDown(){
        temps++;
    }
}
