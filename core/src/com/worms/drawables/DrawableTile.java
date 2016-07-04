package com.worms.drawables;

import com.badlogic.gdx.math.Vector2;
import com.worms.utils.GrassTile;
import com.worms.utils.Tile;

// TODO: Auto-generated Javadoc
public class DrawableTile {
	private Vector2 pos;
	private String typeOfTile;
	
	/**
	 * Instantiates a new drawable tile.
	 *
	 * @param pos the pos
	 * @param t the t
	 */
	public DrawableTile( Vector2 pos, Tile t){
		this.pos = pos;

		if (t instanceof GrassTile){
			typeOfTile = "Grasstile.png";
		} else {
			typeOfTile = "Dirttile.png";
		}
	}
	
	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos(){
		return pos;
	}
	
	/**
	 * Gets the tex.
	 *
	 * @return the tex
	 */
	public String getTypeOfTile(){
		return typeOfTile;
	}
}
