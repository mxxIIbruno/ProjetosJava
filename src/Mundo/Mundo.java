package Mundo;

import entidades.Ceu;
import entidades.Entity;
import entidades.Solido;
import graficos.Camera;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Mundo {

    public static int WIDTH, HEIGHT;
    public Tile[] tiles;

    public Mundo(String path) {
        try {
            BufferedImage cenario3 = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[cenario3.getWidth() * cenario3.getHeight()];
            tiles = new Tile[cenario3.getWidth() * cenario3.getHeight()];
            WIDTH = cenario3.getWidth();
            HEIGHT = cenario3.getHeight();
            cenario3.getRGB(0, 0, cenario3.getWidth(), cenario3.getHeight(), pixels, 0, cenario3.getWidth());

            for (int x = 0; x < cenario3.getWidth(); x++) {
                for (int y = 0; y < cenario3.getHeight(); y++) {
                    int pixelAtual = pixels[x + (y * cenario3.getWidth())];
                    tiles[x + (y * WIDTH)] = new Empty(x * 16, y * 16, Tile.empty);
                    if (pixelAtual == 0xFF7bff00) {
                        Game.player.setX(x * 16);
                        Game.player.setY(y * 16);
                    } else if (pixelAtual == 0xFF8f563b) {
                        Solido solido = new Solido(x * 16, y * 16, 16,16, Entity.chao);
                        Game.entidades.add(solido);
                    } else if (pixelAtual == 0xFF4b692f) {
                        Solido solido = new Solido(x * 16, y * 16, 16, 16, Entity.chaoGRAMA);
                        Game.entidades.add(solido);
                    } else if (pixelAtual == 0xFF004eff) {
                        Ceu ceu = new Ceu(x * 16, y * 16, 16, 16, Entity.ceu);
                        Game.ceuvetor.add(ceu);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        int xi = Camera.x / 16;
        int yi = Camera.y / 16;
        int xf = xi + (Game.WIDTH / 16);
        int yf = yi + (Game.HEIGHT / 16);
        for (int x = xi; x <= xf; x++) {
            for (int y = yi; y <= yf; y++) {
                if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
                    continue;
                Tile tile = tiles[x + (y * WIDTH)];
                tile.render(g);
            }
        }
    }
}
