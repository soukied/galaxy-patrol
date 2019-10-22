package com.soukied.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.soukied.game.Asset;
import com.soukied.game.Game;
import com.soukied.game.State;
import com.soukied.game.Util;

public class helpState extends State {

	private boolean KEY_ENTER = false;
	private String out = "";
	
	@Override
	public void init() {
	
		
	}

	int tick = 0;
	int charIndex = 0;
	@Override
	public void update() {
		if (tick % (int)(Game.FPS/50) == 0) {
			if (charIndex < Asset.helpText.length()) 
				out += Asset.helpText.charAt(charIndex++);
		}
		tick++;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.setColor(new Color(0, 255, 0));
		g.setFont(Asset.JoystixFont.deriveFont(4f));
		for (int i = 0 ; i < out.split("\n").length; i++) {
			Util.drawString(g, out.split("\n")[i], 2, g.getFontMetrics().getHeight() * i);
		}
		
		g.setColor(Color.RED);
		Util.drawString(g, "Tekan <Enter> untuk kembali ke menu", 2, Game.HEIGHT - g.getFontMetrics().getHeight()-2);
	}
	
	@Override
	public void KeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_ENTER && !KEY_ENTER) {
			KEY_ENTER = true;
			State.setState(State.MENU_STATE);
		}
	}
	
	@Override
	public void KeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_ENTER && KEY_ENTER)
			KEY_ENTER = false;
	}

}
