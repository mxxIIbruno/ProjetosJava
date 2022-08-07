package main;

import Mundo.Mundo;
import entidades.*;
import graficos.Sprintsheet;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {

    public JFrame jFrame;
    private Thread thread;
    private boolean isRunning = true;

    public static int WIDTH = 240;
    public static int HEIGHT = 160;
    public static int SCALE = 4;

    public static BufferedImage fundo;
    public static List<Entity> entidades;
    public static Sprintsheet sprite;
    public static Mundo mundo;

    public static Player player;

    public static List<Ceu> ceuvetor;
    public static Sprintsheet ceu;

    public static List<Cenoura> cenoura;
    public static List<Inimigo> inimigo;

    public static UserInterface ui;

    public int level = 1, levelmaximo = 2;

    public Game() {
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        ui = new UserInterface();
        fundo = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entidades = new ArrayList<Entity>();
        sprite = new Sprintsheet("/spritesheet.png");
        ceuvetor = new ArrayList<Ceu>();
        cenoura = new ArrayList<Cenoura>();
        inimigo = new ArrayList<Inimigo>();
        ceu = new Sprintsheet("/ceusprite.png");
        player = new Player(0,0,16, 16, sprite.getSprite(32,0,16,16));
        entidades.add(player);
        mundo = new Mundo("/level1.png");
    }

    public void initFrame() {
        jFrame = new JFrame("JOGO");
        jFrame.add(this);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {

        if (inimigo.size() == 0) {
            level++;
            if (level > levelmaximo) {
                level = 1;
            }
            String Level = "level" + level + ".png";
            Mundo.newlevel(Level);
        }

        for (int i = 0; i < entidades.size(); i++) {
            Entity entidade = entidades.get(i);
            entidade.tick();
        }

        for (int i = 0; i < ceuvetor.size(); i++){
            Ceu entidade = ceuvetor.get(i);
            entidade.tick();
        }

        for (int i = 0; i < cenoura.size(); i++) {
            Cenoura entidade = cenoura.get(i);
            entidade.tick();
        }

        for (int i = 0; i < inimigo.size(); i++) {
            Inimigo entidade = inimigo.get(i);
            entidade.tick();
        }

    }

    public void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if(buffer == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = fundo.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        mundo.render(g);

        for (int i = 0; i < ceuvetor.size(); i ++) {
            Ceu entidade = ceuvetor.get(i);
            entidade.render(g);
        }

        for(int i = 0; i < entidades.size(); i++) {
            Entity entidade = entidades.get(i);
            entidade.render(g);
        }

        for (int i = 0; i < cenoura.size(); i ++) {
            Cenoura entidade = cenoura.get(i);
            entidade.render(g);
        }

        for (int i = 0; i < inimigo.size(); i++) {
            Inimigo entidade = inimigo.get(i);
            entidade.render(g);
        }

        ui.render(g);

        g = buffer.getDrawGraphics();
        g.drawImage(fundo, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        buffer.show();

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTick = 60.0f;
        double ms = 1000000000 / amountOfTick;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ms;
            lastTime = now;
            if(delta > 1) {
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS : " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        }else if(e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }else if(e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        }else if(e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }else if(e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        }
    }
}
