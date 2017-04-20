package com.shikhar.mario.core.sound;


import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *  LoopingByteInputStream.java
 * 
 *  The LoopingByteInputStream is a ByteArrayInputStream that
 *  loops indefinitly. The looping stops when the close() method is called.
 *     
 *  Notice: this class extends the normal ByteArrayInputStream. We override the
 *  read method to make it continually read over and over until close() is called.
 *     
 *  In the SimpleSoundPlayer, if we create our stream as a looping stream instead
 *  of a normal one, we will continually replay the sound instead of playing it once.
 * 
*/
public class LoopingByteInputStream extends ByteArrayInputStream {

    private boolean closed;
    
    // Creates a new LoopingByteInputStream with the specified byte array.
    // The array is not copied.
    public LoopingByteInputStream(byte[] buffer) {
        super(buffer);
        closed = false;
    }

    // This method is overridden
    // Reads length bytes from the array. If the end of the array is reached, the reading 
    //starts over from the beginning of the array. Returns -1 if the array has been closed
    public int read(byte[] buffer, int offset, int length) {
        if (closed) {
            return -1;
        }
        int totalBytesRead = 0;

        while (totalBytesRead < length) {
            int numBytesRead = super.read(buffer,
                offset + totalBytesRead,
                length - totalBytesRead);
            if (numBytesRead > 0) {
                totalBytesRead += numBytesRead;
            }
            else {
                reset();
            }
        }
        return totalBytesRead;
    }

    // This method is overridden
    // Closes the stream. Future calls to the read() methods will return -1.
    public void close() throws IOException {
        super.close();
        closed = true;
    }

}
