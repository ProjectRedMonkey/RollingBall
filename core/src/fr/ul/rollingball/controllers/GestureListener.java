package fr.ul.rollingball.controllers;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class GestureListener implements GestureDetector.GestureListener {
    private static int coeff = 5;
    private boolean gesture;
    private Vector2 acceleration;
    private boolean ball = true;
    private boolean mur = true;

    /**
     * Gère tous les mouvements de souris/doigt
     */
    public GestureListener(){
        acceleration = new Vector2();
        gesture = false;
    }

    public Vector2 getAcceleration() {
        if (gesture){
            gesture = false;
            return acceleration;
        }else{
            return new Vector2(0,0);
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(ball){
            ball = false;
        }else{
            ball = true;
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        if (mur) {
            mur = false;
        } else {
            mur = true;
        }
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        acceleration = new Vector2(velocityX*coeff, -velocityY*coeff);
        gesture = true;
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public boolean isBall() {
        return ball;
    }

    public boolean isMur() {
        return mur;
    }
}
