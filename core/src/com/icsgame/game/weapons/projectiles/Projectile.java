package com.icsgame.game.weapons.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/* ====================== Projectile =========================
Abstract
Extends Sprite
Objects that fly across the map
Fired from weapon
Is in an array in the ScrGame

Method:         Type:       Mandate:
Projectile();   constructor constructs Projectile
update();       boolean     update movement, calls shouldKill
shouldKill();   boolean     abstract, true means the Projectile should be killed
render();       void        render Projectile
dispose();      void        disposes Projectiles assets
getRect();      Rectangle   returns rect
setX();         void        updates X coordinate
setY();         void        updates Y coordinate
=========================================================== */

public abstract class Projectile extends Sprite {

    protected int nDamage; // The damage done by the Projectile
    protected float fSpeed; // The Speed of the Projectile

    protected Vector2 vVel; // The direction of the Projectile

    protected Rectangle rect; // Rectangle of Projectile for collision detection

    protected Projectile(Texture txt, Rectangle rect, Vector2 vVel, int nDamage, float fSpeed) {
        super(txt, (int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
        setRegion(txt);
        setRotation(vVel.angle()-90);

        this.rect = rect;
        this.vVel = new Vector2();
        this.vVel.set(vVel);
        this.fSpeed = fSpeed;
        this. nDamage = nDamage;
    }

    public boolean update() {
        setX(getX() + (vVel.x * fSpeed));
        setY(getY() + (vVel.y * fSpeed));

        return shouldKill();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        draw(batch);
        batch.end();
    }

    protected abstract boolean shouldKill();

    public void dispose() {
        getTexture().dispose();
        rect = null;
        vVel = null;
    }

    public Rectangle getRect() {
        return rect;
    }

    @Override
    public void setX(float x) {
        rect.setX(x);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        rect.setY(y);
        super.setY(y);
    }

    public void reverseDirection() {
        vVel.set(vVel.x*(-1), vVel.y*(-1));
    }
}