package com.worms.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.Player;

import static com.worms.utils.Constants.E_RADIUS_BULLET;

public class Bullet extends Projectile{

	/**
	 * Instantiates a new bullet.
	 *
	 * @param world the world
	 * @param p the p
	 */
	public Bullet(World world, Player p) {
		super( E_RADIUS_BULLET , world, new Texture("Images/Bullet.png"), new Texture("Images/Bulletexplosion.png"), p, 15f);
		// TODO Auto-generated constructor stub
	}
	

	/* (non-Javadoc)
	 * @see com.worms.projectiles.Projectile#shoot(com.badlogic.gdx.math.Vector2, float, float)
	 */
	public void shoot(Vector2 pos, float force, float angle){
		super.shoot(pos, 100, angle);
		super.getBody().setGravityScale(0);
	}
}
