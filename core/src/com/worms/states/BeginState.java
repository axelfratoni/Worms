package com.worms.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.worms.game.InputManager;
import com.worms.utils.WormsTextInputListener;

public class BeginState {
	private SpriteBatch batch;
	private boolean end;
	private Sprite mainMenu;
	private InputManager iM;
	private WormsTextInputListener listener ;

	
	public BeginState(SpriteBatch batch) {
		this.batch = batch;
		end = false;
		mainMenu = new Sprite(new Texture("Images/Mainmenu.png"));
		listener = new WormsTextInputListener();
		mainMenu.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		iM = new InputManager();
	}


	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		mainMenu.draw(batch);
		batch.end();
	}
	
	private void update(){
		if (iM.manageInput(this, listener)){
		end = true;
		}
		
	}
	public WormsTextInputListener getListener(){
		return listener;
	}
	
	public boolean end(){
		return end;
	}
	
	public void dispose(){
		
	}

}
