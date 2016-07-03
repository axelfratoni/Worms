package com.worms.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.worms.game.Worm;
import com.worms.projectiles.Grenade;

public class WormTest {

	Worm player;
	
	@Before
	public void create(){
		player = new Worm( "Images/Redworm.png", 1, false);
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
		for(int i = 0; i < 30; i++){
			player.update();
		}
		assertTrue("Chequea que se este cargando la potencia",player.getCharge() != 0);
		player.seppuku(40);
		assertTrue("Chequea el daño recibido",player.getHealth() == 60);
		
	}

}