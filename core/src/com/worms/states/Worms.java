package com.worms.states;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Worms extends Game {
	
	private GameState game;
	private BeginState begin;
	private EndState end;
	private SpriteBatch batch;
	
	private short state;

	@Override
	public void create () {
		batch = new SpriteBatch();
		begin = new BeginState(batch);
		state = 1;
	}

	@Override
	public void render () {
		switch (state){
		case 1 :	begin.render();
					if (begin.end()){
						game = new GameState(batch, begin.getListener().getPath());
						switchState();
					}
					break;
		case 2 : 	game.render();
					if (game.end()){
						end = new EndState(batch, game.team1Won());
						switchState();
					}
					break;
		case 3 :	end.render();
					if (end.end()) switchState();
					break;
		default :	break;
		}
	}
	
	public void dispose(){
		batch.dispose();
	}
	
	public void switchState(){
		switch (state){
		case 1 :	begin.dispose();
					break;
		case 2 :	game.dispose();
					batch.dispose();
					batch = new SpriteBatch();
					break;
		case 3 :	end.dispose();
					Gdx.app.exit();
					break;
		default : break;
		}
		state ++;
	}
}	
	
