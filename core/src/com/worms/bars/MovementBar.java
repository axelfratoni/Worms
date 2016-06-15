package com.worms.bars;

import com.badlogic.gdx.physics.box2d.World;

public class MovementBar extends Bar {
	
	public MovementBar(float x, float y, World world ){
		super( x, y, world, 0, 0, 1, 1, 1, 1);		
	}
	
}
