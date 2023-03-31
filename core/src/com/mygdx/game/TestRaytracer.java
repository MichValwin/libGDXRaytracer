package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestRaytracer extends ApplicationAdapter {
	// Draw
	Pixmap pixmapBuffer;
	Texture frameBufferTex ;
	SpriteBatch spriteBatch;
	
	// Game
	Player player;
	
	@Override
	public void create () {
		pixmapBuffer = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGBA8888);
		spriteBatch = new SpriteBatch();
		frameBufferTex = new Texture(pixmapBuffer);
		
		// Game 
		player = new Player(new Point2D(20, 20));
	}

	@Override
	public void render () {
		// Update
		player.update(Gdx.graphics.getDeltaTime());
		
		
		// Draw
		// Clear framebuffer (pixmap)
		pixmapBuffer.setColor(Color.PINK);
		pixmapBuffer.fill();
		
		
		player.draw(pixmapBuffer);
		
		
		// Draw pixmap to screen
		frameBufferTex.dispose();
		frameBufferTex = new Texture(pixmapBuffer);
		spriteBatch.begin();
		spriteBatch.draw(frameBufferTex, 0, 0);
		spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		pixmapBuffer.dispose();
		pixmapBuffer = new Pixmap(width, height, Format.RGBA8888);
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		pixmapBuffer.dispose();
		frameBufferTex.dispose();
	}
}
