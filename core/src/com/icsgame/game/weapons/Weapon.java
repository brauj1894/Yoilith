package com.icsgame.game.weapons;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icsgame.game.Player;
import com.icsgame.game.enemies.Enemy;
import com.icsgame.game.weapons.projectiles.Projectile;
import com.icsgame.screens.ScrGame;

import java.util.Random;

/* =========================== Weapon =============================
Abstract class that contains the need basics for any weapon
Extended in every weapon

Method:             Type:           Mandate:
update();           void            updates gun, update nCooldown
loadType(sType);    void            sets up weapon from .properties file
fire();             void            fires gun if you can
fireShot();         Projectile      configures the Projectile
reload();           void            reloads
getAmmo();          int             return nAmmo
getAmmoMax();       int             return nAmmoMax
================================================================ */

public abstract class Weapon {

    protected ScrGame game;
    protected Random ranGen;

    // Only one of the following
    protected boolean isPlayer; // True= player, False= enemy
    protected Player player;
    protected Enemy enemy;

    protected int nDamage; // The damage inflicted by this weapon
    protected int nSpray; // The amount of spray for the Projectile

    protected int nAngleRan; // The random angle for each Projectile
    protected Vector2 vVelRan; // The random velocity of the projectile
    protected Vector2 vVelTemp; // The temperary velocity of the projectile
    protected Rectangle rectProjectile; // The Starting location of the Projectile

    protected int nAmmo; // Current ammo count
    protected int nAmmoMax; // Maximum ammo

    protected boolean bCanFire; // True means gun can fire
    protected boolean bReloading; // True means it is reloading
    protected int nCooldown; // The number of ticks until the weapon can be fired again
    protected int nReload; // The number of ticks until the weapon is reloaded
    protected int nTickCount; // The current count in the cooldown

    protected int nShotsPerFire; // The number of shots for every time you fire

    protected String sType; // The name of the .properties file

    public Weapon() {
        ranGen = new Random();
        vVelRan = new Vector2();
        vVelTemp = new Vector2(1, 1);
        rectProjectile = new Rectangle();
        bCanFire = true;
        bReloading = false;
    }

    public void update() {
        if(!bCanFire){
            if(nTickCount >= nCooldown){
                nTickCount = 0;
                bCanFire = true;
            } else {
                nTickCount++;
            }
        } else {
            if (bReloading) {
                if(nTickCount >= nReload) {
                    nTickCount = 0;
                    nAmmo = nAmmoMax;
                    bReloading= false;
                    bCanFire = true;
                } else {
                    nTickCount++;
                }
            }
        }
    }

    public void fire() {
        if(bCanFire) {
            if (nAmmo > 0) {
                // Can Fire
                // Decrease nAmmo by 1
                nAmmo--;

                // Make it so you can't fire until cooldown is done
                bCanFire = false;

                // Get the correct angle and velocity vector
                if(isPlayer) {
                    vVelRan.set(player.getAngleHead());
                    // Create rect
                    rectProjectile.set((player.getHeadX()) +
                                    ((player.getHeadSize() / 2) * (float) Math.sin(Math.toRadians(90 - vVelRan.angle()))),
                            (player.getHeadY()) +
                                    ((player.getHeadSize() / 2) * (float) Math.cos(Math.toRadians(90 - vVelRan.angle()))),
                            100, 100);
                } else {
                    vVelRan.set((game.getPlayer().getX()+(game.getPlayer().getW()/2))-(enemy.getX()+(enemy.getRect().width/2)),
                            (game.getPlayer().getY()+(game.getPlayer().getH()/2))-(enemy.getY()+(enemy.getRect().height/2)));

                    // Create rect
                    rectProjectile.set(enemy.getX() + (enemy.getRect().width / 2),
                            (enemy.getY() + (enemy.getRect().height / 2)),
                            100, 100);
                }
                //System.out.println(vVelRan.angle());

                for (int i = 0; i < nShotsPerFire; i++) {
                    // Add spray
                    if(nSpray == 0) {
                        nAngleRan = 0;
                    } else {
                        nAngleRan = ranGen.nextInt(nSpray * 2) - nSpray;
                    }

                    vVelTemp.setAngle(nAngleRan + vVelRan.angle());

                    game.getProjectiles().add(fireShot());
                }
            }
        } else {
            if (!hasAmmo() && !bReloading) {
                reload();
            }
        }
    }

    protected abstract Projectile fireShot();

    public abstract void loadType(String sType);

    public void reload() {
        if (!bReloading) {
            bReloading = true;
            bCanFire = false;
            nAmmo = 0;
        }
    }

    public boolean isReloading() { return bReloading; }

    public int getAmmo() { return nAmmo; }

    public int getAmmoMax() { return nAmmoMax; }

    public boolean hasAmmo() { return nAmmo > 0;}

    public boolean canFire() {
        return bCanFire;
    }

    public void scaleDamage(float fSrength) {
        nDamage = (int)(nDamage * fSrength);
        nCooldown = (int)(nCooldown * Math.pow(fSrength, (-1)));
    }
}
