package com.worms.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;
import com.worms.game.Player;

import static com.worms.utils.Constants.BIT_PLAYER;
import static com.worms.utils.Constants.BIT_PROJECTILE;
import static com.worms.utils.Constants.BIT_WALL;
import static com.worms.utils.Constants.E_RADIUS_MISSILE;
import static com.worms.utils.Constants.PPM;

public class Missile extends Projectile{
	private World world;
	
	public Missile( World world, Player p) {
		super(E_RADIUS_MISSILE, world, new Texture("Images/Missile.png") , new Texture("Images/Missileexplosion.png"), p, 45f);
		this.world = world;
	}
	
	public void shoot( float x){
		System.out.println(x);
		super.flagForDeletion();
		super.setBody(BodyCreators.createBox( x * PPM, 900, super.getTex().getWidth(), super.getTex().getHeight(), false, true, world, (short) BIT_PROJECTILE, (short) (BIT_PLAYER | BIT_WALL), (short) 0 , this));
	}

}
