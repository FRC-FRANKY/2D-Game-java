/*
 * Decompiled with CFR 0.152.
 */
package Main;

import Entity.Entity;
import Entity.EnemyPlant;
import Entity.Player;
import Main.AssetSetter;
import Main.CollisionChecker;
import Main.KeyHandler;
import Main.Sound;
import Main.UI;
import Object.SuperObject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel
extends JPanel
implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = 48;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = 768;
    public final int screenHeight = 576;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    int FPS = 60;
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound Sound = new Sound();
    Sound se = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public Player player = new Player(this, this.keyH);
    public SuperObject[] obj = new SuperObject[100];
    public Entity[] npc = new Entity[100];
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int mapOverworld = 0;
    public final int mapBattle1 = 1;
    public final int mapBattle2 = 2;
    public int currentMap = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(768, 576));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        this.aSetter.setObject();
        this.aSetter.setNPC();
        this.tileM.loadMapByType(this.currentMap);
        this.gameState = 0;
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1.0E9 / (double)this.FPS;
        double delta = 0.0;
        while (this.gameThread != null) {
            long now = System.nanoTime();
            delta += (double)(now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1.0) {
                this.update();
                delta -= 1.0;
            }
            this.repaint();
        }
    }

    public void update() {
        if (this.gameState == 1) {
            this.player.update();
            int i = 0;
            while (i < this.npc.length) {
                if (this.npc[i] != null && this.npc[i] instanceof EnemyPlant) {
                    ((EnemyPlant)this.npc[i]).update();
                }
                ++i;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        long drawStart = 0L;
        if (this.keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        if (this.gameState == 0) {
            this.ui.draw(g2);
        } else {
            this.tileM.draw(g2);
            int i = 0;
            while (i < this.obj.length) {
                if (this.obj[i] != null) {
                    this.obj[i].draw(g2, this);
                }
                ++i;
            }
            i = 0;
            while (i < this.npc.length) {
                if (this.npc[i] != null) {
                    this.npc[i].draw(g2);
                }
                ++i;
            }
            this.player.draw(g2);
            this.ui.draw(g2);
        }
        if (this.keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time:" + passed);
        }
        g2.dispose();
    }

    public void playMusic(int i) {
        this.Sound.setFile(i);
        this.Sound.play();
        this.Sound.loop();
    }

    public void stopMusic() {
        this.Sound.stop();
    }

    public void playSE(int i) {
        this.se.setFile(i);
        this.se.play();
    }

    public void enterBattleStage(int mapType) {
        if (this.currentMap != this.mapOverworld) {
            return;
        }
        if (mapType != this.mapBattle1 && mapType != this.mapBattle2) {
            return;
        }
        this.currentMap = mapType;
        this.tileM.loadMapByType(this.currentMap);
        this.player.worldX = 24 * this.tileSize;
        this.player.worldY = 24 * this.tileSize;
        this.ui.showMessage("Battle Stage " + mapType + " started!");
    }

    public void returnToOverworld() {
        if (this.currentMap == this.mapOverworld) {
            return;
        }
        this.currentMap = this.mapOverworld;
        this.tileM.loadMapByType(this.currentMap);
        this.player.worldX = 24 * this.tileSize;
        this.player.worldY = 24 * this.tileSize;
        this.ui.showMessage("Returned to Overworld.");
    }
}
