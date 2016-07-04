package com.worms.utils;

import static com.worms.utils.Constants.BIT_PLAYER;
import static com.worms.utils.Constants.BIT_PROJECTILE;
import static com.worms.utils.Constants.BIT_WALL;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;

// TODO: Auto-generated Javadoc
public class TiledObjectUtil {
	
	private World world;
	private ArrayList<GrassTile> grassTiles;
	private ArrayList<DirtTile> dirtTiles;
	
	/**
	 * Instantiates a new tiled object util.
	 *
	 * @param world the world
	 */
	public TiledObjectUtil(World world ){
	this.world = world;
	grassTiles = new ArrayList<GrassTile>();
	dirtTiles = new ArrayList<DirtTile>();
	}

	/**
	 * Parses the tiled object layer.
	 *
	 * @param objects the objects
	 * @param i the i
	 */
	public void parseTiledObjectLayer(MapObjects objects, int i){
		switch (i){
		case 1 : 
			for (MapObject object: objects) {
				GrassTile gt = new GrassTile(((RectangleMapObject)object).getRectangle());
				gt.setBody(world);
				grassTiles.add(gt);
			}
			break;
		case 2 : 
			for (MapObject object: objects) {
				DirtTile dt = new DirtTile(((RectangleMapObject)object).getRectangle());
				dt.setBody(world);
				dirtTiles.add(dt);
			}
			break;
		case 3 : 
			for (MapObject object: objects) {
				new MapLimit (object, world);
			}
			break;
		default : break;
		}
	}
	

	
	/**
	 * Creates the tile.
	 *
	 * @param object the object
	 * @param t the t
	 * @return the body
	 */
	private Body createTile(MapObject object, Tile t) {
		//SACAR METODO
		Rectangle rect = ((RectangleMapObject)object).getRectangle();
		return BodyCreators.createBox(rect.getX()+rect.getWidth()/2, rect.getY() + rect.getHeight()/2, rect.getWidth(), rect.getHeight(), true, true, world, (BIT_WALL), (short) (BIT_PLAYER | BIT_PROJECTILE), (short) 0, t);
	}

	/**
	 * Parses the tiled object layer.
	 *
	 * @param dirttiles the dirttiles
	 * @param grasstiles the grasstiles
	 */
	public void parseTiledObjectLayer(ArrayList<DirtTile> dirttiles, ArrayList<GrassTile> grasstiles){
		dirtTiles = dirttiles;
		grassTiles = grasstiles;
		Tile t;
 		for (Iterator<GrassTile> it = grassTiles.iterator(); it.hasNext(); ) {
 		    t = it.next();
 		    t.setBody(world);
 		}
 		for (Iterator<DirtTile> it = dirtTiles.iterator(); it.hasNext(); ) {
 		    t = it.next();
 		    t.setBody(world);
 		}
		
	}
	
	
	/**
	 * Gets the grass tiles.
	 *
	 * @return the grass tiles
	 */
	public ArrayList<GrassTile> getGrassTiles(){
		return grassTiles;
	}
	
	/**
	 * Gets the dirt tiles.
	 *
	 * @return the dirt tiles
	 */
	public ArrayList<DirtTile> getDirtTiles(){
		return dirtTiles;
	}
	
	/**
	 * Clean tiles.
	 */
	public void cleanTiles(){
		Tile t;
 		for (Iterator<GrassTile> it = grassTiles.iterator(); it.hasNext(); ) {
 	 		
 		    t = it.next();
 		    if (t.isFlaggedForDeletion() ) {
 		    	t.dispose();
 				it.remove();
 		    }
 		}
 		for (Iterator<DirtTile> it = dirtTiles.iterator(); it.hasNext(); ) {
 		    t = it.next();
 		    if (t.isFlaggedForDeletion() ) {
 		    	t.dispose();
 				it.remove();
 		    }
 		}
	}
}
