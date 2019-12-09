package fr.ul.rollingball.models.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import fr.ul.rollingball.models.GameWorld;

public class Ball3D extends Ball {
    private ModelInstance instance;
    private ModelBatch modelBatch = new ModelBatch();
    public AssetManager assets;
    public boolean loading;
    public Array<ModelInstance> instances = new Array<ModelInstance>();


    /**
     * Crée une bille en 3D
     *
     * @param gameWorld monde de la bille
     * @param position  position de la bille
     */
    public Ball3D(GameWorld gameWorld, Vector2 position) {
        super(gameWorld, position);
        modelBatch = new ModelBatch();

        assets = new AssetManager();
        assets.load("models/sphere.obj", Model.class);
        loading = true;
    }

    private void doneLoading() {
        Model bille = assets.get("models/sphere.obj", Model.class);
        ModelInstance billeInstance = new ModelInstance(bille);
        instances.add(billeInstance);
        loading = false;
    }

    /**
     * Dessine la boule
     */
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera){
        if (loading && assets.update()) {
            doneLoading();
        }
        //camera.update();

        Gdx.gl.glViewport(0, 0, 1024, 720);

        modelBatch.begin(camera);
        modelBatch.render(instances);
        modelBatch.end();
    }
}
