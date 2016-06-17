package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class DrawableArrow {

	private Vector2 pos;
	private Texture tex;
	private float angle;
	private Sprite arrowsprite;
	
	/**
	 * Instantiates a new drawable arrow.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 * @param angle the angle
	 */
	public DrawableArrow(Vector2 pos, Texture tex, float angle){
		this.pos = pos;
		this.tex = tex;
		this.angle = angle;
		arrowsprite = new Sprite(tex);
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
	
	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return angle;
	}
	
	/**
	 * Gets the sprite.
	 *
	 * @return the sprite
	 */
	public Sprite getSprite(){
		return arrowsprite;
	}
}

