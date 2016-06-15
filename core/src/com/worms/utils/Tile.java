package com.worms.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.BodyCreators;
import com.worms.game.Worms;

import static com.worms.utils.Constants.*;

public class Tile {
	private Body tile;
	private Texture tex;
	private boolean deletionFlag;
	public Tile(MapObject object, World world, Texture tex){
		Rectangle rect = ((RectangleMapObject)object).getRectangle();
		tile = BodyCreators.createBox(rect.getX()+rect.getWidth()/2, rect.getY() + rect.getHeight()/2, rect.getWidth(), rect.getHeight(), true, true, world, (BIT_WALL), (short) (BIT_PLAYER | BIT_PROJECTILE), (short) 0, this);
		this.tex = tex;
		deletionFlag = false;
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
		Worms.getBodiesToBeDeleted().add(tile);
		tex.dispose();
	}
			
}
