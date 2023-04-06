package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.interfaces.DrawShape;

public class RayCastedScreen implements DrawShape{

    private Ray rays[];
    
    public RayCastedScreen(int rays, Point2D origin, float angleFirst) {
        this.rays = new Ray[rays];
        float degreeFirstRay = (angleFirst-35) % 360;
        for(int i = 0; i < this.rays.length; i++) {
            this.rays[i] = new Ray(origin, degreeFirstRay+i);
        }
    }

    public void updatePosition(Point2D origin, float angleFirst) {
        for(int i = 0; i < rays.length; i++) {
            rays[i].origin = origin;
            rays[i].angleDegrees = (angleFirst+i) % 360;
        }
    }
        
    public void updateCollision(TileMap tileMap) {
        for(int i = 0; i < rays.length; i++) {
            rays[i].collisionMap(tileMap);
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        for(int i = 0; i < rays.length; i++) {
            rays[i].draw(shapeRenderer);
        }

        // Draw screen
        float height = 200;
        float width = 200;
        float screenOffset = 100;

        
        for(int i = 0; i < rays.length; i++) {
            float length = rays[i].perpWallDist;

            float lineHeight = (int)(400 / length);

            //Draw ceil
            shapeRenderer.setColor(Color.CYAN);
            float x = i + screenOffset;
            float y = (height/ 2.0f + lineHeight) + screenOffset;
            float xx = i + screenOffset;
            float yy = height + screenOffset;
            shapeRenderer.line(x, y, xx, yy);

            //Draw walls
            shapeRenderer.setColor(Color.RED);
            y = ((height / 2.0f) + lineHeight) + screenOffset;
            yy = ((height / 2.0f) - lineHeight) + screenOffset;
            shapeRenderer.line(x, y, xx, yy);

            //Draw floor
            shapeRenderer.setColor(Color.GREEN);
            y = screenOffset;
            yy = ((height / 2.0f) - lineHeight) + screenOffset;
            shapeRenderer.line(x, y, xx, yy);
        }

        // Draw square containing image
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(screenOffset, screenOffset, width, height);

    }
    
}
