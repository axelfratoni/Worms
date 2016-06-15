package com.worms.bars;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.physics.box2d.World;

public class Bar {
	private Texture texAbove, texBelow;
	
	public Bar(float x, float y, World world, int r1, int g1, int b1, int r2, int g2, int b2){
		int width =1 ;
        int height = 1;
        Pixmap pixmap = createProceduralPixmap(width, height,r1, g1, b1);
        Pixmap pixmap2 = createProceduralPixmap(width, height,r2,g2,b2);

        texBelow = new Texture(pixmap);
        texAbove = new Texture(pixmap2);
        
	}
	
	private Pixmap createProceduralPixmap (int width, int height,int r,int g,int b) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);

        pixmap.setColor(r, g, b, 1);
        pixmap.fill();

        return pixmap;
    }
	
	public void dispose(){
		texAbove.dispose();
		texBelow.dispose();		
	}
	
	public Texture getTexAbove(){
		return texAbove;
	}
	
	public Texture getTexBelow(){
		return texBelow;
	}
	
	
}

