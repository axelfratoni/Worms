package com.worms.drawables;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import static com.worms.utils.Constants.PPM;

public class Draw {
	SpriteBatch batch;
	World world;
	
	public Draw( SpriteBatch batch, World world){
				this.batch = batch;
				this.world = world;
	}
	
	
	public void drawPlayer( DrawablePlayer player ){
		batch.draw(player.getTex(), player.getPos().x * PPM - player.getTex().getWidth() / 2, player.getPos().y * PPM- player.getTex().getHeight() / 2);
	}
	
	public void drawArrow( DrawableArrow arrow ){
		arrow.getSprite().setPosition(arrow.getPos().x * PPM - arrow.getSprite().getWidth() / 2, arrow.getPos().y * PPM - arrow.getSprite().getHeight() / 2 );
		arrow.getSprite().setRotation(arrow.getAngle() );
		arrow.getSprite().draw(batch);
	}
	
	public void drawBar ( DrawableBar bar){
		batch.draw( bar.getBarBelow(), bar.getPos().x, bar.getPos().y, bar.getValMax(), 3);
	    batch.draw( bar.getBarAbove(), bar.getPos().x, bar.getPos().y, bar.getVal(), 3);
	}
	
	public void drawProjectile( DrawableProjectile projectile){
		projectile.getSprite().setPosition(projectile.getPos().x  , projectile.getPos().y  );
		projectile.getSprite().setRotation(projectile.getAngle());
		projectile.getSprite().draw(batch);
	}
	
	public void drawMenu(Vector2 pos, Texture tex){
		
		if(tex != null){
			pos.x = pos.x  + Gdx.graphics.getWidth() / 2 - 360;
			pos.y = pos.y  - Gdx.graphics.getHeight() / 2 + 90;
			batch.draw(tex,pos.x,pos.y,200,60);
		}
	}
	
	public void drawExplosion(DrawableExplosion explosion){
		batch.draw( explosion.getTex(), explosion.getPos().x * PPM - explosion.getTex().getWidth() / 2, explosion.getPos().y * PPM - explosion.getTex().getHeight() / 2);
	}
	
	public void drawTile(DrawableTile tile){
		if (tile.getTex() != null)
		batch.draw( tile.getTex(), tile.getPos().x * PPM - tile.getTex().getWidth() / 2, tile.getPos().y * PPM - tile.getTex().getHeight() / 2);
	}
	
}
