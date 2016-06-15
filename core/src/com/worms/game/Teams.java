package com.worms.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.physics.box2d.World;
import com.worms.bars.Bar;

public class Teams {
	
	private static ArrayList<Player> team1;
	private static ArrayList<Player> team2;
	private static int team1size;
	private static int team2size;
	private static int TEAMS_TURN = 0;
	private static int CHAR1_TURN = 0;
	private static int CHAR2_TURN = 0;
	
	public static void createTeams(World world) {
		team1 = new ArrayList<Player>();
		team2 = new ArrayList<Player>();
		
		team1.add(new Player( 198, 300, "Images/Redworm.png", world, 1, false));
		team1.add(new Player( 772, 337, "Images/Redworm.png", world, 1, true));
		team1.add(new Player( 430, 700, "Images/Redworm.png", world, 1, false));
		team1.add(new Player( 1400, 300, "Images/Redworm.png", world, 1, true));
		
		team2.add(new Player( 644 , 600, "Images/Blueworm.png", world, 2, false));
		team2.add(new Player( 1100, 700, "Images/Blueworm.png", world, 2, true));
		team2.add(new Player( 246, 432, "Images/Blueworm.png", world, 2, false));
		team2.add(new Player( 1400, 432, "Images/Blueworm.png", world, 2, true));
		
		team1size = team1.size();
		team2size = team2.size();
	}
	
	public static ArrayList<Player> getTeam(int i){
		switch (i){
		case 1 : return team1;
		case 2 : return team2;
		default : return null;
		}
	}
	public static boolean someoneLost(){
		return (team1.size() == 0) || (team2.size() == 0);
	}
	
	public static Player getPlayerWhoseTurnItIs(){
		if ( TEAMS_TURN % 2 == 0){
			return team1.get(CHAR1_TURN % team1.size());
		} else {
			return team2.get(CHAR2_TURN % team2.size());
		}
	}
	
	public static void updateTurn(){
		if ( team1.size() < team1size){
			CHAR1_TURN -= team1size - team1.size();
			team1size = team1.size();
			
		}
		if ( team2.size() < team2size){
			CHAR2_TURN -= team2size - team2.size();
			team2size = team2.size();
		}
		if (TEAMS_TURN % 2 == 0){
			CHAR1_TURN ++;
		} else {
			CHAR2_TURN++;
		}
		
		TEAMS_TURN++;
		System.out.println("Team turn: " + TEAMS_TURN + " Char1 turn: " + CHAR1_TURN + " Char2 turn: " + CHAR2_TURN);
	}
	
 	public static void checkPlayersHealth(){
 		Player p;
 		for (Iterator<Player> it = team1.iterator(); it.hasNext(); ) {
 		
 		    p = it.next();
 		    if ( p.getHealth() <= 0) {
 				p.dispose(false);
 				it.remove();
 		    }
 		}
 		for (Iterator<Player> it = team2.iterator(); it.hasNext(); ) {
 			
 		    p = it.next();
 		    if ( p.getHealth() <=0) {
 				p.dispose(false);
 		        it.remove();
 		    }
 		}
 		}
 	
	public static void dispose(){
 		Player p;
 		for (Iterator<Player> it = team1.iterator(); it.hasNext(); ) {
		    p = it.next();
		    p.dispose(false);
			it.remove();
 		}
 		for (Iterator<Player> it = team2.iterator(); it.hasNext(); ) {
 		    p = it.next();
			p.dispose(false);
	        it.remove();
 		    }
 		}
}
