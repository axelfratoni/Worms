package com.worms.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import static com.worms.utils.Constants.*;

public class Teams {
	
	private static ArrayList<Worm> team1;
	private static ArrayList<Worm> team2;
	private static int team1size;
	private static int team2size;
	private static int TEAMS_TURN = 0;
	private static int CHAR1_TURN = 0;
	private static int CHAR2_TURN = 0;
	
	/**
	 * Creates the teams.
	 *
	 * @param world the world
	 * @return true, if successful
	 */
	public static boolean createTeams(World world) {
		
	if(team1 == null && team2 == null){
		team1 = new ArrayList<Worm>();
		team2 = new ArrayList<Worm>();
	
		team1.add(new Worm( "Images/Redworm.png", world, 1, false));
		team1.get(0).setBody(setBody( 198, 300, team1.get(0), world));
		team1.add(new Worm( "Images/Redworm.png", world, 1, true));
		team1.get(1).setBody(setBody( 772, 337, team1.get(1), world));
		team1.add(new Worm( "Images/Redworm.png", world, 1, false));
		team1.get(2).setBody(setBody( 430, 700, team1.get(2), world));
		team1.add(new Worm( "Images/Redworm.png", world, 1, true));
		team1.get(3).setBody(setBody( 1400, 300, team1.get(3), world));
	
		team2.add(new Worm( "Images/Blueworm.png", world, 2, false));
		team2.get(0).setBody(setBody( 644, 600, team2.get(0), world));
		team2.add(new Worm( "Images/Blueworm.png", world, 2, true));
		team2.get(1).setBody(setBody( 1100, 700, team2.get(1), world));
		team2.add(new Worm( "Images/Blueworm.png", world, 2, false));
		team2.get(2).setBody(setBody( 246, 432, team2.get(2), world));
		team2.add(new Worm( "Images/Blueworm.png", world, 2, true));
		team2.get(3).setBody(setBody( 1400, 432, team2.get(3), world));
	
		team1size = team1.size();
		team2size = team2.size();
		return true;
	}
	return false;
	}
	
	public static Body setBody(float x, float y, Worm w, World world){
		return BodyCreators.createBox( x, y, (float) 32, (float) 32, false, true, world, BIT_PLAYER, (short) (BIT_WALL | BIT_PROJECTILE | BIT_EXPLOSION), (short) 0, w);
	}
	/**
	 * Gets the team.
	 *
	 * @param i the i
	 * @return the team
	 */
	public static ArrayList<Worm> getTeam(int i){
		switch (i){
		case 1 : return team1;
		case 2 : return team2;
		default : return null;
		}
	}
	
	/**
	 * Someone lost.
	 *
	 * @return true, if successful
	 */
	public static boolean someoneLost(){
		return (team1.size() == 0) || (team2.size() == 0);
	}
	
	/**
	 * Gets the player whose turn it is.
	 *
	 * @return the player whose turn it is
	 */
	public static Worm getPlayerWhoseTurnItIs(){
		if ( TEAMS_TURN % 2 == 0){
			return team1.get(CHAR1_TURN % team1.size());
		} else {
			return team2.get(CHAR2_TURN % team2.size());
		}
	}
	
	/**
	 * Update turn.
	 */
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
	}
	
 	/**
	  * Check players health.
	  */
	 public static void checkPlayersHealth(){
 		Worm p;
 		for (Iterator<Worm> it = team1.iterator(); it.hasNext(); ) {
 		
 		    p = it.next();
 		    if ( p.getHealth() <= 0) {
 				p.dispose(false);
 				it.remove();
 		    }
 		}
 		for (Iterator<Worm> it = team2.iterator(); it.hasNext(); ) {
 			
 		    p = it.next();
 		    if ( p.getHealth() <=0) {
 				p.dispose(false);
 		        it.remove();
 		    }
 		}
 		}
 	
	/**
	 * Dispose.
	 */
	public static void dispose(){
 		Worm p;
 		for (Iterator<Worm> it = team1.iterator(); it.hasNext(); ) {
		    p = it.next();
		    p.dispose(false);
			it.remove();
 		}
 		for (Iterator<Worm> it = team2.iterator(); it.hasNext(); ) {
 		    p = it.next();
			p.dispose(false);
	        it.remove();
 		    }
 		}
	
	/**
	 * Load teams.
	 *
	 * @param t1 the t1
	 * @param t2 the t2
	 * @param posT1 the pos t1
	 * @param posT2 the pos t2
	 * @param world the world
	 */
	public static void loadTeams(ArrayList<Worm> t1, ArrayList<Worm> t2,ArrayList<Vector2> posT1,ArrayList<Vector2> posT2, World world){
		team1 = t1;
		team2 = t2;
		loadPlayers(t1,posT1, world);
		loadPlayers(t2,posT2, world);
		updateTurn();
	}
	
	/**
	 * Load players.
	 *
	 * @param team the team
	 * @param posTeam the pos team
	 * @param world the world
	 */
	public static void loadPlayers(ArrayList<Worm> team, ArrayList<Vector2> posTeam, World world){
		Worm p;
		Vector2 pos;
		Iterator<Vector2> itP = posTeam.iterator();
		Iterator<Worm> it = team.iterator();
		for (; it.hasNext() && itP.hasNext(); ) {
		    p = it.next();
		    pos = itP.next();
//		    p.setPlayer(pos.x, pos.y, world);
 		}
	}
	
	/**
	 * Gets the pos team.
	 *
	 * @param i the i
	 * @return the pos team
	 */
	public static ArrayList<Vector2> getPosTeam(int i){
		Worm P;
		Vector2 Pos;
		ArrayList<Worm> team = (i == 1)? team1 : team2;
		ArrayList <Vector2> PositionTeam = new ArrayList<Vector2>();
		for (Iterator<Worm> it = team.iterator(); it.hasNext(); ) {
	 		P = it.next();
	 		Pos = new Vector2(P.getX(),P.getY());
	 		PositionTeam.add(Pos);
 		}
		return PositionTeam;
	}
}