package com.soukied.game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import com.soukied.game.Entity;
import com.soukied.game.Game;
import com.soukied.game.State;
import com.soukied.game.Util;

public class gameState extends State {
	
	private HashMap<String, Boolean> keys = new HashMap<String, Boolean>();
	private Entity player;
	private Entity[] stars = new Entity[50];
	private ArrayList<Entity> bullets = new ArrayList<Entity>(); 
	private ArrayList<Entity> asteroids = new ArrayList<Entity>();
	private int bulletVelX = -5;
	private int SCORE = 0;
	private boolean isLost = false;
	
	void shootBullet() {
		Entity newBullet = new Entity(player.getX() + player.getWidth() / 2, player.getY());
		newBullet.setSize(1, 6);
		bullets.add(newBullet);
	} 
	
	int getRandomVel() {
		int value = 0;
		int isNegative = (int) (Math.random() * 2);
		value = (int) (Math.random() * 6);
		
		return isNegative == 1 ? value * -1 : value;
	}
	
	int getRandom(int num) {
		double range = Math.random() * (num + 1);
		return (int)range;
	}
	
	int getRandom(int num1, int num2) {
		double range = Math.random() * ((num2 - num1) + 1);
		return (int) (num1 + range);
	}
	
	
	public void init() {
		
		Util.gamePrint("Permainan dimulai");
		
		// initialize player
		int playerSize = 10;
		player = new Entity((Game.WIDTH - playerSize) / 2, Game.HEIGHT - 20);
		player.setSize(playerSize);
		
		// initialize stars
		for (int i = 0; i < stars.length; i++) {
			int starSize = 1;
			Entity star = new Entity(getRandom(Game.WIDTH-starSize), getRandom(Game.HEIGHT-starSize));
			star.setSize(starSize);
			stars[i] = star;
		}
	
		// initialize key event
		keys.put("UP", false);
		keys.put("DOWN", false);
		keys.put("LEFT", false);
		keys.put("RIGHT", false);
		keys.put("SPACE" , false);
	}
	
	int velX = getRandomVel();
	int velY = getRandomVel();
	
	// player update
	int tick = 0;
	int bulletCooldown = 0;
	public void update() {
		if (Game.isFocused() && !isLost) {
			tick++;
			
			// player movement event		
			for (int i = 0 ; i < bullets.size(); i++) {
				Entity bullet = bullets.get(i);
				bullet.moveY(bulletVelX);
				
				if (bullet.getX() + bullet.getWidth() < 0)
					bullets.remove(i);
			}
			
			// asteroids initialize
			if (tick % (Game.FPS * 3) == 0) {
				int asteroidSize = 10;
				Entity asteroid = new Entity(getRandom(Game.WIDTH - asteroidSize), -asteroidSize);
				asteroid.setSize(asteroidSize, asteroidSize);
				asteroids.add(asteroid);
			}
			
			// asteroid movement
			for (int i = 0 ; i < asteroids.size() ; i++) {
				Entity asteroid = asteroids.get(i);
				asteroid.moveY(2);
				if (asteroid.getY() > Game.HEIGHT) asteroids.remove(i);
			}
			
			// stars movement
			for (int i = 0 ; i < stars.length; i++) {
				Entity star = stars[i];
				star.moveY(1);
				if (star.getY() >= Game.HEIGHT) star.setY(-star.getHeight());
			}
			
			int playerMovVel = 3;
			
			if (keys.get("UP")) player.moveY(playerMovVel * -1);
			if (keys.get("DOWN")) player.moveY(playerMovVel);
			if (keys.get("RIGHT")) player.moveX(playerMovVel);
			if (keys.get("LEFT")) player.moveX(playerMovVel * -1);
		
			
			if (keys.get("SPACE")) {
				if (bulletCooldown % (int)(60/5) == 0)
					shootBullet();
				bulletCooldown++;
			} else {
				bulletCooldown = 0;
			}
			
			if (player.getX() < 0) {
				player.setX(0);
				velX *= -1;
			}
			if (player.getX() + player.getWidth() > Game.WIDTH) {
				player.setX(Game.WIDTH - player.getWidth());
				velX *= -1;
			}
			if (player.getY() < 0) {
				player.setY(0);
				velY *= -1;
			}
			if (player.getY() + player.getHeight() > Game.HEIGHT) {
				player.setY(Game.HEIGHT - player.getHeight());
				velY *= -1;
			}
			
			if (tick % (Game.FPS * 3 ) == 0) SCORE++;
			
		}
	}
	
	Color randomColor() {
		int randomRed = (int) (Math.random() * 256);
		int randomBlue = (int) (Math.random() * 256);
		int randomGreen = (int) (Math.random() * 256);
		
		return new Color(randomRed, randomGreen, randomBlue);
	}
	
	public void render(Graphics2D g) {
		
		// background render
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		// stars render
		g.setColor(Color.WHITE);
		for (int i = 0 ; i < stars.length; i++) {
			Entity star = stars[i];
			g.fillRect(star.getX(), star.getY(), star.getWidth(), star.getHeight());
		}
		
		// bullets render
		g.setColor(Color.YELLOW);
		for (int i = 0 ; i < bullets.size(); i++) {
			Entity bullet = bullets.get(i);
			g.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
		}
		
		// asteroids render
		g.setColor(new Color(150, 150, 150));
		for (int i = 0 ; i < asteroids.size(); i++) {
			Entity asteroid = asteroids.get(i);
			g.fillRect(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight());
		}
		
		// player render
		g.setColor(Color.CYAN);
		//g.drawImage(spaceshipImage, player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);
		g.fillRect(player.getX(), player.getY(), player.getHeight(), player.getWidth());
		
		
		// scoring system
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Consolas",Font.BOLD, 4));
		g.drawString("Score : " + SCORE, 2, 5);
	
		
		// if isn't focused
		if (!Game.isFocused()) {
			g.setColor(new Color(150,150,150,150));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
	}
	
	@Override
	public void KeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_UP) 
			keys.put("UP", true);
		else if (keyCode == KeyEvent.VK_LEFT) 
			keys.put("LEFT", true);
		else if (keyCode == KeyEvent.VK_DOWN)
			keys.put("DOWN", true);
		else if (keyCode == KeyEvent.VK_RIGHT)
			keys.put("RIGHT", true);
		else if (keyCode == KeyEvent.VK_SPACE)
			keys.put("SPACE", true);
	}
	
	@Override
	public void KeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_UP) 
			keys.put("UP", false);
		else if (keyCode == KeyEvent.VK_LEFT) 
			keys.put("LEFT", false);
		else if (keyCode == KeyEvent.VK_DOWN)
			keys.put("DOWN", false);
		else if (keyCode == KeyEvent.VK_RIGHT)
			keys.put("RIGHT", false);
		else if (keyCode == KeyEvent.VK_SPACE)
			keys.put("SPACE", false);
	}
	
}
