package com.worms.game;

import static com.worms.utils.Constants.BIT_ARROW;
import static com.worms.utils.Constants.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;
import com.worms.states.GameState;

public class Arrow {
	private Body arrow;
	private Texture arrowtex;
	
	/**
	 * Instantiates a new arrow.
	 *
	 * @param x the x
	 * @param y the y
	 * @param world the world
	 */
	public Arrow(float x, float y,  World world){
		arrowtex = new Texture("Images/LongArrow.png");
		arrow = BodyCreators.createBox(x , y, arrowtex.getWidth(), arrowtex.getHeight(), false, true, world, BIT_ARROW, (short) 0, (short) 0, this);
		arrow.setGravityScale( 0f);
		
	}
	
	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return arrow.getAngle() * MathUtils.radiansToDegrees;
	}
	
	/**
	 * Move arrow.
	 *
	 * @param a the a
	 * @param pos the pos
	 */
	public void moveArrow(boolean a, Vector2 pos){
		float i;
		i = getAngle();
		if (a) {
			if ( i <= 205)
			arrow.setTransform( pos, arrow.getAngle() + (1f / 20f));
		}
		else {
			if ( i >= -20) 
			arrow.setTransform( pos, arrow.getAngle() - (1f / 20f));
		}

	}
	
	
	/**
	 * Update.
	 *
	 * @param pos the pos
	 */
	public void update(Vector2 pos){
		arrow.setTransform ( pos, arrow.getAngle() );
	}
	
	/**
	 * Gets the tip.
	 *
	 * @return the tip
	 */
	public Vector2 getTip(){
		Vector2 tip = new Vector2();
		tip.x =  arrow.getPosition().x + (0.5f + (arrowtex.getWidth() ) / PPM / 2) *  MathUtils.cos( getAngle() * MathUtils.degreesToRadians);
		tip.y =  arrow.getPosition().y + (0.5f + (arrowtex.getWidth() ) / PPM / 2) *  MathUtils.sin( getAngle() * MathUtils.degreesToRadians);
		return tip;
	}
	
	/**
	 * Gets the arrow.
	 *
	 * @return the arrow
	 */
	public Body getArrow(){
		return arrow;
	}
	
	/**
	 * Gets the tex.
	 *
	 * @return the tex
	 */
	public Texture getTex(){
		return arrowtex;
	}
	
	/**
	 * Dispose.
	 */
	public void dispose(){
		GameState.getBodiesToBeDeleted().add(arrow);
		arrowtex.dispose();
	}
	
}
