package com.worms.game;

import com.badlogic.gdx.graphics.Texture;
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

import static com.worms.utils.Constants.*;

import java.util.ArrayList;

public class Player {
	private Texture tex;
	private Body player;
	private World world;
	private short turnStep = 0; // 0 = esperar / 1 = mover / 2 = elegir arma / 3 = cargar / 4 = disparar
	private float startingPosition;
	private ArrayList<Projectile> weapons;
	private float health;
	private Arrow arrow;
	private ChargeBar chargeBar;
	private HealthBar hBar;
	private MovementBar movementBar;
	private Projectile actualWeapon;
	private boolean isFlaggedForDelete;
	private boolean shooting;
	private int team;
	private float missileX;
	private boolean hasSpecialProjectile;
	
	public Player( float x, float y, String str, World world, int team, boolean specialProjectile){
		tex = new Texture(str);
		player = BodyCreators.createBox(x , y, (float) tex.getHeight(), (float) tex.getWidth(), false, true, world, BIT_PLAYER, (short) (BIT_WALL | BIT_PROJECTILE | BIT_EXPLOSION), (short) 0, this);
		this.world = world;
		this.team = team;
		shooting=false;
		health = 100f;
		hBar = new HealthBar(getX(), getY(), world);
		weapons = new ArrayList<Projectile>();
		weapons.add(new Grenade(world,  this));
		weapons.add(new Bullet(world, this));
		if (specialProjectile){
			weapons.add(new Missile(world, this));
		} else {
		}
		this.hasSpecialProjectile = specialProjectile;
		isFlaggedForDelete = false;
	}
	
	public Body getPlayer(){
		return player;
	}
	
	
	public void nextStep(){
		if ( turnStep == 0){
			startingPosition = player.getPosition().x;
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
			movementBar = new MovementBar(getX(),getY(),world);
		if(turnStep == 3 && actualWeapon instanceof Missile){
			Worms.switchCameraStatus();
		} else if(turnStep == 3){
			arrow = new Arrow(getX() + tex.getWidth() /2, getY() + tex.getHeight() / 2,  world);
		}
		if(turnStep == 4)
			chargeBar = new ChargeBar(getX(),getY(),world);
		if(turnStep == 5){
			shooting = true;
		}
	}
	
	public boolean isShooting(){
		return shooting;
	}
	
	public void shootMissile(){
		turnStep = 4;
		nextStep();
	}
	
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
	
	public Texture getTex(){
		return tex;
	}
	
	public Bar getBar(int a){
		switch (a){
		case 1 : return (Bar) hBar; 
		case 2 : return (Bar) movementBar; 
		case 3 : return (Bar) chargeBar; 
		default : return null;
		}
	}
	
	public short getStep(){
		return turnStep;
	}
	
	public boolean hasSpecialProjectile(){
		return hasSpecialProjectile;
	}
	
	public void usedSpecialProjectile(){
		hasSpecialProjectile = false;
	}
	
	public float getHealth(){
		return health;
	}
	
	public float getStartingPosition(){
		return startingPosition;
	}
	
	public float getMovement(){
		return Math.abs(player.getPosition().x - getStartingPosition());
	}
	
	public Arrow getArrow(){
		return arrow;
	}
	
	public void giveCoordinate(float x){
		missileX = x / PPM;
	}
	
	public void selectWeapon(int i){
		actualWeapon = weapons.get(i - 1);
	}
	
	public Projectile getWeapon(){
		return actualWeapon;
	}
	
	public float getY(){
		return player.getPosition().y  * PPM - (tex.getWidth() / 2);
	}
	
	public float getX(){
		return player.getPosition().x  * PPM - (tex.getWidth() / 2);
	}
	
	public boolean isFlaggedForDelete(){
		return isFlaggedForDelete;
	}
	public void flagForDeletion(){
		isFlaggedForDelete = true;
	}
	
	public void update(){
		if (turnStep >= 3 && !(actualWeapon instanceof Missile)){
			arrow.update( player.getPosition() );
		}
		if (turnStep == 4){
			chargeBar.update();
		}

	}
	
	public void seppuku( float damage){
		health -= damage;
	}
	
	public void dispose(boolean tocoMapa){
		if ( arrow != null)
			arrow.dispose();
		if (tocoMapa)
			Teams.getTeam(team).remove(this);
		isFlaggedForDelete = true;
		Worms.getBodiesToBeDeleted().add(player);
		tex.dispose();
	}
	
	public void resetTurn(){
		turnStep = 0;
	}
	
}
