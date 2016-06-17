package com.worms.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class DebugString {
	
	private static BitmapFont bfont;
	private static SpriteBatch spritebatch;

	/**
	 * Sets the sprite batch.
	 *
	 * @param batch the batch
	 */
	public static void SetSpriteBatch (SpriteBatch batch){
		spritebatch = batch; 
	}
	
	/**
	 * Draw.
	 *
	 * @param str the str
	 * @param cam the cam
	 */
	public static void draw(CharSequence str, OrthographicCamera cam){
		Vector3 position = new Vector3(10f,20f,0f);
		cam.unproject(position);
		bfont = new BitmapFont();
		bfont.draw(spritebatch, str, position.x , position.y );
	}
	
}
