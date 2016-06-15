package com.worms.game;

import com.badlogic.gdx.graphics.Texture;

public class Commands {
	
	private Texture texture1;
	private Texture texture2;
	private Texture texture3;
	private Texture texture4;
	
	public Commands(){
		texture1 = new Texture("Images/Menu1.png");
		texture2 = new Texture("Images/Menu2.png");
		texture3 = new Texture("Images/Menu3.png");
		texture4 = new Texture("Images/Menu4.png");
	}
	
	
	public Texture getTex(int step){
		if(step == 1)
			return texture1;
		if(step == 2)
			return texture2;
		if(step == 3)
			return texture3;
		if(step == 4)
			return texture4;
		return null;
	}
	
	public void dispose(){
		texture1.dispose();
		texture2.dispose();
		texture3.dispose();
		texture4.dispose();
	}
}
