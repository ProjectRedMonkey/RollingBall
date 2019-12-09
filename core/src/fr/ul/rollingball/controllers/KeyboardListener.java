package fr.ul.rollingball.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardListener implements InputProcessor {
    private static int coeff = 200;
    private boolean quit;
    private boolean debug;
    private Vector2 acceleration;
    private boolean ball;

    //Permet de gérer les touches via clavier (uniquement pour la version Desktop)
    public KeyboardListener(){
        debug = false;
        quit =  false;
        acceleration = new Vector2(0, 0);
        ball = true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.Q:
                Gdx.app.exit();
                break;
            case Input.Keys.RIGHT:
                acceleration.x += coeff;
                break;
            case Input.Keys.LEFT:
                acceleration.x -= coeff;
                break;
            case Input.Keys.UP:
                acceleration.y += coeff;
                break;
            case Input.Keys.DOWN:
                acceleration.y -= coeff;
                break;
            case Input.Keys.B:
                if(ball){
                    ball = false;
                }else{
                    ball = true;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
                if (debug) {
                    debug = false;
                } else {
                    debug = true;
                }
                break;
            case Input.Keys.RIGHT:
                acceleration.x = 0;
                break;
            case Input.Keys.LEFT:
                acceleration.x = 0;
                break;
            case Input.Keys.UP:
                acceleration.y = 0;
                break;
            case Input.Keys.DOWN:
                acceleration.y = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int getCoeff() {
        return coeff;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isQuit() {
        return quit;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void resetAcceleration(){
        acceleration = new Vector2(0, 0);
    }

    public boolean isBall2D() {
        return ball;
    }
}
