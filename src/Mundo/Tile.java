package Mundo;

import graficos.Camera;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static BufferedImage empty = Game.sprite.getSprite(16, 32, 16, 16);

    public int x, y;
    public BufferedImage sprite;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }
    public  void render(Graphics g) {
        g.drawImage(sprite, x-Camera.x, y-Camera.y, null);
    }
}
