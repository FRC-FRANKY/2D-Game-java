/*
 * Decompiled with CFR 0.152.
 */
package Main;

import Entity.EnemyPlant;
import Main.GamePanel;
import Object.OBJ_Boots;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Keys;
import Object.OBJ_Sword;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        this.gp.obj[0] = new OBJ_Keys(this.gp);
        this.gp.obj[0].worldX = 23 * this.gp.tileSize;
        this.gp.obj[0].worldY = 7 * this.gp.tileSize;
        this.gp.obj[1] = new OBJ_Keys(this.gp);
        this.gp.obj[1].worldX = 32 * this.gp.tileSize;
        this.gp.obj[1].worldY = 42 * this.gp.tileSize;
        this.gp.obj[2] = new OBJ_Keys(this.gp);
        this.gp.obj[2].worldX = 23 * this.gp.tileSize;
        this.gp.obj[2].worldY = 42 * this.gp.tileSize;
        this.gp.obj[7] = new OBJ_Keys(this.gp);
        this.gp.obj[7].worldX = 12 * this.gp.tileSize;
        this.gp.obj[7].worldY = 34 * this.gp.tileSize;
        this.gp.obj[3] = new OBJ_Door(this.gp);
        this.gp.obj[3].worldX = 10 * this.gp.tileSize;
        this.gp.obj[3].worldY = 12 * this.gp.tileSize;
        this.gp.obj[4] = new OBJ_Door(this.gp);
        this.gp.obj[4].worldX = 14 * this.gp.tileSize;
        this.gp.obj[4].worldY = 28 * this.gp.tileSize;
        this.gp.obj[5] = new OBJ_Door(this.gp);
        this.gp.obj[5].worldX = 30 * this.gp.tileSize;
        this.gp.obj[5].worldY = 40 * this.gp.tileSize;
        this.gp.obj[6] = new OBJ_Door(this.gp);
        this.gp.obj[6].worldX = 36 * this.gp.tileSize;
        this.gp.obj[6].worldY = 30 * this.gp.tileSize;
        this.gp.obj[8] = new OBJ_Chest(this.gp);
        this.gp.obj[8].worldX = 10 * this.gp.tileSize;
        this.gp.obj[8].worldY = 8 * this.gp.tileSize;
        this.gp.obj[9] = new OBJ_Boots(this.gp);
        this.gp.obj[9].worldX = 38 * this.gp.tileSize;
        this.gp.obj[9].worldY = 10 * this.gp.tileSize;
        this.gp.obj[10] = new OBJ_Sword(this.gp);
        this.gp.obj[10].worldX = 25 * this.gp.tileSize;
        this.gp.obj[10].worldY = 25 * this.gp.tileSize;
    }

    public void setNPC() {
        this.gp.npc[0] = new EnemyPlant(this.gp);
        this.gp.npc[0].worldX = 20 * this.gp.tileSize;
        this.gp.npc[0].worldY = 20 * this.gp.tileSize;
        this.gp.npc[1] = new EnemyPlant(this.gp);
        this.gp.npc[1].worldX = 34 * this.gp.tileSize;
        this.gp.npc[1].worldY = 34 * this.gp.tileSize;
    }
}
