package com.worms.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.physics.box2d.World;
import com.worms.bars.ChargeBar;
import com.worms.projectiles.Grenade;

public class PlayerTest {

	World world;
	Worm player;
	static { System.loadLibrary("gdx");}
	
	@Before
	public void create(){
		player = new Worm( "Images/Redworm.png", world, 1, false);
	}
	
	@Test
	public void testPlayer() {
		assertEquals("Empieza con 100 de vida", 100, player.getHealth());
		player.nextStep();
		player.nextStep();
		player.selectWeapon(1);
		assertTrue("Chequea que tenga una granada", player.getWeapon() instanceof Grenade);
		player.nextStep();
		assertNotNull("Chequea que se creo la flecha", player.getArrow());
		player.nextStep();
		for(int i = 0; i < 30; i++){
			player.update();
		}
		assertTrue("Chequea que se este cargando la potencia",((ChargeBar)(player.getBar(3))).getCharge() != 0);
	}

}