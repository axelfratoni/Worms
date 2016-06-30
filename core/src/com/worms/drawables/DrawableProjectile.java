package com.worms.drawables;

import com.badlogic.gdx.math.Vector2;
import com.worms.projectiles.Bullet;
import com.worms.projectiles.Grenade;
import com.worms.projectiles.Projectile;

public class DrawableProjectile {
	
	private Vector2 pos;
	private float angle;
	private String typeOfProjectile;
	/**
	 * Instantiates a new drawable projectile.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 */
	public DrawableProjectile(Vector2 pos, Projectile p,float angle){
		this.pos = pos;
		this.angle = angle;
		if ( p instanceof Bullet){
			typeOfProjectile = "Bullet.png";
		} else if ( p instanceof Grenade){
			typeOfProjectile = "Grenade.png";
		} else {
			typeOfProjectile = "Missile.png";
		}
	}
	


	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos() {
		return pos;
	}
	
	public String getTypeOfProjectile(){
		return typeOfProjectile;
	}
	
	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return angle;
	}
	
}
