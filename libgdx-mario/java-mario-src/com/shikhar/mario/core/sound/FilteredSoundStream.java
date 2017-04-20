package com.shikhar.mario.core.sound;


import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;


/**
    The FilteredSoundStream class is a FilterInputStream that
    applies a SoundFilter to the underlying input stream.
    @see SoundFilter
*/
public class FilteredSoundStream extends FilterInputStream {

    private static final int REMAINING_SIZE_UNKNOWN = -1;

    private SoundFilter soundFilter;
    private int remainingSize;

    /**
        Creates a new FilteredSoundStream object with the
        specified InputStream and SoundFilter.
    */
    public FilteredSoundStream(InputStream in,
        SoundFilter soundFilter)
    {
        super(in);
        this.soundFilter = soundFilter;
        remainingSize = REMAINING_SIZE_UNKNOWN;
    }


    /**
        Overrides the FilterInputStream method to apply this
        filter whenever bytes are read
    */
    public int read(byte[] samples, int offset, int length)
        throws IOException
    {
        // read and filter the sound samples in the stream
        int bytesRead = super.read(samples, offset, length);
        if (bytesRead > 0) {
            soundFilter.filter(samples, offset, bytesRead);
            return bytesRead;
        }

        // if there are no remaining bytes in the sound stream,
        // check if the filter has any remaining bytes ("echoes").
        if (remainingSize == REMAINING_SIZE_UNKNOWN) {
            remainingSize = soundFilter.getRemainingSize();
            // round down to nearest multiple of 4
            // (typical frame size)
            remainingSize = remainingSize / 4 * 4;
        }
        if (remainingSize > 0) {
            length = Math.min(length, remainingSize);

            // clear the buffer
            for (int i=offset; i<offset+length; i++) {
                samples[i] = 0;
            }

            // filter the remaining bytes
            soundFilter.filter(samples, offset, length);
            remainingSize-=length;

            // return
            return length;
        }
        else {
            // end of stream
            return -1;
        }
    }

}
