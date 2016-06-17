package com.worms.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.projectiles.Projectile;
import com.worms.states.GameState;
import com.worms.utils.MapLimit;
import com.worms.utils.Tile;

public class WormsContactListener implements ContactListener{
	World world;
	
	public WormsContactListener(World world){
		this.world = world;
	}
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa == null || fb == null) return;
		if (fb.getUserData() == null || fa.getUserData() == null) return;
		
		
		if (isProjectilePlayerContact( fa, fb)){
			Projectile g;
			if ( fb.getUserData() instanceof Projectile){
				g = (Projectile) fb.getUserData();
			} else {
				g = (Projectile) fa.getUserData();
			}
			if ( !g.getDeletionFlag()){
				g.explode();
				GameState.getBodiesToBeDeleted().add(g.getBody());
			}
		} else if (isProjectileTileContact( fa, fb)){
			Projectile g;
			if ( fa.getUserData() instanceof Projectile){
				g = (Projectile) fa.getUserData();
			} else {
				g = (Projectile) fb.getUserData();
			}
			if ( !g.getDeletionFlag()){
				g.explode();
				GameState.getBodiesToBeDeleted().add(g.getBody());
			}
		} else if (isMapLimitContact(fa,fb)){
			if ( fa.getUserData() instanceof MapLimit){
				if ( fb.getUserData() instanceof Projectile){
					GameState.getBodiesToBeDeleted().add(((Projectile)fb.getUserData()).getBody());
					Teams.updateTurn();
				} else if ( fb.getUserData() instanceof Player){
					if (Teams.getPlayerWhoseTurnItIs().equals((Player)fb.getUserData()))
						Teams.updateTurn();
					((Player)fb.getUserData()).dispose(true);
				}
			} else {
				if ( fa.getUserData() instanceof Projectile){
					GameState.getBodiesToBeDeleted().add(((Projectile)fa.getUserData()).getBody());
					Teams.updateTurn();
				} else if ( fa.getUserData() instanceof Player){
					if (Teams.getPlayerWhoseTurnItIs().equals((Player)fa.getUserData()))
						Teams.updateTurn();
					((Player)fa.getUserData()).dispose(true);
				}
			}
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

//	Template
//	private boolean is Contact( Fixture a, Fixture b){
//	return ((a.getUserData() instanceof  && b.getUserData() instanceof ) || (b.getUserData() instanceof  && a.getUserData() instanceof ) );
//}
	
	private boolean isMapLimitContact( Fixture a, Fixture b){
	return ((a.getUserData() instanceof MapLimit || b.getUserData() instanceof MapLimit));
}
	private boolean isProjectileTileContact( Fixture a, Fixture b){
		return ((a.getUserData() instanceof Projectile && b.getUserData() instanceof Tile) || (b.getUserData() instanceof Projectile && a.getUserData() instanceof Tile) );
	}
	
	private boolean isProjectilePlayerContact( Fixture a, Fixture b){
		return ((a.getUserData() instanceof Player && b.getUserData() instanceof Projectile) || (b.getUserData() instanceof Player && a.getUserData() instanceof Projectile) );
	}

	
}
