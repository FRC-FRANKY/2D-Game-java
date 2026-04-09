/*
 * Decompiled with CFR 0.152.
 */
package Object;

import Main.GamePanel;
import Object.SuperObject;
import javax.imageio.ImageIO;

public class OBJ_Keys
extends SuperObject {
    GamePanel gp;

    public OBJ_Keys(GamePanel gp) {
        this.gp = gp;
        this.name = "Keys";
        try {
            this.image = ImageIO.read(this.getClass().getResourceAsStream("/objects/key.png"));
            this.Utool.scaledImage(this.image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
