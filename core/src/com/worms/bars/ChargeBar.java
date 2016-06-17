package com.worms.bars;

import com.badlogic.gdx.physics.box2d.World;

public class ChargeBar extends Bar {

	private float charge;
	private boolean chargeDir;
	
	/**
	 * Instantiates a new charge bar.
	 *
	 * @param x the x
	 * @param y the y
	 * @param world the world
	 */
	public ChargeBar(float x, float y, World world){
		super(x,y,world, 1, 0, 0, 1, 1, 1);
		charge = 0;
		chargeDir = true;
	}
	
	/**
	 * Update.
	 */
	public void update(){
	    if(charge>60 || charge<0)
	    {
	        chargeDir = !chargeDir;
	    }
	    if(chargeDir)
	    	charge++;
	    else
	    	charge--;
	}
	
	/**
	 * Gets the charge.
	 *
	 * @return the charge
	 */
	public float getCharge(){
		return charge;
	}
}
