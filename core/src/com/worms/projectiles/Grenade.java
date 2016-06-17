package com.worms.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.Player;

import static com.worms.utils.Constants.*;

public class Grenade extends Projectile{
	
	/**
	 * Instantiates a new grenade.
	 *
	 * @param world the world
	 * @param p the p
	 */
	public Grenade( World world, Player p) {
		super( E_RADIUS_GRENADE, world, new Texture("Images/Grenade.png"), new Texture("Images/Grenadeexplosion.png"), p, 30f);
		// TODO Auto-generated constructor stub
	}
	
}
