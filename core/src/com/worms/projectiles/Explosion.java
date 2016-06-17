package com.worms.projectiles;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.game.Player;
import com.worms.game.Teams;
import com.worms.states.GameState;
import com.worms.utils.Tile;


public class Explosion {
	private float explRadius;
	private float damage;
	private World world;
	private Vector2 pos;
	private Texture explTex;
	private float time;

	public Explosion(float explRadius, float damage, Vector2 pos, World world, Texture tex) {
		GameState.setExplosion(this);
		
		this.world = world;
		this.damage = damage;
		this.explRadius = explRadius;
		this.pos = pos;
		explTex = tex;
		time = 0;
		
		getObjectsInRange(pos.x-explRadius/2,pos.y-explRadius/2,pos.x+explRadius/2,pos.y+explRadius/2);
	}
	
	public boolean update(float delta){
		time+=delta;

//		return false;
		if (time >= 0.01f){
			Teams.updateTurn();
			
			return true;
		} else {
			return false;
		}
	}
	
	public void getObjectsInRange(float x, float y, float x2, float y2) {
		world.QueryAABB(new QueryCallback() {
	        @Override
	        public boolean reportFixture(Fixture fixture) {
        		if(fixture.getUserData() instanceof Player){
        			Player p;
        			p = (Player) fixture.getUserData();
        			System.out.println("Player: " + p.getPlayer().getPosition().x + " " + p.getPlayer().getPosition().y + " Expl: " + pos.x + " " + pos.y);
        			if ( getDistance(p.getPlayer().getPosition(), pos) <= explRadius){
        				p.seppuku(damage);
        				applyForce(p);
        			}
        			return true;
        		} else if (fixture.getUserData() instanceof Tile){
        			Tile t;
        			t = (Tile) fixture.getUserData();

        			if ( getDistance(t.getTile().getPosition(), pos) <= explRadius){
        				t.flagForDeletion();
        			}
        		}
    		return true;
	        }
	    }, Math.min(x, x2), Math.min(y, y2), Math.max(x, x2), Math.max(y, y2));
	}
	
	public void applyForce(Player player){
		float playPosX = player.getPlayer().getPosition().x;
		float playPosY = player.getPlayer().getPosition().y;
		
		float dist = getDistance(player.getPlayer().getPosition(), pos);
		float angle = MathUtils.atan2(playPosY - pos.y, playPosX - pos.x);
		float force = (1000 - 1000 * dist / explRadius);
		
		float Xforce = force * MathUtils.cos(angle);
		float Yforce = force * MathUtils.sin( angle);
		
		if(Yforce < 200) 
			Yforce = 200;
		
		System.out.println(" Fuerza X:" + Xforce + " Fuerza Y: " + Yforce);
		
		player.getPlayer().applyForceToCenter( Xforce , Yforce, false);
	}
	
	
	public float getExplRadius() {
		return explRadius;
	}

	public float getDamage() {
		return damage;
	}

	public Vector2 getPos() {
		return pos;
	}

	public Texture getExplTex() {
		return explTex;
	}

	
	public void dispose(){
		explTex.dispose();
	}
	private float getDistance( Vector2 pos1, Vector2 pos2){
		return (float) Math.sqrt( Math.pow(pos1.x - pos2.x,2) + Math.pow( pos1.y - pos2.y,2) ) ; 
	}

	
}
