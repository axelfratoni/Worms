package com.worms.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import static com.worms.utils.Constants.PPM;
public class BodyCreators {
	
	public static Body createBox(float x, float y, float width, float height, boolean isStatic, boolean fixedRotation, World world, short  cBits, short mBits, short gIndex, Object o){
		Body pBody;
		BodyDef bdef = new BodyDef();
		
		if (isStatic){
			bdef.type = BodyDef.BodyType.StaticBody;
		} else {
			bdef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bdef.position.set( x / PPM, y / PPM );
		bdef.fixedRotation = fixedRotation;
		pBody = world.createBody( bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox( width / 2 / PPM, height / 2 / PPM);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.filter.categoryBits = cBits; // Is a
		fixtureDef.filter.maskBits = mBits; // Collides with
		fixtureDef.filter.groupIndex = gIndex;
 		pBody = world.createBody(bdef);
		pBody.createFixture(fixtureDef).setUserData(o);
		shape.dispose();
		
		return pBody;
	}
	
	public static Body createSphere(float x, float y, float radius, boolean isStatic, boolean fixedRotation, World world, short  cBits, short mBits, short gIndex, Object o){
		Body pBody;
		BodyDef bdef = new BodyDef();
		
		if (isStatic){
			bdef.type = BodyDef.BodyType.StaticBody;
		} else {
			bdef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bdef.position.set( x / PPM, y / PPM );
		bdef.fixedRotation = fixedRotation;
		pBody = world.createBody( bdef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius( radius);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.filter.categoryBits = cBits; // Is a
		fixtureDef.filter.maskBits = mBits; // Collides with
		fixtureDef.filter.groupIndex = gIndex;
		pBody = world.createBody(bdef);
		pBody.createFixture(fixtureDef).setUserData(o);
		shape.dispose();
		
		return pBody;
	}
	
}
