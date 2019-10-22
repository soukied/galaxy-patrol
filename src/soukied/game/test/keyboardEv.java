package soukied.game.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyboardEv implements KeyListener {

	@Override
	public void keyPressed(KeyEvent ev) {
		State.getState().KeyDown(ev.getKeyCode());
		//int keyCode = ev.getKeyCode();		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		State.getState().KeyUp(event.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
