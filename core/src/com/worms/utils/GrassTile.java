package com.worms.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public class GrassTile extends Tile {
	
	public GrassTile(MapObject object, World world) {
		super( object, world, new Texture("Images/Grasstile.png"));
	}
	
	
}
