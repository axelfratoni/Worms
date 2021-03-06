package com.worms.utils;

public final class Constants {
	
	public static final float SCALE = 2f;
	public static final float PPM = 32f;
	public static final float SPEED = 10f;
	public static final float MOVEMENT_LIMIT = 3f;
	
	public static final short BIT_WALL =1;
	public static final short BIT_PLAYER = 2;
	public static final short BIT_ARROW = 4;
	public static final short BIT_PROJECTILE = 8;
	public static final short BIT_EXPLOSION = 16;

	public static final float E_RADIUS_GRENADE = 1.5f;
	public static final float E_RADIUS_BULLET = 0.5f;
	public static final float E_RADIUS_MISSILE = 3f;

	public static final int BEGINNING_STEP = 0;
	public static final int MOVEMENT_STEP = 1;
	public static final int WEAPON_STEP = 2;
	public static final int ANGLE_STEP = 3;
	public static final int CHARGING_STEP = 4;
	public static final int SHOOTING_STEP = 5;
}