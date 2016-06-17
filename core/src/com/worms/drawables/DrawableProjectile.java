package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class DrawableProjectile {
	
	private Sprite sprite;
	private Vector2 pos;
	private float angle;
	
	/**
	 * Instantiates a new drawable projectile.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 */
	public DrawableProjectile(Vector2 pos, Texture tex){
		this.sprite = new Sprite(tex);
		this.pos = pos;
		this.angle = 0;
	}
	
	/**
	 * Instantiates a new drawable projectile.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 * @param angle the angle
	 */
	public DrawableProjectile(Vector2 pos, Texture tex, float angle){
		this.sprite = new Sprite(tex);
		this.pos = pos;
		this.angle = angle;
	}

	/**
	 * Gets the sprite.
	 *
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
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
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return angle;
	}
	
}
