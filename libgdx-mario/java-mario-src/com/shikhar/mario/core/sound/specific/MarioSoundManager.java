package com.shikhar.mario.core.sound.specific;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;

import com.shikhar.mario.core.sound.SoundManager;


public class MarioSoundManager {
	
	private static final AudioFormat f1 = new AudioFormat(22050, 8, 1, true, true); // 22050 Hz.
	private static final AudioFormat f2 = new AudioFormat(10512, 8, 1, true, true); // 10512 Hz.
	private static final String fileName = "sounds/mario_sounds.txt";
	
	private SoundManager s1;
	private SoundManager s2;
	
	private class SoundRecord {
		public String name;
		public String location;
		public int hz;
		public SoundRecord(String name, String location, int hz) {
			this.name = name;
			this.location = location;
			this.hz = hz;
		}
	}
	
	public MarioSoundManager() {
		s1 = new SoundManager(f1);
		s2 = new SoundManager(f2);
		
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName)); 
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
		while(s.hasNextLine()) {
			
			String line = s.nextLine();
			Scanner ls = new Scanner(line);
			SoundRecord sr = new SoundRecord(ls.next(), ls.next(), ls.nextInt());

		}
		
		
	}

}
