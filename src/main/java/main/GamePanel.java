package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16px x 16 px
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48px x 48px
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread; // Hilo del juego
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Previene el parpadeo de la pantalla
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        if (gameState == playState){
            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if (gameState == pauseState){
            // Pause
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // DEBUG
        long drawStart = 0;
        if (keyHandler.checkDrawTime){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState){
            ui.draw(g2);
        }else{
            // TILE
            tileM.draw(g2);

            // OBJECT
            for (int i = 0; i < obj.length; i++){
                if (obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }
            // NPC
            for (int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    npc[i].draw(g2);
                }
            }
            // PLAYER
            player.draw(g2);
            // UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyHandler.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time  " + passed, 10, 400);
            System.out.println("Draw Time  " + passed);
        }

        g2.dispose(); // Libera los recursos de memoria
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
