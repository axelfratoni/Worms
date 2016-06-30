package com.worms.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.bars.Bar;
import com.worms.bars.ChargeBar;
import com.worms.bars.HealthBar;
import com.worms.bars.MovementBar;
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
	private transient World world;
	private transient short turnStep = 0; // 0 = esperar / 1 = mover / 2 = elegir arma / 3 = cargar / 4 = disparar
	private transient ArrayList<Projectile> weapons;
	private transient Arrow arrow;
	
	private transient ChargeBar chargeBar;
	private transient HealthBar hBar;
	private transient MovementBar movementBar;
	
	private transient Projectile actualWeapon;
	private transient String savePath;
	private transient boolean isSaving;
	private float startingPosition;
	private float health;
	private boolean isFlaggedForDelete;
	private boolean shooting;
	private int team;
	private float missileX;
	private boolean hasSpecialProjectile;
	private float wormsHeight;
	private float wormsWidth;
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
		wormsHeight = 32;
		wormsWidth = 32;
		this.team = team;
		this.setSaving(false);
		health = 100f;
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
		this.world = world;
		this.shooting = false;
		body = null;
		hBar = new HealthBar(0, 0);
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
		if ( turnStep == 0){
			startingPosition = body.getPosition().x;
		}
		turnStep++;
		if(turnStep > 5){
			turnStep = 0;
			movementBar.dispose();
			if (!(actualWeapon instanceof Missile)){
				chargeBar.dispose();
				arrow.dispose();
			}
		}
		if(turnStep == 1)
			movementBar = new MovementBar(getX(),getY());
		if(turnStep == 3 && actualWeapon instanceof Missile){
			GameState.switchCameraStatus();
		} else if(turnStep == 3){
			arrow = new Arrow(getX() + wormsWidth /2, getY() + wormsHeight / 2,  world);
		}
		if(turnStep == 4)
			chargeBar = new ChargeBar(getX(),getY());
		if(turnStep == 5){
			shooting = true;
		}
	}
	
	/**
	 * Checks if is shooting.
	 *
	 * @return true, if is shooting
	 */
	public boolean isShooting(){
		return shooting;
	}
	
	/**
	 * Shoot missile.
	 */
	public void shootMissile(){
		turnStep = 4;
		nextStep();
	}
	
	/**
	 * Shoot.
	 */
	public void shoot(){
		if ( !(actualWeapon instanceof Missile)){
			actualWeapon.shoot( arrow.getTip(), chargeBar.getCharge() * 5  , arrow.getAngle());
		} else {
			Missile m;
			m = (Missile) actualWeapon;
			m.shoot(missileX);
		}
		shooting = false;
	}
	
	
	/**
	 * Gets the bar.
	 *
	 * @param a the a
	 * @return the bar
	 */
	public Bar getBar(int a){
		switch (a){
		case 1 : return (Bar) hBar; 
		case 2 : return (Bar) movementBar; 
		case 3 : return (Bar) chargeBar; 
		default : return null;
		}
	}
	
	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public short getStep(){
		return turnStep;
	}
	
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
		return Math.abs(body.getPosition().x - getStartingPosition());
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
		return body.getPosition().y  * PPM - wormsWidth / 2 ;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public float getX(){
		return body.getPosition().x  * PPM - wormsHeight / 2;
	}
	
	/**
	 * Checks if is flagged for delete.
	 *
	 * @return true, if is flagged for delete
	 */
	public boolean isFlaggedForDelete(){
		return isFlaggedForDelete;
	}
	
	/**
	 * Flag for deletion.
	 */
	public void flagForDeletion(){
		isFlaggedForDelete = true;
	}
	
	/**
	 * Update.
	 */
	public void update(){
		if (turnStep >= 3 && !(actualWeapon instanceof Missile)){
			arrow.update( body.getPosition() );
		}
		if (turnStep == 4){
			chargeBar.update();
		}

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
		if ( arrow != null)
			arrow.dispose();
		if (tocoMapa)
			Teams.getTeam(team).remove(this);
		isFlaggedForDelete = true;
		GameState.getBodiesToBeDeleted().add(body);
	}
	
	public int getTeam(){
		return team;
	}
	
	/**
	 * Reset turn.
	 */
	public void resetTurn(){
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
	
	
}
