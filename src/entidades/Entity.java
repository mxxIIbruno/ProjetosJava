package entidades;

import graficos.Camera;
import main.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {

    private int maskx, masky, mwidth, mheight;
    public static BufferedImage chao = Game.sprite.getSprite(0,0,16,16);
    public static BufferedImage chaoGRAMA = Game.sprite.getSprite(16, 0, 16, 16);
    public static BufferedImage empty = Game.sprite.getSprite(16, 32, 16, 16);

    public static BufferedImage ceu = Game.ceu.getSprite(0, 0, 1471, 400);

    public static BufferedImage grama = Game.sprite.getSprite(0,32,16,16);
    public static BufferedImage inimigo = Game.sprite.getSprite(112, 0, 16, 16);
    public static BufferedImage cenoura = Game.sprite.getSprite(112,16,16,16);

    public static BufferedImage save = Game.sprite.getSprite(96,16,16,16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void  setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(sprite, this.getX()-Camera.x, this.getY()-Camera.y, null);
    }
}
