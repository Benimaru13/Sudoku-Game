
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/**
 * Simple static audio manager; loads small clips from classpath (resources/sounds/*.wav).
 */
public class AudioManager {
    private static final Map<String, Clip> clips = new HashMap<>();
    private static boolean muted = false;

    public static void load(String key, String resourcePath) {
        try (InputStream is = AudioManager.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new IOException("Resource not found: " + resourcePath);
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is))) {
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clips.put(key, clip);
            }
        } catch (Exception e) {
            System.err.println("Audio load error for " + resourcePath + ": " + e.getMessage());
        }
    }

    public static void play(String key) {
        if (muted) return;
        Clip c = clips.get(key);
        if (c == null) return;
        if (c.isRunning()) c.stop();
        c.setFramePosition(0);
        c.start();
    }

    public static void loop(String key) {
        if (muted) return;
        Clip c = clips.get(key);
        if (c == null) return;
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stop(String key) {
        Clip c = clips.get(key);
        if (c == null) return;
        if (c.isRunning()) c.stop();
    }

    public static void setMuted(boolean m) {
        muted = m;
        if (muted) clips.values().forEach(clip -> { if (clip.isRunning()) clip.stop(); });
    }
}
