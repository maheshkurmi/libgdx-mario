package com.shikhar.mario.core.sound;


/**
 * MidiPlayer.java
 * See MidiTest2.java to easily see how to use this object
 * See MidiTest1.java for a more complex application using a meta event
 * 
 * The Java Sound API provides MIDI sound capabilities. To play MIDI music, you need two
 * objects: Sequence and Sequencer. A Sequence object contains the MIDI data, and a
 * Sequencer sends a Sequence to the MIDI synthesizer.
 * 
 * Generally, playing a MIDI file looks something like this:
 * Sequence sequence = MidiSystem.getSequence(new File(filename));
 * Sequencer sequencer = MidiSystem.getSequencer();
 * sequencer.open();
 * sequencer.setSequence(sequence);
 * sequencer.start();
 * 
 * By default, the Sequencer plays a Sequence once and then stops. To loop a 
 * Sequence, you need to be notified when the music is done playing and start over.
 * This is done with a MetaEventListener.
 * 
 */

import java.io.*;
import javax.sound.midi.*;

public class MidiPlayer implements MetaEventListener {

    // Midi meta event integer for a track ending
    public static final int END_OF_TRACK_MESSAGE = 47;

    private Sequencer sequencer; // play through this sequencer
    private boolean loop;
    private boolean paused;

    // Creates a new MidiPlayer object.
    // Gets the sequencer to play a sequence
    public MidiPlayer() {
        try {
        	// MidiSystem is a lot like AudioSystem, in that it allows you to
        	// get the proper materials needed to begin.
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
        }
        catch (MidiUnavailableException ex) {
            sequencer = null;
        }
    }
    
    // Loads a sequence from the file system. Returns null if an error occurs.
    public Sequence getSequence(String filename) {
        try {
            return this.getSequence(new FileInputStream("java-mario-src/"+filename));
        }
        catch (IOException ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    // Loads a sequence from an input stream. Returns null if an error occurs.
    public Sequence getSequence(InputStream is) {
        try {
            if (!is.markSupported()) {
                is = new BufferedInputStream(is);
            }
            // get the sequence
            Sequence s = MidiSystem.getSequence(is);
            // close the stream and return the sequence to play!
            is.close();
            return s;
        }
        catch (InvalidMidiDataException ex) {
            //ex.printStackTrace();
            return null;
        }
        catch (IOException ex) {
            //ex.printStackTrace();
            return null;
        }
    }

    // Plays a sequence, optionally looping. This method returns
    // immediately. The sequence is not played if it is invalid.
    public void play(Sequence sequence, boolean loop) {
    	// must have a sequencer, sequence, and sequencer must be open
        if (sequencer != null && sequence != null && sequencer.isOpen()) {
            try {
                sequencer.setSequence(sequence);
                sequencer.start();
                paused=false;
                sequencer.setTempoFactor((float) 1);
                this.loop = loop;
            }
            catch (InvalidMidiDataException ex) {
                ex.printStackTrace();
            }
        }
    }

    // This method is called by the sound system when a meta
    // event occurs. In this case, when the end-of-track meta
    // event is received, the sequence is restarted if
    // looping is on.
    public void meta(MetaMessage event) {
        if (event.getType() == END_OF_TRACK_MESSAGE) {
            if (sequencer != null && sequencer.isOpen() && loop) {
            	// tick postion required to reset the sequencer
            	sequencer.setTickPosition(0);
                sequencer.start();
            } else {
            	System.exit(0);
            }
        }
    }

    // Stops the sequencer and resets its position to 0.
    public void stop() {
         if (sequencer != null && sequencer.isOpen()) {
             sequencer.stop();
             sequencer.setMicrosecondPosition(0);
         }
    }

    // Closes the sequencer
    public void close() {
         if (sequencer != null && sequencer.isOpen()) {
             sequencer.close();
         }
    }

    // Gets the sequencer
    public Sequencer getSequencer() {
        return sequencer;
    }

    // Sets the paused state. Music may not imediately pause
    public void setPaused(boolean paused) {
        if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
            this.paused = paused;
            if (paused) {
                sequencer.stop();
            }
            else {
                sequencer.start();
            }
        }
    }

    // Returns the paused state
    public boolean isPaused() {
        return paused;
    }

}
