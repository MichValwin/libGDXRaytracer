package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.interfaces.DrawShape;

public class TileMap implements DrawShape{
    private int width;
    private int height;
    private Tile tiles[];

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width*height];
        // Init 
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(y == 0 || y == height-1 || x == 0 || x == width-1){
                    tiles[x + y*width] = Tile.FILLED;
                }else{
                    tiles[x + y*width] = Tile.EMPTY;
                }

                if(y == 5 && x == 10)tiles[x + y*width] = Tile.FILLED;
            }
        }
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Tile tile = tiles[x + y*width];
                if(tile == Tile.FILLED) {
                    shapeRenderer.rect(x*20, y*20, 20, 20);
                }
            }
        }
    }

    public boolean isPositionValid(int x, int y) {
        if(x >= 0 && x < width && y >= 0 && y < height) {
            return true;
        }else{
            return false;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[] getTilemap(){
        return tiles;
    }
}
