package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DrawableExplosion {
	
	private Vector2 pos;
	private float radius;
	private Texture tex;
	
	public DrawableExplosion( Vector2 pos, float radius, Texture tex){
		this.pos = pos;
		this.radius = radius;
		this.tex = tex;
	}

	public Vector2 getPos() {
		return pos;
	}

	public float getRadius() {
		return radius;
	}

	public Texture getTex() {
		return tex;
	}
	
	
}
