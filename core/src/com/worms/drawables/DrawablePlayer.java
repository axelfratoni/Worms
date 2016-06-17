package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DrawablePlayer {

	private Vector2 pos;
	private Texture tex;
	
	/**
	 * Instantiates a new drawable player.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 */
	public DrawablePlayer(Vector2 pos, Texture tex){
		this.pos = pos;
		this.tex = tex;
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
	public Texture getTex(){
		return tex;
	}
	
}
