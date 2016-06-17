package com.worms.states;

import static com.worms.utils.Constants.MOVEMENT_LIMIT;
import static com.worms.utils.Constants.PPM;
import static com.worms.utils.Constants.SCALE;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.worms.game.Commands;
import com.worms.game.InputManager;
import com.worms.game.Player;
import com.worms.game.Teams;
import com.worms.game.WormsContactListener;
import com.worms.projectiles.Explosion;
import com.worms.projectiles.Missile;
import com.worms.utils.DebugString;
import com.worms.utils.DirtTile;
import com.worms.utils.GrassTile;
import com.worms.utils.Tile;
import com.worms.utils.TiledObjectUtil;

public class GameState{
	
	private World world;
	private Box2DDebugRenderer b2dr;

	private boolean end;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture crosshair;
	
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	private TiledObjectUtil tiledObjectUtil;

	private Draw drawsManager;
	private Player playerWhoseTurnItIs;
	
	private Commands menu;
	
	private InputManager inputManager;
	
	
	private static Explosion activeExplosion;
	private static ArrayList<Body> bodiesToBeDeleted;
	private int i;
	private static boolean isExplosionHappening;
	private static boolean cameraIsLocked;
	private static boolean shoot;
	
	
	/**
	 * Instantiates a new game state.
	 *
	 * @param batch the batch
	 * @param loadPath the load path
	 */
	public GameState(SpriteBatch batch, String loadPath) {
		this.batch = batch;
		end = false;
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
		
		crosshair = new Texture("Images/Crosshair.png");
		inputManager = new InputManager();
		
		cameraIsLocked = true;
		shoot = false;
		
		tiledObjectUtil = new TiledObjectUtil(world);
		tiledObjectUtil.parseTiledObjectLayer( map.getLayers().get("map-limit").getObjects(), 3);
		if(loadPath != null){
			loadPath = LoadGame(loadPath);
		}
		if(loadPath == null){
			tiledObjectUtil.parseTiledObjectLayer( map.getLayers().get("grass-layer").getObjects(), 1);
			tiledObjectUtil.parseTiledObjectLayer( map.getLayers().get("dirt-layer").getObjects(), 2);
			Teams.createTeams(world); /* Si se carga partida comentar 1,2,3 */
		}
		/*(*)*/
		
		
		drawsManager = new Draw(batch,world);
		
		bodiesToBeDeleted = new ArrayList<Body>();
		i = 0;
		Teams.createTeams(world);
		
		menu = new Commands();
	}

	/**
	 * Render.
	 */
	public void render () {
		update(Gdx.graphics.getDeltaTime()* 0.01f);
		
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tmr.render();
		
		batch.begin();
		teamDraw();
		batch.end();
	//	b2dr.render(world, camera.combined.scl(PPM));
	}
	
	/**
	 * Camera update.
	 *
	 * @param delta time in which the camera updates
	 */
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
			
			position = inputManager.manageInput(this, position, false);
			playerWhoseTurnItIs.giveCoordinate(position.x);
			if (shoot){
				playerWhoseTurnItIs.shootMissile();
				shoot = false;
			}
		} else {
			position = inputManager.manageInput(this, position, true);
		}
		camera.position.set(position);
		
		camera.update();
	}
	
	
	/**
	 * Update.
	 *
	 * @param delta time in which the game updates
	 */
	public void update(float delta){
		Teams.checkPlayersHealth();
		if ( Teams.someoneLost()){
			end = true;
		}else {
			playerWhoseTurnItIs = Teams.getPlayerWhoseTurnItIs();
		}
		if(playerWhoseTurnItIs.isSaving()){
			playerWhoseTurnItIs.setSaving(false);
			SaveGame(playerWhoseTurnItIs.getSavePath());
			System.out.println("Se ha guardado partida en: "+ playerWhoseTurnItIs.getSavePath());
		}
		tiledObjectUtil.cleanTiles();
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
			inputManager.manageInput(this, playerWhoseTurnItIs);
		}
		batch.setProjectionMatrix(camera.combined);
	}
	

	
	
	/**
	 * Team draw.
	 */
	public void teamDraw(){	
		for (Tile t: tiledObjectUtil.getGrassTiles()){
			DrawableTile dT = new DrawableTile(  t.getTile().getPosition(), t.getTex() );
			drawsManager.drawTile(dT);
		}
		for (Tile t: tiledObjectUtil.getDirtTiles()){
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
		
		if (!cameraIsLocked && playerWhoseTurnItIs.getWeapon() instanceof Missile){
			batch.draw(crosshair, camera.position.x - 50 , camera.position.y - 50);
		}
		
		if ( isExplosionHappening){
			drawExplosion();
		}
//		DebugString.draw( playerWhoseTurnItIs.getWeapon().getTex().toString(), camera);
		
	}
	
	/**
	 * Draw explosion.
	 */
	public void drawExplosion(){
		DrawableExplosion dE = new DrawableExplosion(activeExplosion.getPos(), activeExplosion.getExplRadius(), activeExplosion.getExplTex());
		drawsManager.drawExplosion(dE);
	}
	
	/**
	 * Draw.
	 *
	 * @param player that's going to be drawn
	 */
	public void draw(Player p){
		Vector2 pos = new Vector2(p.getX(),p.getY());
		DrawablePlayer dp = new DrawablePlayer(p.getPlayer().getPosition(), p.getTex());
		drawsManager.drawPlayer(dp);
		DrawableBar dHB = new DrawableBar(p.getBar(1).getTexAbove(), p.getBar(1).getTexBelow(), new Vector2(pos.x,pos.y+30), 30f, p.getHealth() * 30 / 100, 3f );
		drawsManager.drawBar(dHB);
		drawsManager.drawMenu(new Vector2(pos.x + p.getTex().getWidth() / 2,pos.y + p.getTex().getHeight() / 2), menu.getTex(p.getStep(), p.hasSpecialProjectile()));
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
	
	/**
	 * Dispose.
	 */
	public void dispose(){
		world.dispose();
		b2dr.dispose();
		Teams.dispose();
		map.dispose();
		tmr.dispose();
	}
	
	/**
	 * End.
	 * @return true, if successful
	 */
	public boolean end(){
		return end;
	}
	
	/**
	 * Sweep bodies after the step, checks if any body is going to be deleted.
	 */
	public void sweepBodies(){
		for (; i < bodiesToBeDeleted.size(); i++){
			bodiesToBeDeleted.get(i).setActive(false);;
			bodiesToBeDeleted.get(i).setUserData(null);
			world.destroyBody(bodiesToBeDeleted.get(i));
		}
	}
	
	/**
	 * Sets the explosion.
	 * @param e the new explosion
	 */
	public static void setExplosion(Explosion e){
		isExplosionHappening = true;
		activeExplosion = e;
	}
	
	/**
	 * Switch camera status.
	 */
	public static void switchCameraStatus(){
		if (cameraIsLocked)
			cameraIsLocked = false;
		else
			cameraIsLocked = true;
	}
	
	/**
	 * Gets the bodies to be deleted.
	 * @return the bodies to be deleted
	 */
	public static ArrayList<Body> getBodiesToBeDeleted(){
		return bodiesToBeDeleted;
	}
	
	/**
	 * Shoot missile.
	 */
	public static void shootMissile(){
		shoot = true;
	}

	/**
	 * Save game.
	 *
	 * @param path the path
	 */
	public void SaveGame(String path){
		 try
	      { 
	         FileOutputStream fileOut = new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(Teams.getTeam(1));
	         out.writeObject(Teams.getTeam(2));
	         out.writeObject(Teams.getPosTeam(1));
	         out.writeObject(Teams.getPosTeam(2));
	         out.writeObject(tiledObjectUtil.getDirtTiles());
	         out.writeObject(tiledObjectUtil.getGrassTiles());
	         out.close();
	         fileOut.close();
	      }catch(Exception i)
	      {
	          i.printStackTrace();
	      }
	}
	
	/**
	 * Load game.
	 *
	 * @param path 
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	public String LoadGame(String path){
		ArrayList<Player> t1;
		ArrayList<Player> t2;
		ArrayList<Vector2> posT1;
		ArrayList<Vector2> posT2;
		ArrayList<DirtTile> dirttile;
		ArrayList<GrassTile> grasstile;
		try{
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream In = new ObjectInputStream(fileIn);
			t1 = (ArrayList<Player>) In.readObject();
			t2 = (ArrayList<Player>) In.readObject();
			posT1 = (ArrayList<Vector2>) In.readObject();
			posT2 = (ArrayList<Vector2>) In.readObject();
			dirttile = (ArrayList<DirtTile>) In.readObject();
			grasstile = (ArrayList<GrassTile>) In.readObject();
			tiledObjectUtil.parseTiledObjectLayer(dirttile,grasstile);
			Teams.loadTeams(t1,t2,posT1,posT2, world);
			In.close();
			fileIn.close();
			return path;
		}catch(Exception i){
			return null;
		}
	}

	/**
	 * Team1 won.
	 * @return true, if team 1 is the winner
	 */
	public boolean team1Won() {
		return !(Teams.getTeam(1).isEmpty());
	}
	
}
