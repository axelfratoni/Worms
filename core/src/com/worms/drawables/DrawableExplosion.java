package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DrawableExplosion {
	
	private Vector2 pos;
	private float radius;
	private Texture tex;
	
	/**
	 * Instantiates a new drawable explosion.
	 *
	 * @param pos the pos
	 * @param radius the radius
	 * @param tex the tex
	 */
	public DrawableExplosion( Vector2 pos, float radius, Texture tex){
		this.pos = pos;
		this.radius = radius;
		this.tex = tex;
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos() {
		return pos;
	}

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Gets the tex.
	 *
	 * @return the tex
	 */
	public Texture getTex() {
		return tex;
	}
	
	
}
