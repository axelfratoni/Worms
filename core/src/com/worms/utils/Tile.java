package com.worms.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;
import com.worms.states.GameState;

import static com.worms.utils.Constants.*;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tile implements Serializable{
	private transient Body tile;
	private transient Texture tex;
	private boolean deletionFlag;
	private String texurl;
	private Rectangle rect;
	
	/**
	 * Instantiates a new tile.
	 *
	 * @param object the object
	 * @param world the world
	 * @param texURL the tex url
	 */
	public Tile(MapObject object, World world, String texURL){
		texurl = texURL;
		deletionFlag = false;
		rect = ((RectangleMapObject)object).getRectangle();
		setTile(world);
	}
	
	/**
	 * Flag for deletion.
	 */
	public void flagForDeletion(){
		deletionFlag = true;
	}
	
	/**
	 * Checks if is flagged for deletion.
	 *
	 * @return true, if is flagged for deletion
	 */
	public boolean isFlaggedForDeletion(){
		return deletionFlag;
	}
	
	/**
	 * Gets the tile.
	 *
	 * @return the tile
	 */
	public Body getTile(){
		return tile;
	}
	
	/**
	 * Gets the tex.
	 *
	 * @return the tex
	 */
	public Texture getTex(){
		return tex;
	}
	
	/**
	 * Dispose.
	 */
	public void dispose(){
		GameState.getBodiesToBeDeleted().add(tile);
		tex.dispose();
	}
	
	/**
	 * Sets the tile.
	 *
	 * @param world the new tile
	 */
	public void setTile(World world){
		this.tex = new Texture(texurl);
		this.tile = BodyCreators.createBox(rect.getX()+rect.getWidth()/2, rect.getY() + rect.getHeight()/2, rect.getWidth(), rect.getHeight(), true, true, world, (BIT_WALL), (short) (BIT_PLAYER | BIT_PROJECTILE), (short) 0, this);
	}
			
}
