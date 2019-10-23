package com.soukied.game;

import java.awt.Font;

public class Asset {
	
	private static boolean isLoaded = false;
	// fonts
	public static Font JoystixFont = null;
	public static Font LunchtimeFont = null;
	// splashes
	public static String aboutText = null;
	public static String helpText = null;
	// audios
	public static Audio menuSong = null;
	public static Audio gameSong = null;
	public static Audio typeSound = null;
	public static Audio playerShootSound = null;
	public static Audio optionSelectSound = null;
	public static Audio collideSound = null;

	
	public static void Init() {
		if (isLoaded) return;
		// init fonts
		JoystixFont = Util.getFont("/fonts/joystix.ttf");
		LunchtimeFont = Util.getFont("/fonts/lunchtime.ttf");
		// init splashes
		aboutText = Util.readFile("/splashes/about.txt");
		helpText = Util.readFile("/splashes/help.txt");
		// init audios
		menuSong = new Audio("/audio/menu.wav");
		gameSong = new Audio("/audio/game.wav");
		typeSound = new Audio("/audio/type.wav");
		playerShootSound = new Audio("/audio/player_shoot.wav", true);
		optionSelectSound = new Audio("/audio/option_select.wav", true);
		collideSound = new Audio("/audio/collide.wav", true);
		
		isLoaded = true;
	}
}
