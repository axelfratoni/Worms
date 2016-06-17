package com.worms.bars;

import com.badlogic.gdx.physics.box2d.World;

public class MovementBar extends Bar {
	
	/**
	 * Instantiates a new movement bar.
	 *
	 * @param x the x
	 * @param y the y
	 * @param world the world
	 */
	public MovementBar(float x, float y, World world ){
		super( x, y, world, 0, 0, 1, 1, 1, 1);		
	}
	
}
