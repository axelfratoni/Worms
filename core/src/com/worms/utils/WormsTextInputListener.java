package com.worms.utils;

import com.badlogic.gdx.Input.TextInputListener;

public class WormsTextInputListener implements TextInputListener {
	
	
	private String path;
	
	/**
	 * Instantiates a new worms text input listener.
	 */
	public WormsTextInputListener(){
		path = null;
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath(){
		return path;
	}
	
	/**
	 * Checks for inputed.
	 *
	 * @return true, if successful
	 */
	public boolean hasInputed(){
		return (path != null);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Input.TextInputListener#input(java.lang.String)
	 */
	@Override
	public void input(String text) {
		path = text;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Input.TextInputListener#canceled()
	 */
	@Override
	public void canceled() {
	}

}