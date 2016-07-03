package com.worms.projectiles;

import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;
import com.worms.game.Worm;

import static com.worms.utils.Constants.BIT_PLAYER;
import static com.worms.utils.Constants.BIT_PROJECTILE;
import static com.worms.utils.Constants.BIT_WALL;
import static com.worms.utils.Constants.E_RADIUS_MISSILE;
import static com.worms.utils.Constants.PPM;

public class Missile extends Projectile{
	private static float width = 32f;
	private static float height = 64f;
	/**
	 * Instantiates a new missile.
	 *
	 * @param world the world
	 * @param p the p
	 */
	public Missile( Worm p) {
		super(E_RADIUS_MISSILE,  p, 45f, width, height);
	}
	
	/**
	 * Shoot.
	 *
	 * @param x the x
	 */
	public void shoot( float x, World world){
		super.flagForDeletion();
		super.setBody(BodyCreators.createBox( x * PPM, 900, width/4, height/4, false, true, world, (short) BIT_PROJECTILE, (short) (BIT_PLAYER | BIT_WALL), (short) 0 , this));
	}

}
