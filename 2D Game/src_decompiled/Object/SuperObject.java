/*
 * Decompiled with CFR 0.152.
 */
package Object;

import Main.GamePanel;
import Main.UtilityTool;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    protected UtilityTool Utool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = this.worldX - gp.player.worldX + gp.player.screenX;
        int screenY = this.worldY - gp.player.worldY + gp.player.screenY;
        if (this.worldX + gp.tileSize >= gp.player.worldX - gp.player.screenX - 50 && this.worldX - gp.tileSize <= gp.player.worldX + gp.player.screenX + 50 && this.worldY + gp.tileSize >= gp.player.worldY - gp.player.screenY - 50 && this.worldY - gp.tileSize <= gp.player.worldY + gp.player.screenY + 50) {
            g2.drawImage(this.image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
