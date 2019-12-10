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
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameWorld;

public class Ball3D extends Ball implements ApplicationListener {
    private ModelInstance instance;
    private ModelBatch modelBatch = new ModelBatch();
    private ModelInstance boule3D;
    private Environment envnt = new Environment();
    private PerspectiveCamera cam;
    private CameraInputController camController;


    /**
     * Crée une bille en 3D
     *
     * @param gameWorld monde de la bille
     * @param position  position de la bille
     */
    public Ball3D(GameWorld gameWorld, Vector2 position) {
        super(gameWorld, position);
    }


    @Override
    public void create() {
        modelBatch = new ModelBatch();

        // Instance du modèle de la boule 3D
        ModelBuilder mb = new ModelBuilder();
        // Création d’un modèle de sphère avec une texture associée
        Model modele = mb.createSphere(2, 2, 2, 40, 40, new Material(TextureAttribute.createDiffuse(TextureFactory.getInstance().getTextureBille2D())),
                VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal|VertexAttributes.Usage.TextureCoordinates);
        boule3D = new ModelInstance(modele);

        cam = new PerspectiveCamera(67, 80, 60);
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);

        ModelLoader loader = new ObjLoader();
        modele = loader.loadModel(Gdx.files.internal("models/sphere.obj"));
        instance = new ModelInstance(modele);



        //Lumière d’ambiance (blanche avec intensitémoyenne)
        ColorAttribute ambiance = new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f);
        // Lumière spéculaire verte(réflection de la lumière sur l’objet)
        ColorAttribute speculaire= new ColorAttribute(ColorAttribute.Specular, 0.0f, 0.7f, 0.0f, 1f);
        // Affectation des attributs à l’environnement
        envnt.set(ambiance, speculaire);
        // Ajoutd’une lumière directionnelle(couleur+ direction)
        envnt.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, 1f, -0.2f));

        Quaternion rotation = new Quaternion();

        Quaternion rotLoc= new Quaternion();
        rotLoc.set(new Vector3(getPosition().x, getPosition().y, 0).nor(), 5f * 1f/ getRayon());
        rotation.mulLeft(rotLoc);

        boule3D.transform.set(new Vector3(getPosition().x, getPosition().y, 5) ,rotation);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        camController.update();
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(boule3D, envnt);
        modelBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
