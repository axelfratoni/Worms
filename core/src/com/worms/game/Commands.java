package com.worms.game;

import com.badlogic.gdx.graphics.Texture;

public class Commands {
	
	private Texture texture1;
	private Texture texture2;
	private Texture texture3;
	private Texture texture4;
	private Texture texture5;
	
	/**
	 * Instantiates a new commands.
	 */
	public Commands(){
		texture1 = new Texture("Images/Menu1.png");
		texture2 = new Texture("Images/Menu2.png");
		texture3 = new Texture("Images/Menu3.png");
		texture4 = new Texture("Images/Menu4.png");
		texture5 = new Texture("Images/Menu2WOMissile.png");
	}
	
	
	/**
	 * Gets the tex.
	 *
	 * @param step the step
	 * @param hasMissile the has missile
	 * @return the tex
	 */
	public Texture getTex(int step, boolean hasMissile){
		if(step == 1)
			return texture1;
		if(step == 2 && hasMissile)
			return texture2;
		if (step == 2 && !hasMissile)
			return texture5;
		if(step == 3)
			return texture3;
		if(step == 4)
			return texture4;
		return null;
	}
	
	/**
	 * Dispose.
	 */
	public void dispose(){
		texture1.dispose();
		texture2.dispose();
		texture3.dispose();
		texture4.dispose();
	}
}
