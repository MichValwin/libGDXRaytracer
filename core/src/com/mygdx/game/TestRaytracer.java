package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TestRaytracer extends ApplicationAdapter {
	// Draw
	SpriteBatch spriteBatch;
	ShapeRenderer shapeRenderer;
	OrthographicCamera orthoCamera;

	// Game
	Player player;
	TileMap tileMap;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		orthoCamera = new OrthographicCamera(360, 240);
		orthoCamera.translate(360/2, 240/2);
		orthoCamera.update();
		
		// Game 
		player = new Player(new Point2D(70, 70));
		tileMap = new TileMap(18, 12);
	}

	@Override
	public void render () {
		// Update
		player.update(Gdx.graphics.getDeltaTime());
		player.collisionMap(tileMap);
		
		// Draw
		// Clear framebuffer
		Gdx.gl.glClearColor(0.8f, 0.6f, 0.5f, 0);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(orthoCamera.combined);
		shapeRenderer.begin(ShapeType.Line);
		
		
		tileMap.draw(shapeRenderer);
		player.draw(shapeRenderer);
		
		shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		shapeRenderer.dispose();
	}
}
