package com.worms.bars;


public class ChargeBar extends Bar {

	private float charge;
	private boolean chargeDir;
	
	/**
	 * Instantiates a new charge bar.
	 *
	 * @param x the x
	 * @param y the y
	 * @param  the 
	 */
	public ChargeBar(float x, float y){
		super(x,y, 1, 0, 0, 1, 1, 1);
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
