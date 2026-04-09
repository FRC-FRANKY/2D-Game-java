/*
 * Decompiled with CFR 0.152.
 */
package Entity;

import Entity.Entity;
import Entity.EnemyPlant;
import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player
extends Entity {
    public KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasChest = 0;
    public boolean hasSword = false;
    private BufferedImage swordImage;
    private int attackCooldown = 0;
    private boolean swordSwinging = false;
    private int swordSwingCounter = 0;
    private final int swordSwingDuration = 10;
    private final double renderScale = 1.6;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        this.screenX = gp.screenWidth / 2 - gp.tileSize;
        this.screenY = gp.screenHeight / 2 - gp.tileSize;
        this.solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;
        this.setDefaultValues();
        this.getPlayerImages();
    }

    public void setDefaultValues() {
        this.worldX = this.gp.tileSize * 23;
        this.worldY = this.gp.tileSize * 21;
        this.speed = 4;
        this.direction = "down";
    }

    public void getPlayerImages() {
        this.up1 = this.setup("/player/boy up 1");
        this.up2 = this.setup("/player/boy up 2");
        this.down1 = this.setup("/player/boy down 1");
        this.down2 = this.setup("/player/boy down 2");
        this.left1 = this.setup("/player/boy left 1");
        this.left2 = this.setup("/player/boy left 2");
        this.right1 = this.setup("/player/boy right 1");
        this.right2 = this.setup("/player/boy right 2");
        this.swordImage = this.setup("/objects/sword");
    }

    public void update() {
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
        if (this.swordSwinging) {
            --this.swordSwingCounter;
            if (this.swordSwingCounter <= 0) {
                this.swordSwinging = false;
            }
        }
        if (this.keyH.spacebarPressed && this.hasSword && this.attackCooldown == 0) {
            this.trySwordAttack();
            this.attackCooldown = 12;
            this.swordSwinging = true;
            this.swordSwingCounter = this.swordSwingDuration;
        }
        block27: {
            block28: {
                if (!this.keyH.upPressed && !this.keyH.downPressed && !this.keyH.leftPressed && !this.keyH.rightPressed) break block27;
                if (this.keyH.upPressed) {
                    this.direction = "up";
                } else if (this.keyH.downPressed) {
                    this.direction = "down";
                } else if (this.keyH.leftPressed) {
                    this.direction = "left";
                } else if (this.keyH.rightPressed) {
                    this.direction = "right";
                }
                this.collisionOn = false;
                this.gp.checker.checkTile(this);
                int objIndex = this.gp.checker.checkObject(this, true);
                this.pickUpObject(objIndex);
                if (this.collisionOn) break block28;
                switch (this.direction) {
                    case "up": {
                        this.worldY -= this.speed;
                        break;
                    }
                    case "down": {
                        this.worldY += this.speed;
                        break;
                    }
                    case "left": {
                        this.worldX -= this.speed;
                        break;
                    }
                    case "right": {
                        this.worldX += this.speed;
                    }
                }
            }
            ++this.spriteCounter;
            if (this.spriteCounter > 10) {
                if (this.spriteNum == 1) {
                    this.spriteNum = 2;
                } else if (this.spriteNum == 2) {
                    this.spriteNum = 1;
                }
                this.spriteCounter = 0;
            }
        }
        this.handleMapPortals();
    }

    public void pickUpObject(int i) {
        block17: {
            String objectName;
            if (i == 999) break block17;
            switch (objectName = this.gp.obj[i].name) {
                case "Keys": {
                    this.gp.playSE(1);
                    ++this.hasChest;
                    this.gp.obj[i] = null;
                    this.gp.ui.showMessage("You pick a Key.");
                    break;
                }
                case "Door": {
                    if (this.hasSword) {
                        this.travelThroughDoor(i);
                        break;
                    }
                    if (this.hasChest > 0) {
                        this.gp.playSE(3);
                        --this.hasChest;
                        this.travelThroughDoor(i);
                        break;
                    }
                    this.gp.ui.showMessage("Need a key or sword to pass.");
                    break;
                }
                case "Boots": {
                    this.gp.playSE(2);
                    this.gp.obj[i] = null;
                    ++this.speed;
                    this.gp.ui.showMessage("Movement Speed has Increased.");
                    break;
                }
                case "Chest": {
                    this.gp.ui.gameFinished = true;
                    this.gp.stopMusic();
                    this.gp.playSE(4);
                    break;
                }
                case "Sword": {
                    this.hasSword = true;
                    this.gp.obj[i] = null;
                    this.gp.playSE(2);
                    this.gp.ui.showMessage("You got a Sword! Press SPACE to attack.");
                }
            }
        }
    }

    private void handleMapPortals() {
        int playerCenterX = this.worldX + this.gp.tileSize / 2;
        int playerCenterY = this.worldY + this.gp.tileSize / 2;
        int playerCol = playerCenterX / this.gp.tileSize;
        int playerRow = playerCenterY / this.gp.tileSize;
        int tileNum = this.gp.tileM.mapTileNum[playerCol][playerRow];
        if (this.gp.currentMap == this.gp.mapOverworld) {
            if (tileNum == 14) {
                this.gp.enterBattleStage(this.gp.mapBattle1);
            } else if (tileNum == 15) {
                this.gp.enterBattleStage(this.gp.mapBattle2);
            }
        } else if (tileNum == 11) {
            this.gp.returnToOverworld();
        }
    }

    private void trySwordAttack() {
        int attackRange = this.gp.tileSize;
        int attackX = this.worldX;
        int attackY = this.worldY;
        if ("up".equals(this.direction)) {
            attackY -= attackRange;
        } else if ("down".equals(this.direction)) {
            attackY += attackRange;
        } else if ("left".equals(this.direction)) {
            attackX -= attackRange;
        } else if ("right".equals(this.direction)) {
            attackX += attackRange;
        }
        Rectangle attackArea = new Rectangle(attackX + 8, attackY + 16, 32, 32);
        boolean hit = false;
        for (int i = 0; i < this.gp.obj.length; ++i) {
            if (this.gp.obj[i] == null) continue;
            this.gp.obj[i].solidArea.x = this.gp.obj[i].worldX + this.gp.obj[i].solidAreaDefaultX;
            this.gp.obj[i].solidArea.y = this.gp.obj[i].worldY + this.gp.obj[i].solidAreaDefaultY;
            if ("Door".equals(this.gp.obj[i].name) && attackArea.intersects(this.gp.obj[i].solidArea)) {
                this.travelThroughDoor(i);
                hit = true;
            }
            this.gp.obj[i].solidArea.x = this.gp.obj[i].solidAreaDefaultX;
            this.gp.obj[i].solidArea.y = this.gp.obj[i].solidAreaDefaultY;
        }
        for (int i = 0; i < this.gp.npc.length; ++i) {
            if (!(this.gp.npc[i] instanceof EnemyPlant)) continue;
            EnemyPlant enemy = (EnemyPlant)this.gp.npc[i];
            if (!enemy.alive) continue;
            enemy.solidArea.x = enemy.worldX + enemy.solidAreaDefaultX;
            enemy.solidArea.y = enemy.worldY + enemy.solidAreaDefaultY;
            if (attackArea.intersects(enemy.solidArea)) {
                enemy.alive = false;
                this.gp.npc[i] = null;
                hit = true;
            }
            enemy.solidArea.x = enemy.solidAreaDefaultX;
            enemy.solidArea.y = enemy.solidAreaDefaultY;
        }
        this.gp.playSE(1);
        if (hit) {
            this.gp.ui.showMessage("Sword strike!");
        } else {
            this.gp.ui.showMessage("Swing!");
        }
    }

    private void travelThroughDoor(int doorIndex) {
        if (this.gp.obj[doorIndex] == null) {
            return;
        }
        this.gp.playSE(3);
        if (this.gp.currentMap == this.gp.mapOverworld) {
            if (this.gp.obj[doorIndex].worldX < this.gp.tileSize * 24) {
                this.gp.enterBattleStage(this.gp.mapBattle1);
            } else {
                this.gp.enterBattleStage(this.gp.mapBattle2);
            }
        } else {
            this.gp.returnToOverworld();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
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
        int playerRenderSize = (int)((double)this.gp.tileSize * this.renderScale);
        int playerDrawX = this.screenX - (playerRenderSize - this.gp.tileSize) / 2;
        int playerDrawY = this.screenY - (playerRenderSize - this.gp.tileSize) / 2;
        g2.drawImage(image, playerDrawX, playerDrawY, playerRenderSize, playerRenderSize, null);
        if (this.hasSword && this.swordImage != null) {
            this.drawDirectionalSword(g2, playerDrawX, playerDrawY, playerRenderSize);
        }
    }

    private void drawDirectionalSword(Graphics2D g2, int playerDrawX, int playerDrawY, int playerRenderSize) {
        int swordSize = playerRenderSize / 2;
        double swingProgress = this.swordSwinging ? 1.0 - (double)this.swordSwingCounter / (double)this.swordSwingDuration : 0.0;
        int centerX = playerDrawX + playerRenderSize / 2;
        int centerY = playerDrawY + playerRenderSize / 2;
        int handX = centerX;
        int handY = centerY;
        int swordX = centerX - swordSize / 2;
        int swordY = centerY - swordSize / 2;
        double angleDeg = 0.0;
        if ("up".equals(this.direction)) {
            handX = centerX + 8;
            handY = playerDrawY + playerRenderSize / 3;
            swordX = handX - swordSize / 2;
            swordY = handY - swordSize + 6;
            angleDeg = this.swordSwinging ? (-120.0 + 70.0 * swingProgress) : -95.0;
        } else if ("down".equals(this.direction)) {
            handX = centerX - 8;
            handY = playerDrawY + playerRenderSize - playerRenderSize / 4;
            swordX = handX - swordSize / 2;
            swordY = handY - 6;
            angleDeg = this.swordSwinging ? (35.0 + 70.0 * swingProgress) : 70.0;
        } else if ("left".equals(this.direction)) {
            handX = playerDrawX + playerRenderSize / 4;
            handY = centerY + 4;
            swordX = handX - swordSize + 6;
            swordY = handY - swordSize / 2;
            angleDeg = this.swordSwinging ? (145.0 - 55.0 * swingProgress) : 135.0;
        } else if ("right".equals(this.direction)) {
            handX = playerDrawX + playerRenderSize - playerRenderSize / 4;
            handY = centerY + 4;
            swordX = handX - 6;
            swordY = handY - swordSize / 2;
            angleDeg = this.swordSwinging ? (-35.0 + 55.0 * swingProgress) : -20.0;
        }
        AffineTransform old = g2.getTransform();
        g2.rotate(Math.toRadians(angleDeg), handX, handY);
        g2.drawImage(this.swordImage, swordX, swordY, swordSize, swordSize, null);
        g2.setTransform(old);
    }
}
