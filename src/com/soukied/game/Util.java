package com.soukied.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class Util {

	public static void gamePrint(String text) {
		System.out.println("[GAME] " + text);
	}
	
	public static int getHeight(Graphics g) {
		return g.getFontMetrics().getHeight();
	}
	
	public static void drawString(Graphics g, String text, int x , int y) {
		g.drawString(text, x, g.getFontMetrics().getHeight() + y);
	}
	
	public static void drawStringToCenter(Graphics g, String text, int y) {
		g.drawString(text, (Game.WIDTH/2) - (getWidth(g, text)/2), y);
	}
	
	public static void drawStringToRight(Graphics g, String text, int y) {
		g.drawString(text, Game.WIDTH - getWidth(g, text) - 2, g.getFontMetrics().getHeight() + y);
	}
	
	public static int getWidth(Graphics g, String text) {
		return g.getFontMetrics().stringWidth(text);
	}
	
	public static int getRandom(int number) {
		return (int) (Math.random() * number);
	}
	
	public static Color getRandomColor() {
		return new Color(getRandom(256), getRandom(256), getRandom(256));
	}
	
	public static Font getFont(String fontFile) {
		InputStream fontStream = Util.class.getResourceAsStream(fontFile);
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontStream);			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return font;
	}
	
	public static String readFile(String fileName) {
		String out = "";
		InputStream file = Util.class.getResourceAsStream(fileName);
		try {
			byte data[] = new byte[file.available()];
			file.read(data);
			file.close();
			out = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
}
