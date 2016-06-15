package com.worms.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class DrawableArrow {

	private Vector2 pos;
	private Texture tex;
	private float angle;
	private Sprite arrowsprite;
	
	public DrawableArrow(Vector2 pos, Texture tex, float angle){
		this.pos = pos;
		this.tex = tex;
		this.angle = angle;
		arrowsprite = new Sprite(tex);
	}
	
	public Vector2 getPos(){
		return pos;
	}
	
	public Texture getTex(){
		return tex;
	}
	
	public float getAngle(){
		return angle;
	}
	
	public Sprite getSprite(){
		return arrowsprite;
	}
}

