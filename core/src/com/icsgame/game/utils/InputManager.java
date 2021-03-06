package com.icsgame.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.icsgame.screens.ScrGame;

/* ======================== InputManager ================================
Controls all the inputs into the game
====================================================================== */

public class InputManager {

    ScrGame game;

    public InputManager(ScrGame _game){
        game = _game;
    }

    public boolean handleInput(){
        if(!esc()) { // True means the game is stopping
            wasd();
            click();
            keyboard();
            return false;
        }
        return true;
    }

    private void click(){
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            game.getPlayer().useWeapon();
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            game.getPlayer().useMelee();
        }
    }

    private void wasd(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            game.getPlayer().addVel(0f, 0.5f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            game.getPlayer().addVel(-0.5f, 0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            game.getPlayer().addVel(0f, -0.5f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            game.getPlayer().addVel(0.5f, 0f);
        }
    }

    private void keyboard(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            game.getPlayer().reloadWeapon();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            game.getPlayer().nextWeapon();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            game.spawnEnemy();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Y)){
            game.increaseCombo();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            game.getSoundEngine().nextSong();
        }
    }

    private boolean esc(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            return true;
        }
        return false;
    }
}
