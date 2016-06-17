package com.worms.utils;

import java.io.Serializable;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

@SuppressWarnings("serial")
public class GrassTile extends Tile implements Serializable{
	
	public GrassTile(MapObject object, World world) {
		super( object, world, "Images/Grasstile.png");
	}
	
	
}
