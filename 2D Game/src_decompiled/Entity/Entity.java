/*
 * Decompiled with CFR 0.152.
 */
package Entity;

import Main.GamePanel;
import Main.UtilityTool;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {
    GamePanel gp;
    public int worldX;
    public int worldY;
    public int speed;
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage right1;
    public BufferedImage right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        block20: {
            BufferedImage image = null;
            int screenX = this.worldX - this.gp.player.worldX + this.gp.player.screenX;
            int screenY = this.worldY - this.gp.player.worldY + this.gp.player.screenY;
            if (this.worldX + this.gp.tileSize < this.gp.player.worldX - this.gp.player.screenX - 50 || this.worldX - this.gp.tileSize > this.gp.player.worldX + this.gp.player.screenX + 50 || this.worldY + this.gp.tileSize < this.gp.player.worldY - this.gp.player.screenY - 50 || this.worldY - this.gp.tileSize > this.gp.player.worldY + this.gp.player.screenY + 50) break block20;
            g2.drawImage(image, screenX, screenY, this.gp.tileSize, this.gp.tileSize, null);
            switch (this.direction) {
                case "up": {
                    if (this.spriteNum == 1) {
                        image = this.up1;
                    }
                    if (this.spriteNum != 2) break;
                    image = this.up2;
                    break;
                }
                case "down": {
                    if (this.spriteNum == 1) {
                        image = this.down1;
                    }
                    if (this.spriteNum != 2) break;
                    image = this.down2;
                    break;
                }
                case "left": {
                    if (this.spriteNum == 1) {
                        image = this.left1;
                    }
                    if (this.spriteNum != 2) break;
                    image = this.left2;
                    break;
                }
                case "right": {
                    if (this.spriteNum == 1) {
                        image = this.right1;
                    }
                    if (this.spriteNum != 2) break;
                    image = this.right2;
                }
            }
        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool Utool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(String.valueOf(imagePath) + ".png"));
            image = Utool.scaledImage(image, this.gp.tileSize, this.gp.tileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
