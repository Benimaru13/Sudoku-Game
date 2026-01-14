package game;

import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/**
 * Simple static audio manager; loads small clips from classpath (resources/sounds/*.wav).
 */
public class AudioManager {
    private static final Map<String, Clip> clips = new HashMap<>();
    private static boolean muted = false;

    // Load an audio clip from the given resource path and associate it with the given key
    public static void load(String key, String resourcePath) {
        // try to load audio from resource stream
        try (InputStream audioStream = AudioManager.class.getResourceAsStream(resourcePath)) { 
            // if resource not found, throw exception
            if (audioStream == null) throw new IOException("Resource not found: " + resourcePath);
            // get the audio stream from a buffer stream
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(audioStream))) {
                Clip clip = AudioSystem.getClip(); // empty clip
                clip.open(ais); // load audio data into clip
                clips.put(key, clip); // store clip in map
            }
        } catch (Exception e) {
            System.err.println("Audio load error for " + resourcePath + ": " + e.getMessage());
        }
    }

    public static void play(String key) {
        if (muted) return;
        Clip c = clips.get(key);
        if (c == null) {
            System.err.println("Audio clip not found: " + key);
            return;
        }
        if (c.isRunning()) c.stop(); // stop if already playing
        c.setFramePosition(0); // reset the clip to the beginning
        c.start();
    }

    public static void loop(String key) {
        if (muted) return;
        Clip c = clips.get(key);
        if (c == null) {
            System.err.println("Audio clip not found: " + key);
            return;
        }
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stop(String key) {
        Clip c = clips.get(key);
        if (c == null) {
            System.err.println("Audio clip not found: " + key);
            return;
        }
        if (c.isRunning()) c.stop();
    }

    public static void setMuted(boolean mute) {
        muted = mute;
        if (muted) clips.values().forEach(clip -> { if (clip.isRunning()) clip.stop(); });
    }
}
