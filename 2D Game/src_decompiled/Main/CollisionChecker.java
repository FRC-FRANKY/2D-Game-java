/*
 * Decompiled with CFR 0.152.
 */
package Main;

import Entity.Entity;
import Main.GamePanel;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int entityLeftCol = entityLeftWorldX / this.gp.tileSize;
        int entityRightCol = entityRightWorldX / this.gp.tileSize;
        int entityTopRow = entityTopWorldY / this.gp.tileSize;
        int entityBottomRow = entityBottomWorldY / this.gp.tileSize;
        switch (entity.direction) {
            case "up": {
                entityTopRow = (entityTopWorldY - entity.speed) / this.gp.tileSize;
                int tileNum1 = this.gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                int tileNum2 = this.gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (!this.gp.tileM.tile[tileNum1].collision && !this.gp.tileM.tile[tileNum2].collision) break;
                entity.collisionOn = true;
                break;
            }
            case "down": {
                entityBottomRow = (entityBottomWorldY + entity.speed) / this.gp.tileSize;
                int tileNum1 = this.gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                int tileNum2 = this.gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (!this.gp.tileM.tile[tileNum1].collision && !this.gp.tileM.tile[tileNum2].collision) break;
                entity.collisionOn = true;
                break;
            }
            case "left": {
                entityLeftCol = (entityLeftWorldX - entity.speed) / this.gp.tileSize;
                int tileNum1 = this.gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                int tileNum2 = this.gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (!this.gp.tileM.tile[tileNum1].collision && !this.gp.tileM.tile[tileNum2].collision) break;
                entity.collisionOn = true;
                break;
            }
            case "right": {
                entityRightCol = (entityRightWorldX + entity.speed) / this.gp.tileSize;
                int tileNum1 = this.gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                int tileNum2 = this.gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (!this.gp.tileM.tile[tileNum1].collision && !this.gp.tileM.tile[tileNum2].collision) break;
                entity.collisionOn = true;
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        int i = 0;
        while (i < this.gp.obj.length) {
            block21: {
                if (this.gp.obj[i] == null) break block21;
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                this.gp.obj[i].solidArea.x = this.gp.obj[i].worldX + this.gp.obj[i].solidArea.x;
                this.gp.obj[i].solidArea.y = this.gp.obj[i].worldY + this.gp.obj[i].solidArea.y;
                switch (entity.direction) {
                    case "up": {
                        entity.solidArea.y -= entity.speed;
                        if (!entity.solidArea.intersects(this.gp.obj[i].solidArea)) break;
                        if (this.gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (!player) break;
                        index = i;
                        break;
                    }
                    case "down": {
                        entity.solidArea.y += entity.speed;
                        if (!entity.solidArea.intersects(this.gp.obj[i].solidArea)) break;
                        if (this.gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (!player) break;
                        index = i;
                        break;
                    }
                    case "left": {
                        entity.solidArea.x -= entity.speed;
                        if (!entity.solidArea.intersects(this.gp.obj[i].solidArea)) break;
                        if (this.gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (!player) break;
                        index = i;
                        break;
                    }
                    case "right": {
                        entity.solidArea.x += entity.speed;
                        if (!entity.solidArea.intersects(this.gp.obj[i].solidArea)) break;
                        if (this.gp.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if (!player) break;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                this.gp.obj[i].solidArea.x = this.gp.obj[i].solidAreaDefaultX;
                this.gp.obj[i].solidArea.y = this.gp.obj[i].solidAreaDefaultY;
            }
            ++i;
        }
        return index;
    }
}
