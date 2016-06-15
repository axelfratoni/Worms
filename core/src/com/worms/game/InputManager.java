package com.worms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.worms.projectiles.Bullet;

import static com.worms.utils.Constants.*;

public class InputManager {
	public void manageInput(Player player ){

		if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
			Worms.switchCameraStatus();
		}
		
		if(player.getStep() == 1){
		float horizontalForce = 0f;
		float verticalForce = 0f;
			if ( validateMovement(player.getPlayer().getPosition().x, player.getStartingPosition())){
				if ( Gdx.input.isKeyPressed(Input.Keys.LEFT)){
					horizontalForce = -1f;
				}
				
				if ( Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
					horizontalForce = 1f;
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
	public Vector3 manageInput(Vector3 pos, boolean a){
		int vel = 4;
		if (a){
			if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
				Worms.switchCameraStatus();
			}
		}
		if (!a){ 
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				Worms.shootMissile();
				Worms.switchCameraStatus();
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
