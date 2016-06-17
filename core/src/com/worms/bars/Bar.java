package com.worms.bars;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.physics.box2d.World;

public class Bar {
	private Texture texAbove, texBelow;
	
	/**
	 * Instantiates a new bar.
	 *
	 * @param x the x
	 * @param y the y
	 * @param world the world
	 * @param r1 the r1
	 * @param g1 the g1
	 * @param b1 the b1
	 * @param r2 the r2
	 * @param g2 the g2
	 * @param b2 the b2
	 */
	public Bar(float x, float y, World world, int r1, int g1, int b1, int r2, int g2, int b2){
		int width =1 ;
        int height = 1;
        Pixmap pixmap = createProceduralPixmap(width, height,r1, g1, b1);
        Pixmap pixmap2 = createProceduralPixmap(width, height,r2,g2,b2);

        texBelow = new Texture(pixmap);
        texAbove = new Texture(pixmap2);
        
	}
	
	/**
	 * Creates the procedural pixmap.
	 *
	 * @param width the width
	 * @param height the height
	 * @param r the r
	 * @param g the g
	 * @param b the b
	 * @return the pixmap
	 */
	private Pixmap createProceduralPixmap (int width, int height,int r,int g,int b) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);

        pixmap.setColor(r, g, b, 1);
        pixmap.fill();

        return pixmap;
    }
	
	/**
	 * Dispose.
	 */
	public void dispose(){
		texAbove.dispose();
		texBelow.dispose();		
	}
	
	/**
	 * Gets the tex above.
	 *
	 * @return the tex above
	 */
	public Texture getTexAbove(){
		return texAbove;
	}
	
	/**
	 * Gets the tex below.
	 *
	 * @return the tex below
	 */
	public Texture getTexBelow(){
		return texBelow;
	}
	
	
}

