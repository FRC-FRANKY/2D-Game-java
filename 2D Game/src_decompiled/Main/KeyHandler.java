/*
 * Decompiled with CFR 0.152.
 */
package Main;

import Main.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler
implements KeyListener {
    GamePanel gp;
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean spacebarPressed;
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (this.gp.gameState == this.gp.titleState) {
            if (code == 87) {
                --this.gp.ui.commandNum;
                if (this.gp.ui.commandNum < 0) {
                    this.gp.ui.commandNum = 2;
                }
            }
            if (code == 83) {
                ++this.gp.ui.commandNum;
                if (this.gp.ui.commandNum > 2) {
                    this.gp.ui.commandNum = 0;
                }
            }
            if (code == 10) {
                if (this.gp.ui.commandNum == 0) {
                    this.gp.gameState = this.gp.playState;
                    this.gp.playMusic(0);
                }
                if (this.gp.ui.commandNum == 1) {
                    System.exit(0);
                }
            }
        }
        if (code == 87) {
            this.upPressed = true;
        }
        if (code == 83) {
            this.downPressed = true;
        }
        if (code == 65) {
            this.leftPressed = true;
        }
        if (code == 68) {
            this.rightPressed = true;
        }
        if (code == 80) {
            if (this.gp.gameState == this.gp.playState) {
                this.gp.gameState = this.gp.pauseState;
            } else if (this.gp.gameState == this.gp.pauseState) {
                this.gp.gameState = this.gp.playState;
            }
        }
        if (code == 82) {
            --this.gp.ui.commandNum;
            if (this.gp.ui.commandNum < 0) {
                this.gp.ui.commandNum = 1;
            }
        }
        if (code == 70) {
            ++this.gp.ui.commandNum;
            if (this.gp.ui.commandNum < 1) {
                this.gp.ui.commandNum = 0;
            }
        }
        if (code == 84) {
            if (!this.checkDrawTime) {
                this.checkDrawTime = true;
            } else if (this.checkDrawTime) {
                this.checkDrawTime = false;
            }
        }
        if (code == 32) {
            this.spacebarPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 87) {
            this.upPressed = false;
        }
        if (code == 83) {
            this.downPressed = false;
        }
        if (code == 65) {
            this.leftPressed = false;
        }
        if (code == 68) {
            this.rightPressed = false;
        }
        if (code == 32) {
            this.spacebarPressed = false;
        }
    }
}
