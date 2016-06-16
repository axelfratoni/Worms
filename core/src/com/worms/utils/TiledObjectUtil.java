package com.worms.utils;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.physics.box2d.World;

public class TiledObjectUtil {
	
	private World world;
	private ArrayList<GrassTile> grassTiles;
	private ArrayList<DirtTile> dirtTiles;
	
	public TiledObjectUtil(World world ){
	this.world = world;
	grassTiles = new ArrayList<GrassTile>();
	dirtTiles = new ArrayList<DirtTile>();
	}

	public  void parseTiledObjectLayer(MapObjects objects, int i){
		switch (i){
		case 1 : 
			for (MapObject object: objects) {
				grassTiles.add(new GrassTile (object, world));
			}
			break;
		case 2 : 
			for (MapObject object: objects) {
				dirtTiles.add(new DirtTile (object, world));
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
	
	public ArrayList<GrassTile> getGrassTiles(){
		return grassTiles;
	}
	
	public ArrayList<DirtTile> getDirtTiles(){
		return dirtTiles;
	}
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
