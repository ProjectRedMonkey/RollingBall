package fr.ul.rollingball.models;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Pastille {
    private static int size;
    private GameWorld gameWorld;
    private Body body;
    private boolean picked;
}
