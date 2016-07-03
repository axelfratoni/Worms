package com.worms.projectiles;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.worms.game.Worm;
import com.worms.game.Teams;
import com.worms.states.GameState;
import com.worms.utils.Tile;


public class Explosion {
	private float explRadius;
	private float damage;
	private Vector2 pos;
	private float time;

	/**
	 * Instantiates a new explosion.
	 *
	 * @param explRadius the expl radius
	 * @param damage the damage
	 * @param pos the pos
	 * @param world the world
	 */
	public Explosion(float explRadius, float damage, Vector2 pos) {
		GameState.setExplosion(this);
		
		this.damage = damage;
		this.explRadius = explRadius;
		this.pos = pos;
		time = 0;
		
		getObjectsInRange(pos.x-explRadius/2,pos.y-explRadius/2,pos.x+explRadius/2,pos.y+explRadius/2);
	}
	
	/**
	 * Update.
	 *
	 * @param delta the delta
	 * @return true, if successful
	 */
	public boolean update(float delta){
		time+=delta;

		if (time >= 1f){
			Teams.updateTurn();
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the objects in range.
	 *
	 * @param x the x
	 * @param y the y
	 * @param x2 the x2
	 * @param y2 the y2
	 * @return the objects in range
	 */
	public void getObjectsInRange(float x, float y, float x2, float y2) {
		final List<Fixture> bodiesFound = GameState.getObjectsInRange(x, y, x2, y2);
		
		for(Fixture fixture : bodiesFound){
			if(fixture.getUserData() instanceof Worm){
				Worm p;
				p = (Worm) fixture.getUserData();
				if ( getDistance(p.getPlayer().getPosition(), pos) <= explRadius){
					p.seppuku(damage);
					applyForce(p);
				}
			} else if (fixture.getUserData() instanceof Tile){
				Tile t;
				t = (Tile) fixture.getUserData();
		
				if ( getDistance(t.getTile().getPosition(), pos) <= explRadius){
					t.flagForDeletion();
				}
			}
		}
	}
	
	/**
	 * Apply force.
	 *
	 * @param player the player
	 */
	public void applyForce(Worm player){
		float playPosX = player.getPlayer().getPosition().x;
		float playPosY = player.getPlayer().getPosition().y;
		
		float dist = getDistance(player.getPlayer().getPosition(), pos);
		float angle = MathUtils.atan2(playPosY - pos.y, playPosX - pos.x);
		float force = (1000 - 1000 * dist / explRadius);
		
		float Xforce = force * MathUtils.cos(angle);
		float Yforce = force * MathUtils.sin( angle);
		
		if(Yforce < 200) 
			Yforce = 200;
		
		player.getPlayer().applyForceToCenter( Xforce , Yforce, false);
	}
	
	
	/**
	 * Gets the expl radius.
	 *
	 * @return the expl radius
	 */
	public float getExplRadius() {
		return explRadius;
	}

	/**
	 * Gets the damage.
	 *
	 * @return the damage
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos() {
		return pos;
	}

	
	/**
	 * Dispose.
	 */
	public void dispose(){
	}
	
	/**
	 * Gets the distance.
	 *
	 * @param pos1 the pos1
	 * @param pos2 the pos2
	 * @return the distance
	 */
	private float getDistance( Vector2 pos1, Vector2 pos2){
		return (float) Math.sqrt( Math.pow(pos1.x - pos2.x,2) + Math.pow( pos1.y - pos2.y,2) ) ; 
	}

	
}
