package com.worms.projectiles;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.Worm;

import static com.worms.utils.Constants.E_RADIUS_BULLET;

public class Bullet extends Projectile{
	private static float width = 6f;
	private static float height = 6f;
	/**
	 * Instantiates a new bullet.
	 *
	 * @param world the world
	 * @param p the p
	 */
	public Bullet(World world, Worm p) {
		super( E_RADIUS_BULLET , world, p, 15f, width, height);
		// TODO Auto-generated constructor stub
	}
	

	/* (non-Javadoc)
	 * @see com.worms.projectiles.Projectile#shoot(com.badlogic.gdx.math.Vector2, float, float)
	 */
	public void shoot(float force, float angle, Body b){
		super.shoot( 100, angle, b);
		super.getBody().setGravityScale(0);
	}
}
