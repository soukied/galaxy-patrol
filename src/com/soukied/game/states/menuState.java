package com.soukied.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.soukied.game.Asset;
import com.soukied.game.Game;
import com.soukied.game.State;
import com.soukied.game.Util;

public class menuState extends State {

	
	private boolean
	KEY_UP = false,
	KEY_DOWN = false,
	KEY_ENTER = false;
	
	private final int 
	OPTION_START = 0,
	OPTION_HELP = 1,
	OPTION_ABOUT = 2, 
	OPTION_EXIT = 3;
	
	private int currentOption = 0;
	
	// create a function to point which color is to use for the options
	private Color getColorOptions(int option) {
		Color SELECTED = new Color(0, 255, 0);
		Color UNSELECTED = new Color(0, 140, 0);
		return currentOption == option ? SELECTED : UNSELECTED;
	}
	
	private void optionEnter() {
		if (currentOption == OPTION_START) {
			State.setState(State.GAME_STATE);
			Asset.menuSong.stop();
		}
		else if (currentOption == OPTION_HELP) {
			State.resetHelpState();
			State.setState(State.HELP_STATE);
		}
		else if (currentOption == OPTION_ABOUT) {
			State.resetAboutState();
			State.setState(State.ABOUT_STATE);
		}
		else if (currentOption == OPTION_EXIT)
			System.exit(0);
	}
	
	private void optionUp() {
		currentOption--;
		if (currentOption < 0) currentOption = 0;
		else Asset.optionSelectSound.play();
	}
	
	private void optionDown() {
		currentOption++;
		if (currentOption > OPTION_EXIT) currentOption = OPTION_EXIT;
		else Asset.optionSelectSound.play();
	}
	
	// init
	@Override
	public void init() {
		Asset.menuSong.setLoop();
		Asset.menuSong.play();
	}
	
	// update
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		for(int x = 0 ; x < Game.WIDTH; x++) {
			for (int y = 0; y < Game.HEIGHT; y++) {
				int grayScale = Util.getRandom(256);
				g.setColor(new Color(grayScale, grayScale, grayScale));
				g.fillRect(x, y, 1, 1);
			}
		}
		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		// offset for options
		int offsetY = 0;
		
		// menu title
		g.setColor(Color.RED);
		g.setFont(Asset.LunchtimeFont.deriveFont(15f));
		Util.drawStringToCenter(g, "Galaxy Patrol", offsetY += 40);
	
		// menu options
		g.setFont(Asset.JoystixFont.deriveFont(8f));
		
		String TITLE_LABEL = "Mulai";
		if (currentOption == OPTION_START) TITLE_LABEL = "> " + TITLE_LABEL + " <";
		g.setColor(getColorOptions(OPTION_START));
		Util.drawStringToCenter(g, TITLE_LABEL, offsetY += 20);
		
		String HELP_LABEL = "Bantuan";
		if (currentOption == OPTION_HELP) HELP_LABEL = "> " + HELP_LABEL + " <";

		g.setColor(getColorOptions(OPTION_HELP));
		Util.drawStringToCenter(g, HELP_LABEL, offsetY += 20);
		
		String ABOUT_LABEL = "Tentang";
		if (currentOption == OPTION_ABOUT) ABOUT_LABEL = "> " + ABOUT_LABEL + " <";
		g.setColor(getColorOptions(OPTION_ABOUT));
		Util.drawStringToCenter(g, ABOUT_LABEL, offsetY += 20);

		String EXIT_LABEL = "Keluar";
		if (currentOption == OPTION_EXIT) EXIT_LABEL = "> " + EXIT_LABEL + " <";
		g.setColor(getColorOptions(OPTION_EXIT));
		Util.drawStringToCenter(g, EXIT_LABEL, offsetY += 20);
		
		// render footer
		g.setColor(Color.WHITE);
		g.setFont(Asset.JoystixFont.deriveFont(2.5f));
		Util.drawString(g, "Made by Adhya Adam Sulthan as an entry for Campus Exhibition", 3, Game.HEIGHT - 8);
		
		// render version label
		g.setColor(Color.YELLOW);
		g.setFont(Asset.JoystixFont.deriveFont(3f));
		Util.drawString(g, "Versi 0.0.1 alpha", 2, 0);
	}
	
	@Override
	public void KeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_UP && !KEY_UP) {
			KEY_UP = true;
			optionUp();
		}
		else if (keyCode == KeyEvent.VK_DOWN && !KEY_DOWN) {
			KEY_DOWN = true;
			optionDown();
		}
		else if (keyCode == KeyEvent.VK_ENTER && !KEY_ENTER) {
			KEY_ENTER = true;
			optionEnter();
		}
	}
	
	@Override
	public void KeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_UP)
			KEY_UP = false;
		else if (keyCode == KeyEvent.VK_DOWN)
			KEY_DOWN = false;
		else if (keyCode == KeyEvent.VK_ENTER)
			KEY_ENTER = false;
	}
}
