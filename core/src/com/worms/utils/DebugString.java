package com.worms.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class DebugString {
	private static BitmapFont bfont;
	private static SpriteBatch spritebatch;
	
	public static void SetSpriteBatch (SpriteBatch batch){
		spritebatch = batch; 
	}
	public static void draw(CharSequence str, OrthographicCamera cam){
		Vector3 position = new Vector3(10f,20f,0f);
		cam.unproject(position);
		bfont = new BitmapFont();
		bfont.draw(spritebatch, str, position.x , position.y );
	}
	
}
