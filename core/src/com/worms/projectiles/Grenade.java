package com.worms.projectiles;

import com.worms.game.Worm;

import static com.worms.utils.Constants.*;

public class Grenade extends Projectile{
	
	private static float width = 16f;
	private static float height = 16f;
	
	/**
	 * Instantiates a new grenade.
	 *
	 * @param world the world
	 * @param p the p
	 */
	public Grenade( Worm p) {
		super( E_RADIUS_GRENADE,  p, 30f, width, height);
		// TODO Auto-generated constructor stub
	}
	
}
