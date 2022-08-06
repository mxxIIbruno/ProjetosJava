package graficos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprintsheet {

    public BufferedImage spritsheet;

    public Sprintsheet(String path) {
        try {
            spritsheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spritsheet.getSubimage(x-Camera.x, y-Camera.y, width, height);
    }
}
