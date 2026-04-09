/*
 * Decompiled with CFR 0.152.
 */
package Main;

import Main.GamePanel;
import Object.OBJ_Keys;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    Font arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;
    double playTime;
    DecimalFormat dformat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        this.arial_40 = new Font("Arial", 0, 40);
        this.arial_80B = new Font("Arial", 1, 60);
        OBJ_Keys key = new OBJ_Keys(gp);
        this.keyImage = key.image;
    }

    public void showMessage(String text) {
        this.message = text;
        this.messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if (this.gameFinished) {
            g2.setFont(this.arial_40);
            g2.setColor(Color.white);
            String text = "You Found the Treasure Chest!!";
            int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = this.gp.screenWidth / 2 - textLength / 2;
            int y = this.gp.screenHeight / 2 - this.gp.tileSize * 3;
            g2.drawString(text, x, y);
            text = "Your Time is:" + this.dformat.format(this.playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = this.gp.screenWidth / 2 - textLength / 2;
            y = this.gp.screenHeight / 2 + this.gp.tileSize * 3;
            g2.drawString(text, x, y);
            g2.setFont(this.arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = this.gp.screenWidth / 2 - textLength / 2;
            y = this.gp.screenHeight / 2 + this.gp.tileSize * 2;
            g2.drawString(text, x, y);
            this.gp.gameThread = null;
        } else {
            if (this.gp.gameState != this.gp.titleState) {
                g2.setFont(this.arial_40);
                g2.setColor(Color.white);
                g2.drawImage(this.keyImage, this.gp.tileSize / 2, this.gp.tileSize / 2, this.gp.tileSize, this.gp.tileSize, null);
                g2.drawString("x" + this.gp.player.hasChest, 75, 61);
                g2.drawString(this.gp.player.hasSword ? "Sword: ON" : "Sword: OFF", 20, 110);
            }
            if (this.gp.gameState != this.gp.pauseState && this.gp.gameState != this.gp.titleState) {
                this.playTime += 0.0078125;
            }
            if (this.gp.gameState != this.gp.titleState) {
                g2.drawString("Time:" + this.dformat.format(this.playTime), this.gp.tileSize * 11, 65);
                String stageText = "Map: OVERWORLD";
                if (this.gp.currentMap == this.gp.mapBattle1) {
                    stageText = "Map: BATTLE 1";
                } else if (this.gp.currentMap == this.gp.mapBattle2) {
                    stageText = "Map: BATTLE 2";
                }
                g2.drawString(stageText, this.gp.tileSize * 9, 110);
            }
            if (this.gp.gameState == this.gp.titleState) {
                this.drawTitleScreen();
            }
            int cfr_ignored_0 = this.gp.gameState;
            this.gp.getClass();
            if (this.gp.gameState == this.gp.pauseState) {
                this.drawPauseScreen();
            }
            if (this.messageOn) {
                g2.setFont(g2.getFont().deriveFont(30.0f));
                g2.drawString(this.message, this.gp.tileSize / 2, this.gp.tileSize * 5);
                ++this.messageCounter;
                if (this.messageCounter > 120) {
                    this.messageCounter = 0;
                    this.messageOn = false;
                }
            }
        }
    }

    public void drawTitleScreen() {
        this.g2.setColor(new Color(70, 120, 80));
        this.g2.fillRect(0, 0, this.gp.screenWidth, this.gp.screenHeight);
        this.g2.setFont(this.g2.getFont().deriveFont(1, 96.0f));
        String text = "Boy Cabbage";
        int x = this.getXforCenteredText(text);
        int y = this.gp.tileSize * 3;
        this.g2.setColor(Color.black);
        this.g2.drawString(text, x + 5, y + 5);
        this.g2.setColor(Color.white);
        this.g2.drawString(text, x, y);
        x = this.gp.screenWidth / 2 - this.gp.tileSize * 2 / 2;
        this.g2.drawImage(this.gp.player.down1, x, y += this.gp.tileSize * 2, this.gp.tileSize * 2, this.gp.tileSize * 2, null);
        this.g2.setFont(this.g2.getFont().deriveFont(1, 48.0f));
        text = "START GAME";
        x = this.getXforCenteredText(text);
        y = (int)((double)y + (double)this.gp.tileSize * 3.5);
        this.g2.drawString(text, x, y);
        if (this.commandNum == 0) {
            this.g2.drawString(">", x - this.gp.tileSize, y);
        }
        text = "QUIT";
        x = this.getXforCenteredText(text);
        this.g2.drawString(text, x, y += this.gp.tileSize);
        if (this.commandNum == 1) {
            this.g2.drawString(">", x - this.gp.tileSize, y);
        }
    }

    public void drawPauseScreen() {
        this.g2.setFont(this.g2.getFont().deriveFont(0, 80.0f));
        String text = "PAUSED";
        int x = this.getXforCenteredText(text);
        int y = this.gp.screenHeight / 2;
        this.g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)this.g2.getFontMetrics().getStringBounds(text, this.g2).getWidth();
        int x = this.gp.screenWidth / 2 - length / 2;
        return x;
    }
}
