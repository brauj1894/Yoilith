package com.icsgame.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.icsgame.game.weapons.Gun;
import com.icsgame.screens.ScrGame;

public class Basic extends Enemy {

    Gun gun;

    public Basic(ScrGame game, Texture txt, int w, int h, float fStrength) {
        super(game, txt, w, h, 2, 30, 30, fStrength);
        gun = new Gun(game, this);
        gun.loadType("smg");
        gun.scaleDamage(fStrength);
        weapon = gun;
    }
}