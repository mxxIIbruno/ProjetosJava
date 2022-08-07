package entidades;

import graficos.Camera;
import main.Game;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Inimigo extends Entity {

    public double speed = 0.5;
    public int movimentacao = 0;
    public int frames = 0, maxFrames = 7, index = 0, maxIndex = 1;
    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;
    public int life = 5, maxLife = 9;

    public BufferedImage[] inimigo;

    public Inimigo(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        inimigo = new BufferedImage[2];

        for (int i = 0; i < 2; i++) {
            inimigo[i] = Game.sprite.getSprite(112 + (i + 16), 0, 16, 16);
        }
    }

    public void tick() {
        movimentacao = 0;

        if (!colisao((int) x, (int) (y + 1))) {
            y += 2;
        }

        if (Game.player.getX() < this.getX() && !colisao((int) (x - speed), this.getY())) {
            x -= speed;
            movimentacao = 1;
        }

        if (Game.player.getX() > this.getX() && !colisao((int) (x + speed), this.getY())) {
            x += speed;
            movimentacao = 1;
        }

        if (movimentacao == 1) {
            frames++;
            if (frames == maxFrames) {
                index++;
                frames = 0;
                if (index > maxIndex)
                    index = 0;
            }
        }

    }

    public void render(Graphics g) {
        g.drawImage(inimigo[index], this.getX()- Camera.x, this.getY()-Camera.y, null);
    }

    public boolean novo(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.entidades.size(); i++) {
            Entity entidade = Game.entidades.get(i);
            if (entidade instanceof Solido) {
                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);
                if (player.intersects(solido)) {
                    return true;
                }
            }
        }
        return false;
     }

    public boolean colisao(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.entidades.size(); i++) {
            Entity entidade = Game.entidades.get(i);
            if (entidade instanceof Solido) {
                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);
                if (player.intersects(solido)) {
                    return true;
                }
            }
        }
        return false;
    }
}
