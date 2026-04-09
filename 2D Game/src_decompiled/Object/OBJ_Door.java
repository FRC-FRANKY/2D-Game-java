/*
 * Decompiled with CFR 0.152.
 */
package Object;

import Main.GamePanel;
import Object.SuperObject;
import javax.imageio.ImageIO;

public class OBJ_Door
extends SuperObject {
    GamePanel gp;

    public OBJ_Door(GamePanel gp) {
        this.gp = gp;
        this.name = "Door";
        try {
            this.image = ImageIO.read(this.getClass().getResourceAsStream("/objects/door.png"));
            this.Utool.scaledImage(this.image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.collision = true;
    }
}
