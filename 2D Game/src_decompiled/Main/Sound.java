/*
 * Decompiled with CFR 0.152.
 */
package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        this.soundURL[0] = this.getClass().getResource("/sound/Super Mario.wav");
        this.soundURL[1] = this.getClass().getResource("/sound/coin.wav");
        this.soundURL[2] = this.getClass().getResource("/sound/Rasengan Sound Effect.wav");
        this.soundURL[3] = this.getClass().getResource("/sound/unlock.wav");
        this.soundURL[4] = this.getClass().getResource("/sound/Super Mario (level clear).wav");
        this.soundURL[5] = this.getClass().getResource("/sound/Main Menu BM.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(this.soundURL[i]);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void play() {
        if (this.clip != null) {
            this.clip.start();
        }
    }

    public void loop() {
        if (this.clip != null) {
            this.clip.loop(-1);
        }
    }

    public void stop() {
        if (this.clip != null) {
            this.clip.stop();
        }
    }
}
