/*
 * Decompiled with CFR 0.152.
 */
package Object;

import Main.GamePanel;
import Object.SuperObject;
import javax.imageio.ImageIO;

public class OBJ_Boots
extends SuperObject {
    GamePanel gp;

    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;
        this.name = "Boots";
        try {
            this.image = ImageIO.read(this.getClass().getResourceAsStream("/objects/boots.png"));
            this.Utool.scaledImage(this.image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
