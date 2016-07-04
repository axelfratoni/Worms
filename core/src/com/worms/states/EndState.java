package com.worms.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.worms.game.Controller;

// TODO: Auto-generated Javadoc
public class EndState {
	
	private SpriteBatch batch;
	private boolean end;
	private Sprite endScreen;
	private Controller iM;
	
	/**
	 * Instantiates a new end state.
	 *
	 * @param batch the batch
	 * @param team1Won the team1 won
	 */
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
		iM = new Controller();
		endScreen = new Sprite(tex);
		endScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}


	/**
	 * Render.
	 */
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		endScreen.draw(batch);
		batch.end();
	}
	
	/**
	 * Update.
	 */
	private void update(){
		if (iM.manageInput(this)){
			end = true;
		}
	}
	


	/**
	 * End.
	 *
	 * @return true, if successful
	 */
	public boolean end(){
		return end;
	}
	
	/**
	 * Dispose.
	 */
	public void dispose(){
		
	}


	
}
