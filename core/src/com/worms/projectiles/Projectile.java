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
	
	public Projectile( float xplRadius, World world, Texture tex, Texture tex2, Player p, float damage){
		this.projectileTex = tex;
		this.explosionTex = tex2;
		this.world = world;
		this.xplRadius = xplRadius;
		this.damage = damage;
		playerWhoThrewIt = p;
		isFlaggedForDeletion = false;
	}
	
	
	public float getX(){
		return body.getPosition().x * PPM - projectileTex.getWidth() / 2;
	}
	
	public float getY(){
		return body.getPosition().y * PPM - projectileTex.getHeight() / 2;
	}
	
	public Texture getTex(){
		return projectileTex;
	}
	
	protected Player getPlayerWhoThrewIt(){
		return playerWhoThrewIt;
	}
	
	protected void setBody(Body b){
		body = b;
	}
	
	protected void flagForDeletion(){
		isFlaggedForDeletion = false;
	}
	
	public Body getBody(){
		return body;
	}
	
	public void shoot(Vector2 pos, float force, float angle){
		isFlaggedForDeletion = false;
		System.out.println("Fueza: " + force + " Angulo: " + angle);
		body = BodyCreators.createBox( pos.x * PPM, pos.y * PPM, projectileTex.getWidth(), projectileTex.getHeight(), false, true, world, (short) BIT_PROJECTILE, (short) (BIT_PLAYER | BIT_WALL), (short) 0 , this);
		Vector2 f = new Vector2( force * MathUtils.cos(angle * MathUtils.degreesToRadians), force * MathUtils.sin(angle * MathUtils.degreesToRadians) );
		body.applyForceToCenter( f , true);
	}
	
	public void explode( ){
		new Explosion(xplRadius, damage, body.getPosition(), world, explosionTex);
		isFlaggedForDeletion = true;
	}
	
	public boolean getDeletionFlag(){
		return isFlaggedForDeletion;
	}
	
	public void dispose(){
		projectileTex.dispose();
	}
}
