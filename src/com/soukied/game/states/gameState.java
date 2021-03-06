package com.soukied.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import com.soukied.game.Asset;
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
	private boolean isPaused = false;
	
	// pause options
	private final int
	OPTION_RESUME = 0,
	OPTION_EXIT = 1;
	
	private int currentPauseOption = OPTION_RESUME;
	
	private int HEALTH = 3;
	
	void shootBullet() {
		Entity newBullet = new Entity(player.getX() + player.getWidth() / 2, player.getY());
		newBullet.setSize(1, 6);
		bullets.add(newBullet);
		Asset.playerShootSound.play();
	} 
	
	int getRandomVel() {
		int value = 0;
		int isNegative = (int) (Math.random() * 2);
		value = (int) (Math.random() * 6);
		
		return isNegative == 1 ? value * -1 : value;
	}
	
	int getRandom(int num) {
		return (int)(Math.random() * (num + 1));
	}
	
	int getRandom(int num1, int num2) {
		return (int) (num1 + (Math.random() * ((num2 - num1) + 1)));
	}
	
	@Override
	public void init() {
		Asset.gameSong.setLoop();
		Asset.gameSong.play();
		
		// initialize player
		int playerSize = 10;
		player = new Entity((Game.WIDTH - playerSize) / 2, Game.HEIGHT - 20);
		player.setSize(playerSize);
		
		// initialize stars
		for (int i = 0; i < stars.length; i++) {
			int starSize = 1;
			Entity star = new Entity(getRandom(Game.WIDTH - starSize), getRandom(Game.HEIGHT - starSize));
			star.setSize(starSize);
			stars[i] = star;
		}
	
		// initialize key event
		keys.put("UP", false);
		keys.put("DOWN", false);
		keys.put("LEFT", false);
		keys.put("RIGHT", false);
		keys.put("SPACE" , false);
		keys.put("ESCAPE" , false);
		keys.put("ENTER" , false);

	}
	
	// player update
	int tick = 0;
	int bulletCooldown = 0;
	@Override
	public void update() {
		if (Game.isFocused() && !isLost && !isPaused) {
			
			if (HEALTH < 1) isLost = true;
				tick++;
			
			// player movement event		
			for (int i = 0 ; i < bullets.size(); i++) {
				Entity bullet = bullets.get(i);
				bullet.moveY(bulletVelX);
				
				for (int j = 0; j < asteroids.size(); j++) {
					Entity asteroid = asteroids.get(j);
					if (asteroid.isCollide(bullet)) {
						Asset.collideSound.play();
						SCORE++;
						asteroids.remove(j);
						bullets.remove(i);
					}
				}
				
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
				if (stars.length > 0)
					star.moveY(1);
				if (star.getY() >= Game.HEIGHT) star.setY(-star.getHeight());
			}
			
			int playerMovVel = 2;
			
			if (keys.get("UP")) player.moveY(playerMovVel * -1);
			if (keys.get("DOWN")) player.moveY(playerMovVel);
			if (keys.get("RIGHT")) player.moveX(playerMovVel);
			if (keys.get("LEFT")) player.moveX(playerMovVel * -1);
			
			// collison checker
			for (int i = 0; i < asteroids.size(); i++) {
				Entity asteroid = asteroids.get(i);
				if (player.isCollide(asteroid)) {
					Asset.collideSound.play();
					asteroids.remove(i);
					HEALTH--;
				}
			}
			
			if (keys.get("SPACE")) {
				if (bulletCooldown % (int)(60/5) == 0)
					shootBullet();
				bulletCooldown++;
			} else {
				bulletCooldown = 0;
			}
			
			if (player.getX() < 0) {
				player.setX(0);
			}
			if (player.getX() + player.getWidth() > Game.WIDTH) {
				player.setX(Game.WIDTH - player.getWidth());	
			}
			if (player.getY() < 0) {
				player.setY(0);
			}
			if (player.getY() + player.getHeight() > Game.HEIGHT) {
				player.setY(Game.HEIGHT - player.getHeight());
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
	
	@Override
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
		g.setFont(Asset.JoystixFont.deriveFont(5f));
		Util.drawString(g,"Skor : " + SCORE, 2, 0);
		Util.drawStringToRight(g, "Nyawa : " + HEALTH, 0);
	
		
		// if isn't focused
		if (!Game.isFocused() || isLost || isPaused) {
			g.setColor(new Color(150,150,150,150));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
		
		if (isLost) {
			int _LOST_OFFSET = 0;
			g.setColor(Color.CYAN);
			g.setFont(Asset.LunchtimeFont.deriveFont(15f));
			Util.drawStringToCenter(g, "KAMU KALAH", _LOST_OFFSET += 50);
			
			g.setColor(Color.YELLOW);
			g.setFont(Asset.JoystixFont.deriveFont(5f));
			Util.drawStringToCenter(g, "Skor terakhir : " + Integer.toString(SCORE), _LOST_OFFSET += 15);
			
			g.setColor(Color.RED);
			Util.drawStringToCenter(g, "Tekan <Enter> untuk kembali ke menu", _LOST_OFFSET += 15);
		}
		
		if (isPaused) {
			int _PAUSE_OFFSET = 0;
			g.setColor(Color.CYAN);
			g.setFont(Asset.LunchtimeFont.deriveFont(13f));
			Util.drawStringToCenter(g, "PERMAINAN BERHENTI", _PAUSE_OFFSET += 50);
			
			g.setFont(Asset.JoystixFont.deriveFont(7f));
			String RESUME_LABEL = "Lanjutkan";
			if (currentPauseOption == OPTION_RESUME) RESUME_LABEL = "> " + RESUME_LABEL + " <";
			g.setColor(currentPauseOption == OPTION_RESUME?new Color(255, 0, 0):new Color(150, 0, 0));
			Util.drawStringToCenter(g, RESUME_LABEL, _PAUSE_OFFSET += 25);
			
			String EXIT_LABEL = "Kembali";
			if (currentPauseOption == OPTION_EXIT) EXIT_LABEL = "> " + EXIT_LABEL + " <";
			g.setColor(currentPauseOption == OPTION_EXIT?new Color(255, 0, 0):new Color(150, 0, 0));
			Util.drawStringToCenter(g, EXIT_LABEL, _PAUSE_OFFSET += 15);
			
		}
		
		if (!Game.isFocused()) {
			int _FOCUS_OFFSET = 0;
			g.setColor(Color.ORANGE);
			g.setFont(Asset.LunchtimeFont.deriveFont(15f));
			Util.drawStringToCenter(g, "JEDA", _FOCUS_OFFSET += 50);
			
			g.setColor(Color.YELLOW);
			g.setFont(Asset.JoystixFont.deriveFont(5f));
			Util.drawStringToCenter(g, "Tekan layar untuk bermain kembali", _FOCUS_OFFSET += 20);
		}
		
	}
	
	private void selectCurrentOption() {
		if (currentPauseOption == OPTION_RESUME) {
			currentPauseOption = OPTION_RESUME;
			isPaused = false;
		} else if (currentPauseOption == OPTION_EXIT) {
			Asset.gameSong.stop();
			State.setState(State.MENU_STATE);
			State.resetGameState();
		}
	}
	
	@Override
	public void KeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_UP) {
			if (!keys.get("UP") && isPaused)
				optionUp();
			keys.put("UP", true);
		}
		else if (keyCode == KeyEvent.VK_LEFT) 
			keys.put("LEFT", true);
		else if (keyCode == KeyEvent.VK_DOWN) {
			if (!keys.get("DOWN") && isPaused)
				optionDown();
			keys.put("DOWN", true);
		}
		else if (keyCode == KeyEvent.VK_RIGHT)
			keys.put("RIGHT", true);
		else if (keyCode == KeyEvent.VK_SPACE)
			keys.put("SPACE", true);
		
		if (!keys.get("ENTER") && isPaused) {
			
		}
		
		if (keyCode == KeyEvent.VK_ESCAPE && !keys.get("ESCAPE")) {
			keys.put("ESCAPE", true);
			isPaused = true;
		} else if (keyCode == KeyEvent.VK_ENTER && !keys.get("ENTER")) {
			if (isLost) {				
				keys.put("ENTER", true);
				Asset.gameSong.stop();
				State.setState(State.MENU_STATE);
				State.resetGameState();
			} else if (isPaused) {
				selectCurrentOption();
			}
		}
		
	}
	
	private void optionUp() {
		this.currentPauseOption--;
		if (this.currentPauseOption < OPTION_RESUME) this.currentPauseOption = OPTION_RESUME;
		else Asset.optionSelectSound.play();
	}
	
	private void optionDown() {
		this.currentPauseOption++;
		if (this.currentPauseOption > OPTION_EXIT) this.currentPauseOption = OPTION_EXIT;
		else Asset.optionSelectSound.play();
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
		
		if (keyCode == KeyEvent.VK_ENTER && keys.get("ENTER") && isPaused) {
			keys.put("ENTER", false);
		}
		
		if (keyCode == KeyEvent.VK_ESCAPE && keys.get("ESCAPE"))
			keys.put("ESCAPE", false);
		else if (keyCode == KeyEvent.VK_ENTER && isLost && keys.get("ENTER")) {
			keys.put("ENTER", false);
		}
	}
	
}
