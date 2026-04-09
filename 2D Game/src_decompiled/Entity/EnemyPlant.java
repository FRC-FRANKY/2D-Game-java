package Entity;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyPlant extends Entity {
    private final Random random = new Random();
    private int actionLockCounter = 0;
    public boolean alive = true;

    public EnemyPlant(GamePanel gp) {
        super(gp);
        this.solidArea = new Rectangle(8, 16, 32, 32);
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.speed = 2;
        this.direction = "down";
        this.up1 = this.setup("/enemy/plant up 1");
        this.up2 = this.setup("/enemy/plant up 2");
        this.down1 = this.setup("/enemy/plant down 1");
        this.down2 = this.setup("/enemy/plant down 2");
        this.left1 = this.setup("/enemy/plant left 1");
        this.left2 = this.setup("/enemy/plant left 2");
        this.right1 = this.setup("/enemy/plant right 1");
        this.right2 = this.setup("/enemy/plant right 2");
    }

    public void update() {
        if (!this.alive) {
            return;
        }
        ++this.actionLockCounter;
        if (this.actionLockCounter > 60) {
            int i = this.random.nextInt(100);
            if (i < 25) {
                this.direction = "up";
            } else if (i < 50) {
                this.direction = "down";
            } else if (i < 75) {
                this.direction = "left";
            } else {
                this.direction = "right";
            }
            this.actionLockCounter = 0;
        }
        this.collisionOn = false;
        this.gp.checker.checkTile(this);
        if (!this.collisionOn) {
            if ("up".equals(this.direction)) {
                this.worldY -= this.speed;
            } else if ("down".equals(this.direction)) {
                this.worldY += this.speed;
            } else if ("left".equals(this.direction)) {
                this.worldX -= this.speed;
            } else if ("right".equals(this.direction)) {
                this.worldX += this.speed;
            }
        }
        ++this.spriteCounter;
        if (this.spriteCounter > 15) {
            this.spriteNum = this.spriteNum == 1 ? 2 : 1;
            this.spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!this.alive) {
            return;
        }
        int screenX = this.worldX - this.gp.player.worldX + this.gp.player.screenX;
        int screenY = this.worldY - this.gp.player.worldY + this.gp.player.screenY;
        if (this.worldX + this.gp.tileSize < this.gp.player.worldX - this.gp.player.screenX - 50 || this.worldX - this.gp.tileSize > this.gp.player.worldX + this.gp.player.screenX + 50 || this.worldY + this.gp.tileSize < this.gp.player.worldY - this.gp.player.screenY - 50 || this.worldY - this.gp.tileSize > this.gp.player.worldY + this.gp.player.screenY + 50) {
            return;
        }
        BufferedImage image = null;
        if ("up".equals(this.direction)) {
            image = this.spriteNum == 1 ? this.up1 : this.up2;
        } else if ("down".equals(this.direction)) {
            image = this.spriteNum == 1 ? this.down1 : this.down2;
        } else if ("left".equals(this.direction)) {
            image = this.spriteNum == 1 ? this.left1 : this.left2;
        } else if ("right".equals(this.direction)) {
            image = this.spriteNum == 1 ? this.right1 : this.right2;
        }
        g2.drawImage(image, screenX, screenY, this.gp.tileSize, this.gp.tileSize, null);
    }
}
