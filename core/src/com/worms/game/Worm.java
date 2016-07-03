package com.worms.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.projectiles.Bullet;
import com.worms.projectiles.Grenade;
import com.worms.projectiles.Missile;
import com.worms.projectiles.Projectile;
import com.worms.states.GameState;

import static com.worms.utils.Constants.*;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Worm implements Serializable{
	
	private transient Body body;
	private transient short turnStep = 0; // 0 = esperar / 1 = mover / 2 = elegir arma / 3 = cargar / 4 = disparar
	private transient ArrayList<Projectile> weapons;
	private transient Arrow arrow;
	
	private transient Projectile actualWeapon;
	private transient String savePath;
	private transient boolean isSaving;
	private float startingPosition;
	private float health;
	private float charge;
	private boolean chargeDir;
	private boolean isFlaggedForDelete;
	private int team;
	private float missileX;
	private boolean hasSpecialProjectile;
	
	private static float height = 32;
	private static float width = 32;
/**
 * Instantiates a new body.
 *
 * @param x the x
 * @param y the y
 * @param str the str
 * @param world the world
 * @param team the team
 * @param specialProjectile the special projectile
 */
public Worm( String str, World world, int team,  boolean specialProjectile){
		arrow = new Arrow();
		this.team = team;
		this.setSaving(false);
		health = 100f;
		charge = 0;
		chargeDir = true;
		this.hasSpecialProjectile = specialProjectile;
		isFlaggedForDelete = false;
		setPlayer(world);
	}
	
	/**
	 * Sets the body.
	 *
	 * @param x the x
	 * @param y the y
	 * @param world the world
	 */
	public void setPlayer( World world){
		body = null;
		weapons = new ArrayList<Projectile>();
		weapons.add(new Grenade(world,  this));
		weapons.add(new Bullet(world, this));
		if (hasSpecialProjectile){
			weapons.add(new Missile(world, this));
		} else {
		}
	}
	
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public Body getPlayer(){
		return body;
	}
	
	public void setBody(Body b){
		if (body == null){
			body = b;
		}
	}
	
	/**
	 * Next step.
	 */
	public void nextStep(){
		turnStep++;
	}
	
	/**
	 * Gets the step.
	 * @return the step
	 */
	public short getStep(){
		return turnStep;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public void shootMissile(){
		turnStep = 4;
		nextStep();
	}
	
	
	public void setStartingPosition(){
		this.startingPosition = body.getPosition().x;
	}
	
	/**
	 * Gets the bar.
	 *
	 * @param a the a
	 * @return the bar
	 */

	/**
	 * Checks for special projectile.
	 *
	 * @return true, if successful
	 */
	public boolean hasSpecialProjectile(){
		return hasSpecialProjectile;
	}
	
	/**
	 * Used special projectile.
	 */
	public void usedSpecialProjectile(){
		hasSpecialProjectile = false;
	}
	
	/**
	 * Gets the health.
	 *
	 * @return the health
	 */
	public float getHealth(){
		return health;
	}
	
	/**
	 * Gets the starting position.
	 *
	 * @return the starting position
	 */
	public float getStartingPosition(){
		return startingPosition;
	}
	
	/**
	 * Gets the movement.
	 *
	 * @return the movement
	 */
	public float getMovement(){
		return Math.abs(body.getPosition().x - startingPosition);
	}
	
	
	/**
	 * Gets the arrow.
	 *
	 * @return the arrow
	 */
	public Arrow getArrow(){
		return arrow;
	}
	
	/**
	 * Give coordinate.
	 *
	 * @param x the x
	 */
	public void giveCoordinate(float x){
		missileX = x / PPM;
	}
	
	/**
	 * Select weapon.
	 *
	 * @param i the i
	 */
	public void selectWeapon(int i){
		actualWeapon = weapons.get(i - 1);
	}
	
	/**
	 * Gets the weapon.
	 *
	 * @return the weapon
	 */
	public Projectile getWeapon(){
		return actualWeapon;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public float getY(){
		return body.getPosition().y  * PPM - width / 2 ;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public float getX(){
		return body.getPosition().x  * PPM - height / 2;
	}
	
	
	/**
	 * Update.
	 */
	public void update(){
		if (turnStep >= 3 && !(actualWeapon instanceof Missile)){
			arrow.update( body.getPosition() );
		}
		if (turnStep == 4){
			if(charge>60 || charge<0)
		    {
		        chargeDir = !chargeDir;
		    }
		    if(chargeDir)
		    	charge++;
		    else
		    	charge--;
		}
		if (turnStep > 4)
			charge = 0;

	}
	
	public float getCharge(){
		return charge;
	}
	
	
	/**
	 * Seppuku.
	 *
	 * @param damage the damage
	 */
	public void seppuku( float damage){
		health -= damage;
	}
	
	/**
	 * Dispose.
	 *
	 * @param tocoMapa the toco mapa
	 */
	public void dispose(boolean tocoMapa){
		if (!isFlaggedForDelete){
			isFlaggedForDelete = true;
			if ( arrow != null)
				arrow.dispose();
			if (tocoMapa)
				Teams.getTeam(team).remove(this);
			GameState.getBodiesToBeDeleted().add(body);
		}
	}
	
	public int getTeam(){
		return team;
	}
	
	/**
	 * Reset turn.
	 */
	public void resetTurn(){
		if (turnStep > 0){
			System.out.println("DISPOSE" + team);
//			if (!(getWeapon() instanceof Missile)){
//				getChargeBar().dispose();
				getArrow().dispose();
//			}
		}
		turnStep = 0;
	}
	
	/**
	 * Checks if is saving.
	 *
	 * @return true, if is saving
	 */
	public boolean isSaving() {
		return isSaving;
	}
	
	/**
	 * Sets the saving.
	 *
	 * @param isSaving the new saving
	 */
	public void setSaving(boolean isSaving) {
		this.isSaving = isSaving;
	}
	
	/**
	 * Gets the save path.
	 *
	 * @return the save path
	 */
	public String getSavePath() {
		return savePath;
	}
	
	/**
	 * Sets the save path.
	 *
	 * @param savePath the new save path
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public float getMissileX() {
		return missileX;
	}
	
	
}
