package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.interfaces.CollisionWithMap;
import com.mygdx.interfaces.DrawShape;
import com.mygdx.interfaces.UpdateEntity;

public class Player implements UpdateEntity, DrawShape, CollisionWithMap{
	private Point2D position;
	private float angleVision;
	
	private RayCastedScreen rayCastedScreen;

	private static final float SPEED_MOVING = 110;
	private static final float SPEED_ROTATING = 120;
	
	public Player(Point2D position) {
		this.position = position;
		this.angleVision = 0;
		this.rayCastedScreen = new RayCastedScreen(70, position, angleVision);
	}
	
	public final Point2D getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Keys.A)) {
			angleVision += SPEED_ROTATING*dt;
			angleVision %= 360;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			// Calculate where player is moving
			Point2D lookAt = getPositionNormLookingAt(angleVision+180.0f);
			double newX = lookAt.x * SPEED_MOVING * dt;
			double newY = lookAt.y * SPEED_MOVING * dt;
			
			newX += position.x;
			newY += position.y;
			
			position.x = (float)newX;
			position.y = (float)newY;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			angleVision -= SPEED_ROTATING*dt;
			angleVision %= 360;
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			Point2D lookAt = getPositionNormLookingAt(angleVision);
			double newX = lookAt.x * SPEED_MOVING * dt;
			double newY = lookAt.y * SPEED_MOVING * dt;
			
			newX += position.x;
			newY += position.y;
			
			position.x = (float)newX;
			position.y = (float)newY;
		}

		// update rays
		this.rayCastedScreen.updatePosition(position, (angleVision-35) % 360);
	}
	
	public Point2D getPositionNormLookingAt(float angle) {
		double rad = angle * (Math.PI / 180.0);
		double x = Math.cos(rad);
		double y = Math.sin(rad);
		return new Point2D((float)x, (float)y);
	}
	
	@Override
	public void draw(ShapeRenderer shapeRenderer) {
		// Player
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle((int)position.x, (int)position.y, 20);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect((int)position.x, (int)position.y, 1, 1);

		this.rayCastedScreen.draw(shapeRenderer);
	}

	@Override
	public void collisionMap(TileMap tileMap) {
		this.rayCastedScreen.updateCollision(tileMap);
	}
}
