package fr.ul.rollingball.models.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.ul.rollingball.models.GameWorld;

public class Ball3D extends Ball {
    private ModelInstance boule3D;
    private Environment envnt = new Environment();
    private float ancienY, ancienX;


    /**
     * Crée une bille en 3D
     *
     * @param gameWorld monde de la bille
     * @param position  position de la bille
     */
    public Ball3D(GameWorld gameWorld, Vector2 position) {
        super(gameWorld, position);

        ModelLoader loader = new ObjLoader();
        Model modele = loader.loadModel(Gdx.files.internal("models/sphere.obj"));
        boule3D = new ModelInstance(modele,position.x, position.y ,-50.f);

        //Lumière d’ambiance (blanche avec intensité moyenne)
        ColorAttribute ambiance = new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f);
        // Lumière spéculaire verte(réflection de la lumière sur l’objet)
        ColorAttribute speculaire= new ColorAttribute(ColorAttribute.Specular, 0.0f, 0.7f, 0.0f, 1f);
        // Affectation des attributs à l’environnement
        envnt.set(ambiance, speculaire);
        // Ajout d’une lumière directionnelle (couleur + direction)
        envnt.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, 1f, -0.2f));

        boule3D.transform.scale(
                1f + getRayon()/2,
                1f + getRayon()/2,
                1f + getRayon()/2
        );
        ancienY = getPosition().y;
        ancienX = getPosition().x;
    }

    /**
     * Affiche la ball3D
     * @param modelBatch sur lequel l'afficher
     */
    public void draw(ModelBatch modelBatch){
        Gdx.gl.glClear( GL20.GL_DEPTH_BUFFER_BIT);
        boule3D.transform.trn( getPosition().x-ancienX,getPosition().y-ancienY,0.0f);
        Vector3 position = boule3D.transform.getTranslation(new Vector3());
        ancienX = position.x;
        ancienY = position.y;

        boule3D.transform.rotate(
                new Vector3(1f * getBody().getLinearVelocity().x,0f,0f),
                getBody().getLinearVelocity().x
        );
        boule3D.transform.rotate(
                new Vector3(0.0f,1f * getBody().getLinearVelocity().y,0.0f),
                getBody().getLinearVelocity().y
        );

        modelBatch.render(boule3D, envnt);

    }
}
