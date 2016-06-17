package com.worms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.worms.projectiles.Bullet;
import com.worms.states.BeginState;
import com.worms.states.EndState;
import com.worms.states.GameState;

import static com.worms.utils.Constants.*;
import com.worms.utils.WormsTextInputListener;

public class InputManager {
	
	WormsTextInputListener listener = null;
	
	/**
	 * Manage input.
	 *
	 * @param g the g
	 * @param player the player
	 */
	public void manageInput(GameState g, Player player ){
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
			listener = new WormsTextInputListener();
			Gdx.input.getTextInput(listener, "Introduzca el nombre del archivo", "SaveGame.sav",null);
		}
		if(listener != null && listener.hasInputed()){
			String savepath = listener.getPath(); 
			player.setSaving(true);
			player.setSavePath(savepath);
			listener = null;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
			GameState.switchCameraStatus();
		}
		
		if(player.getStep() == 1){
		float horizontalForce = 0f;
		float verticalForce = 0f;
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
			}
			
			player.getPlayer().setLinearVelocity( horizontalForce * SPEED, player.getPlayer().getLinearVelocity().y);
		}

		
		if(player.getStep() == 2){
			player.getPlayer().setLinearVelocity( 0, player.getPlayer().getLinearVelocity().y);
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
				player.selectWeapon(1);
				player.nextStep();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
				player.selectWeapon(2);
				player.nextStep();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
				if ( player.hasSpecialProjectile()){
					player.selectWeapon(3);
					player.usedSpecialProjectile();
					player.nextStep();
				}
			}
		}
		
		if(player.getStep() == 3){
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				player.getArrow().moveArrow( true, player.getPlayer().getPosition());
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				player.getArrow().moveArrow( false, player.getPlayer().getPosition());
			}
		}
		
		if(player.getStep() == 4 && player.getWeapon() instanceof Bullet){
			player.nextStep();
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.getStep() != 2 && player.getStep() != 5){
			player.nextStep();
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
	 * @return the vector3
	 */
	public Vector3 manageInput(GameState g, Vector3 pos, boolean a){
		int vel = 4;
		if (a){
			if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
				GameState.switchCameraStatus();
			}
		}
		if (!a){ 
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				GameState.shootMissile();
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
