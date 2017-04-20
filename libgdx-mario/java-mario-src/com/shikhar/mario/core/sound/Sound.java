package com.shikhar.mario.core.sound;

/**
    The Sound class is a container for sound samples. The sound
    samples are format-agnostic and are stored as a byte array.
*/
public class Sound {

    private byte[] samples;

    /**
        Create a new Sound object with the specified byte array.
        The array is not copied.
    */
    public Sound(byte[] samples) {
        this.samples = samples;
    }


    /**
        Returns this Sound's objects samples as a byte array.
    */
    public byte[] getSamples() {
        return samples;
    }

}
