package com.worms.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.physics.box2d.World;
import com.worms.projectiles.Grenade;

public class PlayerTest {

	World world;
	Worm player;
	
	@Before
	public void create(){
		player = new Worm( "Images/Redworm.png", world, 1, false);
	}
	
	@Test
	public void testPlayer() {
		assertEquals(100,player.getHealth(),1);
		player.nextStep();
		player.nextStep();
		player.selectWeapon(1);
		assertTrue("Chequea que tenga una granada", player.getWeapon() instanceof Grenade);
		player.nextStep();
		assertNotNull("Chequea que se creo la flecha", player.getArrow());
		player.nextStep();
	}

}