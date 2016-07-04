package com.worms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.projectiles.Bullet;
import com.worms.projectiles.Missile;
import com.worms.projectiles.Projectile;
import com.worms.states.BeginState;
import com.worms.states.EndState;
import com.worms.states.GameState;

import static com.worms.utils.Constants.*;
import com.worms.utils.WormsTextInputListener;

// TODO: Auto-generated Javadoc
public class Controller {
	
	WormsTextInputListener listener = null;
	
	private World world;
	private boolean a = true; 
	
	/**
	 * Instantiates a new controller.
	 */
	public Controller(){
		this.world = null;
	}
	
	/**
	 * Instantiates a new controller.
	 *
	 * @param world the world
	 */
	public Controller(World world){
		this.world = world;
	}
	
	
	
	/**
	 * Manage input.
	 *
	 * @param g the g
	 * @param player the player
	 */
	public void manageInput(GameState g, Worm player ){
		
		if(listener != null && listener.hasInputed()){
			String savepath = listener.getPath(); 
			player.setSaving(true);
			player.setSavePath(savepath);
			listener = null;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
			GameState.switchCameraStatus();
		}
		if (player.getStep() == BEGINNING_STEP){
			nextStep(player);
			player.nextStep();
		}
		
		if (player.getStep() == CHARGING_STEP){
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				player.nextStep();
				nextStep(player);
  				shoot(player);
			}
			
		}
		
		if(player.getStep() == ANGLE_STEP){
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				player.getArrow().moveArrow( true, player.getPlayer().getPosition());
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				player.getArrow().moveArrow( false, player.getPlayer().getPosition());
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				player.nextStep();
				nextStep(player);
				if (player.getWeapon() instanceof Bullet){
					player.nextStep();
					nextStep(player);
	  				shoot(player);
				}
			}
		}
		
		
		if(player.getStep() == WEAPON_STEP){
			player.getPlayer().setLinearVelocity( 0, player.getPlayer().getLinearVelocity().y);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
				player.selectWeapon(1);
				player.nextStep();
				nextStep(player);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
				player.selectWeapon(2);
				player.nextStep();
				nextStep(player);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
				if ( player.hasSpecialProjectile()){
					player.selectWeapon(3);
					player.usedSpecialProjectile();
					nextStep(player);
					player.nextStep();
				}
			}
		}
		if(player.getStep() == MOVEMENT_STEP){
			float horizontalForce = 0f;
			float verticalForce = 0f;
			if (player.getTeam() == 1 && a){
				nextStep(player);
				a = false;
			} else if (player.getTeam() == 2 && !a){
				nextStep(player);
				a = true;
			}
			if ( validateMovement(player.getPlayer().getPosition().x, player.getStartingPosition())){
				if ( Gdx.input.isKeyPressed(Input.Keys.LEFT)){
					horizontalForce = -1f;
					if(player.getPlayer().getLinearVelocity().epsilonEquals(0,0,1f)){
						player.getPlayer().applyForceToCenter(500 , 100, false);
					}
				}
				
				if ( Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
					horizontalForce = 1f;
					if(player.getPlayer().getLinearVelocity().epsilonEquals(0,0,1f)){
					player.getPlayer().applyForceToCenter(-500 , 100, false);
					}
				}
				
				if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
					if (player.getPlayer().getLinearVelocity().y == 0) {
						verticalForce = 275f;
						player.getPlayer().applyForceToCenter(0 , verticalForce, false);
					}
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
					listener = new WormsTextInputListener();
					Gdx.input.getTextInput(listener, "Introduzca el nombre del archivo", "SaveGame.sav",null);
				}
			}
			
			player.getPlayer().setLinearVelocity( horizontalForce * SPEED, player.getPlayer().getLinearVelocity().y);
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				player.nextStep();
				nextStep(player);
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
		
		}
	
	/**
	 * Manage input.
	 *
	 * @param g the g
	 * @param pos the pos
	 * @param a the a
	 * @param player the player
	 * @return the vector3
	 */
	public Vector3 manageInput(GameState g, Vector3 pos, boolean a, Worm player){
		int vel = 4;
		if (a){
			if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
				GameState.switchCameraStatus();
			}
		}
		if (!a){ 
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				shoot(player);
				player.nextStep();
				player.nextStep();
				GameState.switchCameraStatus();
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			pos.x-= vel;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			pos.x+=vel;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			pos.y+=vel;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			pos.y-=vel;
		}
		return pos;
	}

	/**
	 * Manage input.
	 *
	 * @param b the b
	 * @param listener the listener
	 * @return true, if successful
	 */
	public boolean manageInput(BeginState b, WormsTextInputListener listener){
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
		if ( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			return true;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
			Gdx.input.getTextInput(listener, "Introduzca el nombre del archivo", "SaveGame.sav",null);
			System.out.println(listener.getPath());

		}
		if (listener.getPath() != null){
			return true;
		}
		return false;
		
	}
	
	/**
	 * Manage input.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean manageInput(EndState e){
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
		if ( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			return true;
		}
		return false;
	}
	
	/**
	 * Next step.
	 *
	 * @param player the player
	 */
	public void nextStep( Worm player){
		int turnStep = player.getStep();
		
		if ( turnStep == BEGINNING_STEP){
			player.setStartingPosition();
		}
		
		if(turnStep == WEAPON_STEP && player.getWeapon() instanceof Missile){
			GameState.switchCameraStatus();
		} else if(turnStep == ANGLE_STEP){
			player.getArrow().setBody( BodyCreators.createBox(player.getX() + player.getWidth() /2 , player.getY() + player.getHeight() / 2, player.getArrow().getWidth(), player.getArrow().getHeight(), false, true, world, BIT_ARROW, (short) 0, (short) 0, player.getArrow()));
		}

		if(turnStep == SHOOTING_STEP){
		}
	}
	
	/**
	 * Shoot.
	 *
	 * @param player the player
	 */
	public void shoot(Worm player){
	Projectile weapon = player.getWeapon();
	if ( !(weapon instanceof Missile)){
		Body b = BodyCreators.createBox( player.getArrow().getTip().x * PPM, player.getArrow().getTip().y * PPM, weapon.getWidth(),weapon.getHeight(), false, true, world, (short) BIT_PROJECTILE, (short) (BIT_PLAYER | BIT_WALL), (short) 0 , weapon);
		weapon.shoot(player.getCharge() * 5  , player.getArrow().getAngle(), b);
	} else {
		Missile m;
		m = (Missile) weapon;
		m.shoot(player.getMissileX(), world);
	}
}
	
	/**
	 * Validate movement.
	 *
	 * @param xf the xf
	 * @param xi the xi
	 * @return true, if successful
	 */
	private static boolean validateMovement( float xf, float xi){
		if((xi - xf) > MOVEMENT_LIMIT && Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			return false;
		}
		if((xf - xi) > MOVEMENT_LIMIT && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			return false;
		}
		return true;
	}
}
