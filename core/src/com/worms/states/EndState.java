package com.worms.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.worms.game.InputManager;

public class EndState {
	
	private SpriteBatch batch;
	private boolean end;
	private Sprite endScreen;
	private InputManager iM;
	
	public EndState(SpriteBatch batch, boolean team1Won) {
//		this.batch = batch;
		this.batch = new SpriteBatch();
		end = false;
		Texture tex;
		if ( team1Won){
			tex = new Texture("Images/Team1victoryscreen.png");
		} else {
			tex = new Texture("Images/Team2victoryscreen.png");
		}
		iM = new InputManager();
		endScreen = new Sprite(tex);
		endScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}


	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		endScreen.draw(batch);
		batch.end();
	}
	
	private void update(){
		if (iM.manageInput(this)){
			end = true;
		}
	}
	


	public boolean end(){
		return end;
	}
	
	public void dispose(){
		
	}


	
}