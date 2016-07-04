package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

// TODO: Auto-generated Javadoc
public class DrawableBar {

	private Texture barAbove, barBelow;
	private float valMax, val, width;
	private Vector2 pos;
	
	
	/**
	 * Instantiates a new drawable bar.
	 *
	 * @param barBelow the bar below
	 * @param barAbove the bar above
	 * @param pos the pos
	 * @param valMax the val max
	 * @param val the val
	 * @param width the width
	 */
	public DrawableBar(Texture barBelow, Texture barAbove, Vector2 pos, float valMax, float val, float width){
	this.barAbove = barAbove;
	this.barBelow = barBelow;
	this.pos = pos;
	this.valMax = valMax;
	this.val = val;
	this.width = width;
	}

	/**
	 * Gets the bar above.
	 *
	 * @return the bar above
	 */
	public Texture getBarAbove() {
		return barAbove;
	}

	/**
	 * Gets the bar below.
	 *
	 * @return the bar below
	 */
	public Texture getBarBelow() {
		return barBelow;
	}

	/**
	 * Gets the val max.
	 *
	 * @return the val max
	 */
	public float getValMax() {
		return valMax;
	}

	/**
	 * Gets the val.
	 *
	 * @return the val
	 */
	public float getVal() {
		return val;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos() {
		return pos;
	}


}
