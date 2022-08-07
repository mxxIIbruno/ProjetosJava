package main;

import java.awt.*;

public class UserInterface {

    public void render(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(19,19,52,7);

        g.setColor(Color.red);
        g.fillRect(20,20,50,5);

        g.setColor(Color.green);
        g.fillRect(20,20,(int)((Game.player.life/Game.player.maxLife) * 50), 5);

    }
}
