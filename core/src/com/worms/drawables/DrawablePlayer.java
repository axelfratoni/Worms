package com.worms.drawables;

import com.badlogic.gdx.math.Vector2;

public class DrawablePlayer {

	private Vector2 pos;
	private String playersTeam;
	/**
	 * Instantiates a new drawable player.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 */
	public DrawablePlayer(Vector2 pos, int team){
		this.pos = pos;
		if (team == 1){
			playersTeam = "Redworm.png";
		} else {
			playersTeam = "Blueworm.png";
		}
	}
	
	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos(){
		return pos;
	}
	
	public String getPlayersTeam(){
		return playersTeam;
	}
}
