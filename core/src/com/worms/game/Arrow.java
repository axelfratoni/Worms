package com.worms.game;

import static com.worms.utils.Constants.PPM;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.worms.states.GameState;

// TODO: Auto-generated Javadoc
public class Arrow {
	private Body arrow;
	private static float height = 5f;
	private static float width = 64f;
	/**
	 * Instantiates a new arrow.
	 *
	 */
	public Arrow(){
	}
	
	/**
	 * Sets the body.
	 *
	 * @param b the new body
	 */
	public void setBody(Body b){
		arrow = b;
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
	 * Gets the width.
	 *
	 * @return the width
	 */
	public float getWidth(){
		return width;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public float getHeight(){
		return height;
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
	 * Gets the tip.
	 *
	 * @return the tip
	 */
	public Vector2 getTip(){
		Vector2 tip = new Vector2();
		tip.x =  arrow.getPosition().x + (0.5f + (width ) / PPM / 2) *  MathUtils.cos( getAngle() * MathUtils.degreesToRadians);
		tip.y =  arrow.getPosition().y + (0.5f + (width ) / PPM / 2) *  MathUtils.sin( getAngle() * MathUtils.degreesToRadians);
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
	 * Dispose.
	 */
	public void dispose(){
		if (arrow != null)
			GameState.getBodiesToBeDeleted().add(arrow);
	}
	
}
