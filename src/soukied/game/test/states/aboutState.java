package soukied.game.test.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import soukied.game.test.Asset;
import soukied.game.test.Game;
import soukied.game.test.State;
import soukied.game.test.Util;

public class aboutState extends State {

	boolean ENTER_PRESSED = true;
	String print = "";
	
	public void init() {
		
		
	}

	int tick = 0;
	int charIndex = 0;
	public void update() {
		if(tick % (int)(Game.FPS/20) == 0) {
			
			if (charIndex < Asset.aboutText.length()) {
				print += Asset.aboutText.charAt(charIndex++);
			}
		}
		
			tick++;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.setColor(Color.WHITE);
		g.setFont(Asset.JoystixFont.deriveFont(4f));
		String[] aboutTextList = print.split("\n");
		int fontHeight = g.getFontMetrics().getHeight();
		for (int i = 0; i < aboutTextList.length; i++) {
			Util.drawString(g, aboutTextList[i], 2, fontHeight * i);
		}
		
		g.setColor(Color.YELLOW);
		Util.drawString(g, "Tekan <ENTER> untuk kembali ke menu", 2, Game.HEIGHT-fontHeight-2);
	}

	@Override
	public void KeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_ENTER && !ENTER_PRESSED) {
			ENTER_PRESSED = true;
			State.setState(MENU_STATE);
		}
	}
	
	@Override
	public void KeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_ENTER && ENTER_PRESSED)
			ENTER_PRESSED = false;
	}
	
}
