package com.worms.drawables;

import com.badlogic.gdx.math.Vector2;
import static com.worms.utils.Constants.*;

public class DrawableExplosion {
	
	private Vector2 pos;
	private String explosionType;
	/**
	 * Instantiates a new drawable explosion.
	 *
	 * @param pos the pos
	 * @param radius the radius
	 * @param tex the tex
	 */
	public DrawableExplosion( Vector2 pos, float radius){
		this.pos = pos;
		if ( radius == E_RADIUS_BULLET){
			explosionType = "Bulletexplosion.png";
		} else if ( radius == E_RADIUS_GRENADE){
			explosionType = "Grenadeexplosion.png";
		} else {
			explosionType = "Missileexplosion.png";
		}
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Vector2 getPos() {
		return pos;
	}

	/**
	 * Gets the tex.
	 *
	 * @return the tex
	 */
	public String getExplosionType() {
		return explosionType;
	}
	
	
}
