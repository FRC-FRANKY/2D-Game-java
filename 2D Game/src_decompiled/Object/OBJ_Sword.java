package Object;

import Main.GamePanel;
import javax.imageio.ImageIO;

public class OBJ_Sword extends SuperObject {
    GamePanel gp;

    public OBJ_Sword(GamePanel gp) {
        this.gp = gp;
        this.name = "Sword";
        try {
            this.image = ImageIO.read(this.getClass().getResourceAsStream("/objects/sword.png"));
            this.Utool.scaledImage(this.image, gp.tileSize, gp.tileSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
