package src.game;

import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/**
 * Audio manager for loading and playing sound effects in the Sudoku game.
 * Provides static methods to load audio clips from resources, play them, loop them,
 * and control muting. Audio clips are cached in memory for quick access.
 * 
 * Supported audio formats: WAV files
 * 
 * @author Your Team
 * @version 1.0
 */
public class AudioManager {
    /** Static map storing loaded audio clips by key for quick access */
    private static final Map<String, Clip> clips = new HashMap<>();
    
    /** Global mute flag; when true, no sounds are played */
    private static boolean muted = false;

    /**
     * Loads an audio clip from a resource path and caches it with the given key.
     * Must be called before playing a sound.
     * 
     * @param key a unique identifier for this audio clip (e.g., "correct", "wrong")
     * @param resourcePath the classpath resource path to the WAV file (e.g., "/sounds/correct.wav")
     */
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

    /**
     * Plays a previously loaded audio clip from the beginning.
     * If the clip is already playing, it stops and restarts from the beginning.
     * Does nothing if the audio system is muted.
     * 
     * @param key the identifier of the audio clip to play
     */
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

    /**
     * Plays a previously loaded audio clip in a continuous loop.
     * Does nothing if the audio system is muted.
     * 
     * @param key the identifier of the audio clip to loop
     */
    public static void loop(String key) {
        if (muted) return;
        Clip c = clips.get(key);
        if (c == null) {
            System.err.println("Audio clip not found: " + key);
            return;
        }
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playback of a previously loaded audio clip.
     * 
     * @param key the identifier of the audio clip to stop
     */
    public static void stop(String key) {
        Clip c = clips.get(key);
        if (c == null) {
            System.err.println("Audio clip not found: " + key);
            return;
        }
        if (c.isRunning()) c.stop();
    }

    /**
     * Sets the global mute state for all audio playback.
     * When muted, all play() and loop() calls are ignored, and existing sounds are stopped.
     * 
     * @param mute true to mute all sounds, false to unmute
     */
    public static void setMuted(boolean mute) {
        muted = mute;
        if (muted) clips.values().forEach(clip -> { if (clip.isRunning()) clip.stop(); });
    }
}
