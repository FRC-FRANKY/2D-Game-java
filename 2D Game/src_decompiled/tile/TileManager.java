package tile;

import Main.GamePanel;
import Main.UtilityTool;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    public tile[] tile;
    public int[][] mapTileNum;
    private final String[] mapPaths = new String[]{"/maps/world version2.txt", "/maps/battle_stage_1.txt", "/maps/battle_stage_2.txt"};

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tile = new tile[50];
        this.mapTileNum = new int[50][50];
        this.gettileImage();
        this.loadMapByType(this.gp.currentMap);
    }

    public void gettileImage() {
        this.setup(0, "grass00", false);
        this.setup(1, "grass00", false);
        this.setup(2, "grass00", false);
        this.setup(3, "grass00", false);
        this.setup(4, "grass00", false);
        this.setup(5, "grass00", false);
        this.setup(6, "grass00", false);
        this.setup(7, "grass00", false);
        this.setup(8, "grass00", false);
        this.setup(9, "grass00", false);
        this.setup(10, "grass00", false);
        this.setup(11, "grass01", false);
        this.setup(12, "water00", true);
        this.setup(13, "water01", true);
        this.setup(14, "water02", true);
        this.setup(15, "water03", true);
        this.setup(16, "water04", true);
        this.setup(17, "water05", true);
        this.setup(18, "water06", true);
        this.setup(19, "water07", true);
        this.setup(20, "water08", true);
        this.setup(21, "water09", true);
        this.setup(22, "water10", true);
        this.setup(23, "water11", true);
        this.setup(24, "water12", true);
        this.setup(25, "water13", true);
        this.setup(26, "road00", false);
        this.setup(27, "road01", false);
        this.setup(28, "road02", false);
        this.setup(29, "road03", false);
        this.setup(30, "road04", false);
        this.setup(31, "road05", false);
        this.setup(32, "road06", false);
        this.setup(33, "road07", false);
        this.setup(34, "road08", false);
        this.setup(35, "road08", false);
        this.setup(36, "road08", false);
        this.setup(37, "road08", false);
        this.setup(38, "road08", false);
        this.setup(39, "earth", false);
        this.setup(40, "wall", true);
        this.setup(41, "tree", true);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool Utool = new UtilityTool();
        try {
            this.tile[index] = new tile();
            this.tile[index].image = ImageIO.read(this.getClass().getResource("/tiles/" + imageName + ".png"));
            this.tile[index].image = Utool.scaledImage(this.tile[index].image, this.gp.tileSize, this.gp.tileSize);
            this.tile[index].collision = collision;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadmap(String filePath) {
        try {
            InputStream is = this.getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int row = 0; row < 50; ++row) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] numbers = line.trim().split("\\s+");
                for (int col = 0; col < 50 && col < numbers.length; ++col) {
                    this.mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }
            }
            br.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void loadMapByType(int mapType) {
        int safeMapType = mapType;
        if (safeMapType < 0 || safeMapType >= this.mapPaths.length) {
            safeMapType = 0;
        }
        this.loadmap(this.mapPaths[safeMapType]);
    }

    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < 50; ++worldRow) {
            for (int worldCol = 0; worldCol < 50; ++worldCol) {
                int tileNum = this.mapTileNum[worldCol][worldRow];
                int worldX = worldCol * this.gp.tileSize;
                int worldY = worldRow * this.gp.tileSize;
                int screenX = worldX - this.gp.player.worldX + this.gp.player.screenX;
                int screenY = worldY - this.gp.player.worldY + this.gp.player.screenY;
                if (worldX + this.gp.tileSize >= this.gp.player.worldX - this.gp.player.screenX - 50 && worldX - this.gp.tileSize <= this.gp.player.worldX + this.gp.player.screenX + 50 && worldY + this.gp.tileSize >= this.gp.player.worldY - this.gp.player.screenY - 50 && worldY - this.gp.tileSize <= this.gp.player.worldY + this.gp.player.screenY + 50) {
                    g2.drawImage(this.tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }
}
