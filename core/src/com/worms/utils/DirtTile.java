package com.worms.utils;

import java.io.Serializable;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

@SuppressWarnings("serial")
public class DirtTile extends Tile implements Serializable {

	public DirtTile(MapObject object, World world) {
		super(object, world, "Images/Dirttile.png");
	}

}
