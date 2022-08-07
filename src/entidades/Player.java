package entidades;

import Mundo.Mundo;
import graficos.Camera;
import main.Game;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left, down, up;
    public double speed = 1.0;
    public double life = 100, maxLife = 100;

    public int direita = 1, esquerda = 0;
    public int direcaoAtual = direita;

    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;

    public boolean jump = false;
    public boolean isJump = false;
    public int jumpHeight = 36;
    public int jumpFrames = 0;
    public Inimigo p1;
    public Cenoura vida;

    public int movimentacao = 0;
    public int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;

    public BufferedImage[] playerRight;
    public BufferedImage[] playerLeft;

    public int posx, posy;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        playerRight = new BufferedImage[4];
        playerLeft = new BufferedImage[4];

        for(int i = 0; i < 4; i++) {
            playerRight[i] = Game.sprite.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for(int i = 0; i < 4; i++) {
            playerLeft[i] = Game.sprite.getSprite(80 - (i * 16), 16, 16, 16);
        }
    }

    @SuppressWarnings("unused")
    public void tick() {

        movimentacao = 0;

        if(!colisao((int) x, (int) (y + 1)) && isJump == false) {
            y += 2;
            for (int i = 0; i < Game.inimigo.size(); i++) {
                Inimigo e = Game.inimigo.get(i);
                if (e instanceof Inimigo) {
                    if (damage(this.getX(), this.getY() - 8)) {
                        isJump = true;
                        ((Inimigo) p1).life--;
                        if (((Inimigo) p1).life == 0) {
                            Game.inimigo.remove(p1);
                        }
                    }
                }
                break;
            }
        }

        if(right && !colisao((int)(x + speed), this.getY())) {
            x += speed;
            movimentacao = 1;
            direcaoAtual = direita;
        }

        if(left && !colisao((int)(x - speed), this.getY())) {
            x -= speed;
            movimentacao = 1;
            direcaoAtual = esquerda;
        }

        if(jump) {
            if(colisao(this.getX(), this.getY() + 1)) {
                isJump = true;
            }
        }

        if (isJump) {
            if (!colisao(this.getX(), this.getY() - 2)) {
                y -= 2;
                jumpFrames += 2;
                if (jumpFrames == jumpHeight) {
                    isJump = false;
                    jump = false;
                    jumpFrames = 0;
                }
            } else {
                isJump = false;
                jump =false;
                jumpFrames = 0;
            }
        }

        if (movimentacao == 1) {
            frames++;
            if (frames == maxFrames) {
                index++;
                frames = 0;
                if (index > maxIndex) {
                    index = 0;
                }
            }
        }

        if (damage(this.getX(), this.getY())) {
            life -= 0.45;
        }

        if (vida(this.getX(), this.getY()) && life < 100) {
            life += 10;
            if (life > 100) {
                life = 100;
            }
            Game.cenoura.remove(vida);
        }

        if (check(this.getX(), this.getY())) {
            posx = this.getX();
            posy = this.getY();
        }

        if (life <= 0) {
            setX(posx);
            setY(posy);
            life = 100;
        }

        Camera.x = Camera.Clamp(this.getX() - (Game.WIDTH / 2), 0, Mundo.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.Clamp(this.getY() - (Game.HEIGHT / 2), 0, Mundo.HEIGHT * 16 - Game.HEIGHT);

    }

    public boolean colisao(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for(int i = 0; i < Game.entidades.size(); i++) {
            Entity entidade = Game.entidades.get(i);
            if(entidade instanceof Solido) {
                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);
                if(player.intersects(solido)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.entidades.size(); i++) {
            Entity entidade = Game.entidades.get(i);
            if (entidade instanceof Check) {
                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);
                if (player.intersects(solido)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean damage(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.inimigo.size(); i++) {
            Inimigo entidade = Game.inimigo.get(i);
            if (entidade instanceof Inimigo) {
                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);
                if (player.intersects(solido)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean vida(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.cenoura.size(); i++) {
            Cenoura cenoura = Game.cenoura.get(i);
            if (cenoura instanceof Cenoura) {
                Rectangle solido = new Rectangle(cenoura.getX() + maskx, cenoura.getY() + masky, maskw, maskh);
                if (player.intersects(solido)) {
                    vida = cenoura;
                    return true;
                }
            }
        }
        return false;
    }

    public void render(Graphics g) {
        if(direcaoAtual == direita && movimentacao == 1) {
            g.drawImage(playerRight[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
        }
        if(direcaoAtual == direita && movimentacao == 0) {
            g.drawImage(playerRight[0], this.getX()-Camera.x, this.getY()-Camera.y, null);
        }
        if(direcaoAtual == esquerda && movimentacao == 1) {
            g.drawImage(playerLeft[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
        }
        if(direcaoAtual == esquerda && movimentacao == 0) {
            g.drawImage(playerLeft[0], this.getX()-Camera.x, this.getY()-Camera.y, null);
        }
    }

}
