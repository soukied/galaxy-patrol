package com.soukied.game;

import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private String fileName;
	private Clip clip;
	private boolean isRunning = false;
	private Thread songThread;
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
			new Thread(new Runnable() {
				public void run() {
					try {						
						Clip tempClip = AudioSystem.getClip();
						tempClip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileName)));
						tempClip.start();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			return;
		}
		if (isRunning && !isSfx) return;
		songThread = new Thread(new Runnable() {
			public void run() {
				clip.start();
				isRunning = true;
			}
		});
		songThread.start();
	}
	
	public void stop() {
		if (!isRunning) return;
		clip.stop();
	}
	
	public void pause() {
		if (!isRunning) return;
		try {
			songThread.join();
			clip.stop();
			clip.setFramePosition(0);
			isRunning = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
