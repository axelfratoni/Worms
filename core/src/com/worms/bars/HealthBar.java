package com.worms.bars;

import com.badlogic.gdx.physics.box2d.World;

public class HealthBar extends Bar {

	
	/**
	 * Instantiates a new health bar.
	 *
	 * @param x the x
	 * @param y the y
	 * @param world the world
	 */
	public HealthBar(float x, float y, World world) {
		super(x, y, world, 0, 1, 0, 1, 0, 0);
		// TODO Auto-generated constructor stub
	}
	
}
