package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.interfaces.CollisionWithMap;
import com.mygdx.interfaces.DrawShape;

public class Ray implements CollisionWithMap, DrawShape{
    public Point2D origin;
    public float angleDegrees;

    public boolean collides;
    public float lengthCollision;

    // debug
    private ArrayList<Point2DInt> tilesCrossed;
    private Point2DInt tileCollides;
    public boolean collidesX;
    public float perpWallDist;

    public Ray(Point2D origin, float angleDegrees) {
        this.origin = origin;
        this.angleDegrees = angleDegrees;
        this.collides = false;
        this.lengthCollision = 0;

        tilesCrossed = new ArrayList<Point2DInt>();
        tileCollides = new Point2DInt(0, 0);
        collidesX = false;
    }

    @Override
    public void collisionMap(TileMap tileMap) {
        this.collides = false;
        tilesCrossed.clear();
       

        double rad = angleDegrees * (Math.PI / 180.0);
        Point2D direction = new Point2D((float)Math.cos(rad), (float)Math.sin(rad));
        Point2DInt mapStart = new Point2DInt((int)(origin.x / 20),(int) (origin.y / 20));

        // Distance from the ray to one X or Y side
        float deltaDistX = (direction.x == 0) ? Float.MAX_VALUE : (float)Math.abs(1.0 / direction.x);
        float deltaDistY = (direction.y == 0) ? Float.MAX_VALUE : (float)Math.abs(1.0 / direction.y);

        Point2DInt stepDir = new Point2DInt(0,0);
        Point2D rayDistanceToSide = new Point2D(0,0);

        if(direction.x < 0) {
            stepDir.x = -1;
            rayDistanceToSide.x = (origin.x - (mapStart.x*20))*  deltaDistX;
        }else{
            stepDir.x = 1;
            rayDistanceToSide.x = (((mapStart.x+1)*20) - origin.x) * deltaDistX;
        }

        if(direction.y < 0) {
            stepDir.y = -1;
            rayDistanceToSide.y = (origin.y - (mapStart.y*20)) * deltaDistY;
        }else{
            stepDir.y = 1;
            rayDistanceToSide.y = (((mapStart.y+1)*20) - origin.y) * deltaDistY;
        }

        // Determinate where it collides
        float distanceTotal = 0.0f;
        Point2DInt mapCheck = new Point2DInt(mapStart.x, mapStart.y);
        while(!collides && distanceTotal < 2000.0f) {
            if(rayDistanceToSide.x < rayDistanceToSide.y) {
                mapCheck.x += stepDir.x;
                distanceTotal = rayDistanceToSide.x;
                rayDistanceToSide.x += deltaDistX * 20;
                collidesX = true;
            }else{
                mapCheck.y += stepDir.y;
                distanceTotal = rayDistanceToSide.y;
                rayDistanceToSide.y += deltaDistY * 20;
                collidesX = false;
            }

            tilesCrossed.add(new Point2DInt(mapCheck.x, mapCheck.y));
            
            if(tileMap.isPositionValid((int)mapCheck.x, (int)mapCheck.y)){
                Tile tile = tileMap.getTilemap()[(int)mapCheck.x + (int)mapCheck.y*tileMap.getWidth()];
                if(tile == Tile.FILLED) {
                    this.collides = true;
                    lengthCollision = distanceTotal;
                    tileCollides = mapCheck;

                    if(collidesX){
                        perpWallDist = rayDistanceToSide.x - deltaDistX * 20;
                    }else{
                        perpWallDist = rayDistanceToSide.y - deltaDistY * 20;
                    }
                }
            }
        }
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        double rad = angleDegrees * (Math.PI / 180.0);
        if(collides) {
            float x = origin.x + (float)Math.cos(rad) * lengthCollision;
            float y = origin.y + (float)Math.sin(rad) * lengthCollision;
            shapeRenderer.line((int)origin.x, (int)origin.y, (int)x, (int)y);
        }else{
            float x = origin.x + (float)Math.cos(rad) * 200;
            float y = origin.y + (float)Math.sin(rad) * 200;
            shapeRenderer.line((int)origin.x, (int)origin.y, (int)x, (int)y);
        }

        if(collides) {
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.rect(tileCollides.x*20, tileCollides.y*20, 20, 20);
        }
    }
}
