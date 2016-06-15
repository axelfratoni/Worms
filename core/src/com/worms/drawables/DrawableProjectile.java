package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class DrawableProjectile {
	
	private Sprite sprite;
	private Vector2 pos;
	private float angle;
	
	public DrawableProjectile(Vector2 pos, Texture tex){
		this.sprite = new Sprite(tex);
		this.pos = pos;
		this.angle = 0;
	}
	public DrawableProjectile(Vector2 pos, Texture tex, float angle){
		this.sprite = new Sprite(tex);
		this.pos = pos;
		this.angle = angle;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Vector2 getPos() {
		return pos;
	}
	
	public float getAngle(){
		return angle;
	}
	
}
