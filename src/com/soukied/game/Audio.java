package com.soukied.game;

import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private String fileName;
	private Clip clip;
	private boolean isRunning = false;
	private boolean isSfx = false;
	private InputStream audioFile;
	
	public Audio(String filename) {
		this.fileName = filename;
		try {
			audioFile = getClass().getResourceAsStream(filename);
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(audioFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Audio(String filename, boolean isSfx) {
		this(filename);
		this.isSfx = isSfx;
	}
	
	public void setLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void play() {
		if (isSfx) {				
			try {
				Clip tempClip = AudioSystem.getClip();
				tempClip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileName)));
				tempClip.start();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}					
		}
		if (isRunning && !isSfx) return;
			clip.start();
			isRunning = true;
	}
	
	public void stop() {
		if (!isRunning) return;
		clip.stop();
		clip.setFramePosition(0);
		isRunning = false;
	}
	
	public void pause() {
		if (!isRunning) return;
			clip.stop();
			isRunning = false;
	}
}
