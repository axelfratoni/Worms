package com.worms.projectiles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.Worm;
import com.worms.states.GameState;

import static com.worms.utils.Constants.*;

public class Projectile {
	private Body body;
	private World world;
	private float xplRadius;
	private float damage;
	private Worm playerWhoThrewIt;
	private boolean isFlaggedForDeletion;
	private float width;
	private float height;
	/**
	 * Instantiates a new projectile.
	 *
	 * @param xplRadius the xpl radius
	 * @param world the world
	 * @param p the p
	 * @param damage the damage
	 */
	public Projectile( float xplRadius, World world, Worm p, float damage, float w, float h){
		width = w;
		height = h;
		this.world = world;
		this.xplRadius = xplRadius;
		this.damage = damage;
		playerWhoThrewIt = p;
		isFlaggedForDeletion = false;
	}
	
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public float getX(){
		return body.getPosition().x * PPM - width / 2;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public float getY(){
		return body.getPosition().y * PPM - height / 2;
	}
	
	/**
	 * Gets the player who threw it.
	 *
	 * @return the player who threw it
	 */
	protected Worm getPlayerWhoThrewIt(){
		return playerWhoThrewIt;
	}
	
	/**
	 * Sets the body.
	 *
	 * @param b the new body
	 */
	protected void setBody(Body b){
		body = b;
	}
	
	/**
	 * Flag for deletion.
	 */
	protected void flagForDeletion(){
		isFlaggedForDeletion = false;
	}
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public Body getBody(){
		return body;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	/**
	 * Shoot.
	 *
	 * @param pos the pos
	 * @param force the force
	 * @param angle the angle
	 */
	public void shoot( float force, float angle, Body b){
		isFlaggedForDeletion = false;
		body =  b;
		Vector2 f = new Vector2( force * MathUtils.cos(angle * MathUtils.degreesToRadians), force * MathUtils.sin(angle * MathUtils.degreesToRadians) );
		body.applyForceToCenter( f , true);
	}
	
	/**
	 * Explode.
	 */
	public void explode( ){
		GameState.getBodiesToBeDeleted().add(getBody());
		new Explosion(xplRadius, damage, body.getPosition(), world);
		isFlaggedForDeletion = true;
	}
	
	public void touchedMapLimit(){
		if (body != null){
			GameState.getBodiesToBeDeleted().add(getBody());
		}
	}
	/**
	 * Gets the deletion flag.
	 *
	 * @return the deletion flag
	 */
	public boolean getDeletionFlag(){
		return isFlaggedForDeletion;
	}
	
	/**
	 * Dispose.
	 */
	public void dispose(){
	}
}
