package com.shikhar.mario.core.sound;

/**
    A abstract class designed to filter sound samples.
    Since SoundFilters may use internal buffering of samples,
    a new SoundFilter object should be created for every sound
    played. However, SoundFilters can be reused after they are
    finished by called the reset() method.
    <p>Assumes all samples are 16-bit, signed, little-endian
    format.
    @see FilteredSoundStream
*/
public abstract class SoundFilter{

    /**
        Resets this SoundFilter. Does nothing by default.
    */
    public void reset() {
        // do nothing
    }


    /**
        Gets the remaining size, in bytes, that this filter
        plays after the sound is finished. An example would
        be an echo that plays longer than it's original sound.
        This method returns 0 by default.
    */
    public int getRemainingSize() {
        return 0;
    }


    /**
        Filters an array of samples. Samples should be in
        16-bit, signed, little-endian format.
    */
    public void filter(byte[] samples) {
        filter(samples, 0, samples.length);
    }


    /**
        Filters an array of samples. Samples should be in
        16-bit, signed, little-endian format. This method
        should be implemented by subclasses.
    */
    public abstract void filter(
        byte[] samples, int offset, int length);


    /**
        Convenience method for getting a 16-bit sample from a
        byte array. Samples should be in 16-bit, signed,
        little-endian format.
    */
    public static short getSample(byte[] buffer, int position) {
        return (short)(
            ((buffer[position+1] & 0xff) << 8) |
            (buffer[position] & 0xff));
    }


    /**
        Convenience method for setting a 16-bit sample in a
        byte array. Samples should be in 16-bit, signed,
        little-endian format.
    */
    public static void setSample(byte[] buffer, int position,
        short sample)
    {
        buffer[position] = (byte)(sample & 0xff);
        buffer[position+1] = (byte)((sample >> 8) & 0xff);
    }

}
