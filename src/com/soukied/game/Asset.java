package com.soukied.game;

import java.awt.Font;

public class Asset {
	public static final Font JoystixFont = Util.getFont("/fonts/joystix.ttf");
	public static final Font LunchtimeFont = Util.getFont("/fonts/lunchtime.ttf");
	public static final String aboutText = Util.readFile("/splashes/about.txt");
	public static final String helpText = Util.readFile("/splashes/help.txt");
}
