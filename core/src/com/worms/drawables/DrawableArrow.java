package com.worms.drawables;

import com.badlogic.gdx.math.Vector2;

// TODO: Auto-generated Javadoc
public class DrawableArrow {

	private Vector2 pos;
	private float angle;
	
	/**
	 * Instantiates a new drawable arrow.
	 *
	 * @param pos the pos
	 * @param angle the angle
	 */
	public DrawableArrow(Vector2 pos, float angle){
		this.pos = pos;
		this.angle = angle;
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
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return angle;
	}
	
}

