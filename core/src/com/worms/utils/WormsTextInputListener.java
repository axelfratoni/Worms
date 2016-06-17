package com.worms.utils;

import com.badlogic.gdx.Input.TextInputListener;

public class WormsTextInputListener implements TextInputListener {
	
	
	private String path;
	
	public WormsTextInputListener(){
		path = null;
	}
	
	public String getPath(){
		return path;
	}
	
	public boolean hasInputed(){
		return (path != null);
	}
	
	@Override
	public void input(String text) {
		path = text;
	}

	@Override
	public void canceled() {
	}

}