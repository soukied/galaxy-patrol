package com.soukied.game;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1010854727450177696L;

	public static final int HEIGHT = 144;
	public static final int WIDTH = HEIGHT * 10 / 9;
	public static final int FPS = 60;
	public static final int SCALE = 4;
	public static final String TITLE = "Galaxy Patrol versi 0.0.1 alpha";
	
	private static JFrame window;
	public static boolean isFocused() {
		return window.isFocused();
	}
	
	private Thread thread;
	private boolean running = false;
	
	public static void main(String args[]) {
		window = new JFrame(TITLE);
		window.setSize(WIDTH * SCALE,  HEIGHT * SCALE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		State.setState(State.MENU_STATE);
		
		window.addKeyListener(new keyboardEv());
		
		window.add(new Game());
		window.pack();
	}
	
	public Game() {
		setSize(WIDTH * SCALE, HEIGHT * SCALE);
		setFocusable(false);
		start();
	}
	
	private synchronized void start() {
		if (running) return;
		running=  true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if (!running) return;
		try {
			thread.join();
			running = false;
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		double timePerMs = 1000000000.0 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long now;
		double time = 0;
		init();
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerMs;
			time += now - lastTime;
			lastTime = now;
			if (delta > 1) {
				update();
				render();
				delta--;
			}
			
			if (time > 1000000000) {
				time = 0;
			}
		}
		stop();
	}
	
	private void init() {
		State.getState().init();
	}
	
	private void update() {
		State.getState().update();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.scale(SCALE, SCALE);

		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		State.getState().render(g);

//		
		g.dispose();
		bs.show();
	}
}
