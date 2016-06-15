package com.worms.game;

import static com.worms.utils.Constants.MOVEMENT_LIMIT;
import static com.worms.utils.Constants.PPM;
import static com.worms.utils.Constants.SCALE;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.worms.bars.ChargeBar;
import com.worms.drawables.Draw;
import com.worms.drawables.DrawableArrow;
import com.worms.drawables.DrawableBar;
import com.worms.drawables.DrawableExplosion;
import com.worms.drawables.DrawablePlayer;
import com.worms.drawables.DrawableProjectile;
import com.worms.drawables.DrawableTile;
import com.worms.projectiles.Explosion;
import com.worms.projectiles.Missile;
import com.worms.utils.DebugString;
import com.worms.utils.Tile;
import com.worms.utils.TiledObjectUtilPrueba;

public class Worms extends ApplicationAdapter {
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	private TiledObjectUtilPrueba tiledObjectUtilPrueba;

	private Draw drawsManager;
	private Player playerWhoseTurnItIs;
	private Commands menu;
	
	private InputManager inputManager;
	
	private static ArrayList<Body> bodiesToBeDeleted;
	private int i;
	
	private static Explosion activeExplosion;
	private static boolean isExplosionHappening;
	private static boolean cameraIsLocked;
	private static boolean shoot;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / SCALE, h / SCALE);
		camera.zoom = 1.5f;
		world = new World(new Vector2( 0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();
		world.setContactListener(new WormsContactListener(world));
		
		DebugString.SetSpriteBatch(batch);
		
		map = new TmxMapLoader().load("Maps/map-prueba.tmx");
//		map = new TmxMapLoader().load("Maps/test-map.tmx");
		tmr = new OrthogonalTiledMapRenderer(map);
		
		inputManager = new InputManager();
		
		cameraIsLocked = true;
		shoot = false;
		
		tiledObjectUtilPrueba = new TiledObjectUtilPrueba(world);
//		tiledObjectUtil.parseTiledObjectLayer( map.getLayers().get("collision-layer").getObjects());
//		tiledObjectUtil.parseTiledObjectLayer( map.getLayers().get("collision-layer2").getObjects());
//		tiledObjectUtilPrueba.parseTiledObjectLayer( map.getLayers().get("collision-layer3").getObjects());
		tiledObjectUtilPrueba.parseTiledObjectLayer( map.getLayers().get("grass-layer").getObjects(), 1);
		tiledObjectUtilPrueba.parseTiledObjectLayer( map.getLayers().get("dirt-layer").getObjects(), 2);
		tiledObjectUtilPrueba.parseTiledObjectLayer( map.getLayers().get("map-limit").getObjects(), 3);
		drawsManager = new Draw(batch,world);
		
		bodiesToBeDeleted = new ArrayList<Body>();
		i = 0;
		Teams.createTeams(world);
		
		menu = new Commands();
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime()* 0.01f);
		
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tmr.render();
		
		batch.begin();
		teamDraw();
		batch.end();
		
		b2dr.render(world, camera.combined.scl(PPM));
	}
	
	public void cameraUpdate(float delta){
		Vector3 position = camera.position;
		if (cameraIsLocked){
			if ( playerWhoseTurnItIs.getStep() == 5){
				position.x = playerWhoseTurnItIs.getWeapon().getBody().getPosition().x * PPM;
				position.y = playerWhoseTurnItIs.getWeapon().getBody().getPosition().y * PPM;
			} else {
				position.x = playerWhoseTurnItIs.getPlayer().getPosition().x * PPM;
				position.y = playerWhoseTurnItIs.getPlayer().getPosition().y * PPM;
			}
		} else if (playerWhoseTurnItIs.getWeapon() instanceof Missile){
			
			position = inputManager.manageInput(position, false);
			playerWhoseTurnItIs.giveCoordinate(position.x);
			if (shoot){
				playerWhoseTurnItIs.shootMissile();
				shoot = false;
			}
		} else {
			position = inputManager.manageInput(position, true);
		}
		camera.position.set(position);
		
		camera.update();
	}
	
	
	public void update(float delta){
		Teams.checkPlayersHealth();
		if ( Teams.someoneLost()){
			Gdx.app.exit();
		}else {
			playerWhoseTurnItIs = Teams.getPlayerWhoseTurnItIs();
		}
		tiledObjectUtilPrueba.cleanTiles();
		world.step( 1/60f, 6, 2);
		if (playerWhoseTurnItIs.isShooting()){
			playerWhoseTurnItIs.shoot();
		}

		sweepBodies();
		
		if(playerWhoseTurnItIs.getStep() == 0)
			playerWhoseTurnItIs.nextStep();
		
		cameraUpdate(delta);
		tmr.setView(camera);

		playerWhoseTurnItIs.update();
		
		if ( isExplosionHappening){
			if ( activeExplosion.update(delta)){
				playerWhoseTurnItIs.nextStep();
				isExplosionHappening = false;
			} 
		} else if ( cameraIsLocked) {
			inputManager.manageInput(playerWhoseTurnItIs);
		}
		batch.setProjectionMatrix(camera.combined);
	}
	

	
	
	public void teamDraw(){	
		for (Tile t: tiledObjectUtilPrueba.getGrassTiles()){
			DrawableTile dT = new DrawableTile(  t.getTile().getPosition(), t.getTex() );
			drawsManager.drawTile(dT);
		}
		for (Tile t: tiledObjectUtilPrueba.getDirtTiles()){
			DrawableTile dT = new DrawableTile(  t.getTile().getPosition(), t.getTex() );
			drawsManager.drawTile(dT);
		}
		
		for (Player p: Teams.getTeam(1)){
			if(Teams.getPlayerWhoseTurnItIs()!=p )
			p.resetTurn();
			draw(p);
		}
		for (Player p: Teams.getTeam(2)){
			if(Teams.getPlayerWhoseTurnItIs()!=p )
				p.resetTurn();
			draw(p);

		}
		
		if ( isExplosionHappening){
			drawExplosion();
		}
//		DebugString.draw( playerWhoseTurnItIs.getWeapon().getTex().toString(), camera);
		
	}
	
	public void drawExplosion(){
		DrawableExplosion dE = new DrawableExplosion(activeExplosion.getPos(), activeExplosion.getExplRadius(), activeExplosion.getExplTex());
		drawsManager.drawExplosion(dE);
	}
	
	public void draw(Player p){
		Vector2 pos = new Vector2(p.getX(),p.getY());
		DrawablePlayer dp = new DrawablePlayer(p.getPlayer().getPosition(), p.getTex());
		drawsManager.drawPlayer(dp);
		DrawableBar dHB = new DrawableBar(p.getBar(1).getTexAbove(), p.getBar(1).getTexBelow(), new Vector2(pos.x,pos.y+30), 30f, p.getHealth() * 30 / 100, 3f );
		drawsManager.drawBar(dHB);
		drawsManager.drawMenu(new Vector2(pos.x + p.getTex().getWidth() / 2,pos.y + p.getTex().getHeight() / 2), menu.getTex(p.getStep()));
		if ( p.getStep() ==  1){
			DrawableBar dMB = new DrawableBar(p.getBar(2).getTexAbove(), p.getBar(2).getTexBelow(), pos, MOVEMENT_LIMIT * 10, p.getMovement() * 10 , 3f );
			drawsManager.drawBar(dMB);
		}
		if ( p.getStep() ==  2){
			
		}
		if ( p.getStep() >=  3 && p.getStep() < 5 && !(p.getWeapon() instanceof Missile)){
			DrawableArrow dA = new DrawableArrow(p.getPlayer().getPosition(),p.getArrow().getTex(),p.getArrow().getAngle());
			drawsManager.drawArrow(dA);
		}
		if ( p.getStep() ==  4){
			DrawableBar dCB = new DrawableBar(p.getBar(3).getTexAbove(), p.getBar(3).getTexBelow(), pos, 30, ((ChargeBar) p.getBar(3)).getCharge()/2, 3f );
			drawsManager.drawBar(dCB);
		}
		if ( p.getStep() ==  5 && p.isShooting() == false){
			DrawableProjectile dPr;
			if (p.getWeapon() instanceof Missile)
				 dPr = new DrawableProjectile(new Vector2(p.getWeapon().getX(),p.getWeapon().getY()),p.getWeapon().getTex());
			else 
				 dPr = new DrawableProjectile(new Vector2(p.getWeapon().getX(),p.getWeapon().getY()),p.getWeapon().getTex(), p.getArrow().getAngle());
			drawsManager.drawProjectile(dPr);
		}
		
	}
	
	public void dispose(){
		world.dispose();
		b2dr.dispose();
		Teams.dispose();
		map.dispose();
		tmr.dispose();
	}
	
	
	
	public void sweepBodies(){
		for (; i < bodiesToBeDeleted.size(); i++){
			bodiesToBeDeleted.get(i).setActive(false);;
			world.destroyBody(bodiesToBeDeleted.get(i));
		}
	}
	
	public static void setExplosion(Explosion e){
		isExplosionHappening = true;
		activeExplosion = e;
	}
	
	public static void switchCameraStatus(){
		if (cameraIsLocked)
			cameraIsLocked = false;
		else
			cameraIsLocked = true;
	}
	
	public static ArrayList<Body> getBodiesToBeDeleted(){
		return bodiesToBeDeleted;
	}
	
	public static void shootMissile(){
		shoot = true;
	}
	
}
