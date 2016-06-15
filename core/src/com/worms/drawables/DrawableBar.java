package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DrawableBar {

	private Texture barAbove, barBelow;
	private float valMax, val, width;
	private Vector2 pos;
	
	
	public DrawableBar(Texture barBelow, Texture barAbove, Vector2 pos, float valMax, float val, float width){
	this.barAbove = barAbove;
	this.barBelow = barBelow;
	this.pos = pos;
	this.valMax = valMax;
	this.val = val;
	this.width = width;
	}

	public Texture getBarAbove() {
		return barAbove;
	}

	public Texture getBarBelow() {
		return barBelow;
	}

	public float getValMax() {
		return valMax;
	}

	public float getVal() {
		return val;
	}

	public float getWidth() {
		return width;
	}

	public Vector2 getPos() {
		return pos;
	}


}
