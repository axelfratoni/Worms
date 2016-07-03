package com.worms.utils;

import java.io.Serializable;

import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class DirtTile extends Tile implements Serializable {

	/**
	 * Instantiates a new dirt tile.
	 *
	 * @param object the object
	 * @param world the world
	 */
	public DirtTile(Rectangle r) {
		super(r);
	}

}
