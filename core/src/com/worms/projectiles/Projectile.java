package com.worms.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;
import com.worms.game.Player;

import static com.worms.utils.Constants.*;

public class Projectile {
	private Texture projectileTex, explosionTex;
	private Body body;
	private World world;
	private float xplRadius;
	private float damage;
	private Player playerWhoThrewIt;
	private boolean isFlaggedForDeletion;
	
	/**
	 * Instantiates a new projectile.
	 *
	 * @param xplRadius the xpl radius
	 * @param world the world
	 * @param tex the tex
	 * @param tex2 the tex2
	 * @param p the p
	 * @param damage the damage
	 */
	public Projectile( float xplRadius, World world, Texture tex, Texture tex2, Player p, float damage){
		this.projectileTex = tex;
		this.explosionTex = tex2;
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
		return body.getPosition().x * PPM - projectileTex.getWidth() / 2;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public float getY(){
		return body.getPosition().y * PPM - projectileTex.getHeight() / 2;
	}
	
	/**
	 * Gets the tex.
	 *
	 * @return the tex
	 */
	public Texture getTex(){
		return projectileTex;
	}
	
	/**
	 * Gets the player who threw it.
	 *
	 * @return the player who threw it
	 */
	protected Player getPlayerWhoThrewIt(){
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
	
	/**
	 * Shoot.
	 *
	 * @param pos the pos
	 * @param force the force
	 * @param angle the angle
	 */
	public void shoot(Vector2 pos, float force, float angle){
		isFlaggedForDeletion = false;
		body = BodyCreators.createBox( pos.x * PPM, pos.y * PPM, projectileTex.getWidth(), projectileTex.getHeight(), false, true, world, (short) BIT_PROJECTILE, (short) (BIT_PLAYER | BIT_WALL), (short) 0 , this);
		Vector2 f = new Vector2( force * MathUtils.cos(angle * MathUtils.degreesToRadians), force * MathUtils.sin(angle * MathUtils.degreesToRadians) );
		body.applyForceToCenter( f , true);
	}
	
	/**
	 * Explode.
	 */
	public void explode( ){
		new Explosion(xplRadius, damage, body.getPosition(), world, explosionTex);
		isFlaggedForDeletion = true;
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
		projectileTex.dispose();
	}
}
