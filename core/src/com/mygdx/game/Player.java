package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.interfaces.DrawEntity;
import com.mygdx.interfaces.UpdateEntity;

public class Player implements UpdateEntity, DrawEntity{
	private Point2D position;
	private float angleVision;
	
	
	private static final float SPEED_MOVING = 200;
	private static final float SPEED_ROTATING = 200;
	
	public Player(Point2D position) {
		this.position = position;
		this.angleVision = 0;
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
			angleVision -= SPEED_ROTATING*dt;
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
			angleVision += SPEED_ROTATING*dt;
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
	}
	
	private Point2D getPositionNormLookingAt(float angle) {
		double rad = angle * (Math.PI / 180.0);
		double x = Math.cos(rad);
		double y = Math.sin(rad);
		return new Point2D((float)x, (float)y);
	}
	
	@Override
	public void draw(Pixmap pixmap) {
		// Player
		pixmap.setColor(Color.BLACK);
		pixmap.drawCircle((int)position.x, (int)position.y, 20);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel((int)position.x, (int)position.y);
		
		// line of vision
		Point2D lookAt = getPositionNormLookingAt(angleVision);
		lookAt.x *= 50;
		lookAt.y *= 50;
		lookAt.x += position.x;
		lookAt.y += position.y;
		pixmap.drawLine((int)position.x, (int)position.y, (int)lookAt.x, (int)lookAt.y);
	}
}
