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
	
	public Tile(MapObject object, World world, String texURL){
		texurl = texURL;
		deletionFlag = false;
		rect = ((RectangleMapObject)object).getRectangle();
		setTile(world);
	}
	
	public void flagForDeletion(){
		deletionFlag = true;
	}
	
	public boolean isFlaggedForDeletion(){
		return deletionFlag;
	}
	
	public Body getTile(){
		return tile;
	}
	public Texture getTex(){
		return tex;
	}
	
	public void dispose(){
		GameState.getBodiesToBeDeleted().add(tile);
		tex.dispose();
	}
	
	public void setTile(World world){
		this.tex = new Texture(texurl);
		this.tile = BodyCreators.createBox(rect.getX()+rect.getWidth()/2, rect.getY() + rect.getHeight()/2, rect.getWidth(), rect.getHeight(), true, true, world, (BIT_WALL), (short) (BIT_PLAYER | BIT_PROJECTILE), (short) 0, this);
	}
			
}
