package com.worms.drawables;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.worms.bars.ChargeBar;
import com.worms.game.Commands;
import com.worms.game.Worm;
import com.worms.projectiles.Missile;

import static com.worms.utils.Constants.MOVEMENT_LIMIT;
import static com.worms.utils.Constants.PPM;

import java.util.HashMap;

public class Draw {
	private SpriteBatch batch;
	private HashMap<String, Texture> textures;
	private HashMap<String, Sprite> sprites;
	private Commands menu;
	/**
	 * Instantiates a new draw.
	 *
	 * @param batch the batch
	 * @param world the world
	 */
	public Draw( SpriteBatch batch){
				this.batch = batch;
				menu = new Commands();
				
				textures = new HashMap<String,Texture>();
				sprites = new HashMap<String,Sprite>();
				textures.put("Blueworm.png" , new Texture("Images/Blueworm.png"));
				textures.put("Redworm.png", new Texture("Images/Redworm.png"));
				textures.put("Dirttile.png" , new Texture("Images/Dirttile.png"));
				textures.put("Grasstile.png" , new Texture("Images/Grasstile.png"));
				textures.put("Crosshair.png" , new Texture("Images/Crosshair.png"));
				textures.put("Bulletexplosion.png", new Texture("Images/Bulletexplosion.png"));
				textures.put("Grenadeexplosion.png", new Texture("Images/Grenadeexplosion.png"));
				textures.put("Missileexplosion.png", new Texture("Images/Missileexplosion.png"));
				
				sprites.put("Bullet.png", new Sprite( new Texture("Images/Bullet.png")));
				sprites.put("Grenade.png", new Sprite( new Texture("Images/Grenade.png")));
				sprites.put("Missile.png", new Sprite( new Texture("Images/Missile.png")));
				sprites.put("LongArrow.png" , new Sprite( new Texture("Images/LongArrow.png")));
	}
	
	public void draw(Worm p){
		Vector2 pos = new Vector2(p.getX(),p.getY());
		DrawablePlayer dp = new DrawablePlayer(p.getPlayer().getPosition(), p.getTeam());
		drawPlayer(dp);
		DrawableBar dHB = new DrawableBar(p.getBar(1).getTexAbove(), p.getBar(1).getTexBelow(), new Vector2(pos.x,pos.y+30), 30f, p.getHealth() * 30 / 100, 3f );
		drawBar(dHB);
		drawMenu(new Vector2(pos.x ,pos.y ), menu.getTex(p.getStep(), p.hasSpecialProjectile()));
		if ( p.getStep() ==  1){
			DrawableBar dMB = new DrawableBar(p.getBar(2).getTexAbove(), p.getBar(2).getTexBelow(), pos, MOVEMENT_LIMIT * 10, p.getMovement() * 10 , 3f );
			drawBar(dMB);
		}
		if ( p.getStep() ==  2){
			
		}
		if ( p.getStep() >=  3 && p.getStep() < 5 && !(p.getWeapon() instanceof Missile)){
			DrawableArrow dA = new DrawableArrow(p.getPlayer().getPosition(),p.getArrow().getAngle());
			drawArrow(dA);
		}
		if ( p.getStep() ==  4){
			DrawableBar dCB = new DrawableBar(p.getBar(3).getTexAbove(), p.getBar(3).getTexBelow(), pos, 30, ((ChargeBar) p.getBar(3)).getCharge()/2, 3f );
			drawBar(dCB);
		}
		if ( p.getStep() ==  5 && p.isShooting() == false){
			DrawableProjectile dPr;
			if (p.getWeapon() instanceof Missile)
				 dPr = new DrawableProjectile(new Vector2(p.getWeapon().getX(),p.getWeapon().getY()), p.getWeapon(), 0);
			else 
				 dPr = new DrawableProjectile(new Vector2(p.getWeapon().getX(),p.getWeapon().getY()), p.getWeapon(), p.getArrow().getAngle());
			drawProjectile(dPr);
		}
		
	}
	
	
	/**
	 * Draw player.
	 *
	 * @param player the player
	 */
	public void drawPlayer( DrawablePlayer player ){
		batch.draw(textures.get(player.getPlayersTeam()), player.getPos().x * PPM - textures.get(player.getPlayersTeam()).getWidth() / 2, player.getPos().y * PPM- textures.get(player.getPlayersTeam()).getHeight() / 2);
	}
	
	/**
	 * Draw arrow.
	 *
	 * @param arrow the arrow
	 */
	public void drawArrow( DrawableArrow arrow ){
		String arrows = "LongArrow.png";
		sprites.get(arrows).setPosition(arrow.getPos().x * PPM - sprites.get(arrows).getWidth() / 2, arrow.getPos().y * PPM - sprites.get(arrows).getHeight() / 2);
		sprites.get(arrows).setRotation(arrow.getAngle());
		sprites.get(arrows).draw(batch);
	}
	
	/**
	 * Draw bar.
	 *
	 * @param bar the bar
	 */
	public void drawBar ( DrawableBar bar){
		batch.draw( bar.getBarBelow(), bar.getPos().x, bar.getPos().y, bar.getValMax(), 3);
	    batch.draw( bar.getBarAbove(), bar.getPos().x, bar.getPos().y, bar.getVal(), 3);
	}
	
	/**
	 * Draw projectile.
	 *
	 * @param projectile the projectile
	 */
	public void drawProjectile( DrawableProjectile projectile){
		String type = projectile.getTypeOfProjectile();
		sprites.get(type).setPosition(projectile.getPos().x  , projectile.getPos().y);
		sprites.get(type).setRotation(projectile.getAngle());
		sprites.get(type).draw(batch);
	}
	
	/**
	 * Draw menu.
	 *
	 * @param pos the pos
	 * @param tex the tex
	 */
	public void drawMenu(Vector2 pos, Texture tex){
		
		if(tex != null){
			pos.x = pos.x  + Gdx.graphics.getWidth() / 2 - 360;
			pos.y = pos.y  - Gdx.graphics.getHeight() / 2 + 90;
			batch.draw(tex,pos.x,pos.y,200,60);
		}
	}
	
	/**
	 * Draw explosion.
	 *
	 * @param explosion the explosion
	 */
	public void drawExplosion(DrawableExplosion explosion){
		batch.draw( textures.get(explosion.getExplosionType()), explosion.getPos().x * PPM - textures.get(explosion.getExplosionType()).getWidth() / 2, explosion.getPos().y * PPM - textures.get(explosion.getExplosionType()).getHeight() / 2);
	}
	
	/**
	 * Draw tile.
	 *
	 * @param tile the tile
	 */
	public void drawTile(DrawableTile tile){
		batch.draw( textures.get(tile.getTypeOfTile()), tile.getPos().x * PPM - textures.get(tile.getTypeOfTile()).getWidth() / 2, tile.getPos().y * PPM - textures.get(tile.getTypeOfTile()).getHeight() / 2);	}
	
}
