package com.icsgame.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icsgame.game.map.Tile;
import com.icsgame.objects.Bar;
import com.icsgame.screens.ScrGame;

import java.util.Random;

/* ======================== Enemy ================================
Basic generic enemy class
================================================================== */

public abstract class Enemy {

    protected ScrGame game;

    protected int nHealth;
    protected int nHealthMax;

    protected Rectangle rect;
    protected Texture txt;
    protected Vector2 vVel;
    protected float fSpeed;
    protected Bar bar;

    protected Random ranGen = new Random();

    public Enemy(ScrGame game, Texture txt, int w, int h, float fSpeed, int nHealth, int nHealthMax) {
        this.game = game;
        this.txt = txt;
        this.rect = new Rectangle(0, 0, w, h);
        this.fSpeed = fSpeed;
        this.nHealth = nHealth;
        this.nHealthMax = nHealthMax;

        vVel = new Vector2();
        bar = new Bar("extra/red.png", "extra/green.png", getX(), getY()+rect.getHeight()+5, 20,
                nHealth, nHealthMax, 4, false);

        spawnController();
    }

    public Enemy(ScrGame game, Texture txt, Rectangle rect, float fSpeed) {
        this.game = game;
        this.txt = txt;
        this.rect.set(rect);
        this.fSpeed = fSpeed;

        vVel = new Vector2();

        spawnController();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(txt, rect.x, rect.y, rect.width, rect.height);

        // Health Bar
        bar.update(getX(), getY()+rect.getHeight()+5, nHealth, nHealthMax);
        bar.render(batch);
        batch.end();
    }

    public boolean update() {
        aiController();

        // Update position
        setX(getX()+(vVel.x*fSpeed));
        setY(getY()+(vVel.y*fSpeed));

        return false;
    }

    protected void aiController() {
        vVel.set(game.getPlayer().getX()-getX(), game.getPlayer().getY()-getY());
        vVel.nor();
    }

    protected void spawnController() {
        do {
            setX(ranGen.nextInt(game.getMap().getMapW()));
            setY(ranGen.nextInt(game.getMap().getMapH()));
        } while (!canSpawn(game.getMap().getTiles()));
    }

    protected boolean canSpawn(Tile[][] tiles) {
        // Check if the enemy is close to the player
        Circle playerNoSpawnArea = new Circle();
        playerNoSpawnArea.set(game.getPlayer().getCenterPosition(), game.nNoSpawnPlayerRadius);

        if(Intersector.overlaps(playerNoSpawnArea, rect)) {
            return false;
        }

        // Check if it is colliding with the map
        for (int x = 0; x < tiles.length; x++){
            for (int y = 0; y < tiles.length; y++){
                if(game.getRectCollision().isColliding(tiles[x][y].getRect(), rect) && tiles[x][y].getType() != 1){
                    return false;
                }
            }
        }
        return true;
    }


    public void setX(float x) {
        rect.setX(x);
    }

    public void setY(float y) {
        rect.setY(y);
    }

    public float getX() { return rect.getX(); }

    public float getY() { return rect.getY(); }

    public Rectangle getRect() { return rect; }

    public Vector2 getVel() { return vVel; }

    public float getSpeed() { return fSpeed; }
}
