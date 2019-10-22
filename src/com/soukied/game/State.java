package com.soukied.game;

import java.awt.Graphics2D;

import com.soukied.game.states.aboutState;
import com.soukied.game.states.gameState;
import com.soukied.game.states.helpState;
import com.soukied.game.states.menuState;

public abstract class State {

	public static State MENU_STATE = new menuState();
	public static State ABOUT_STATE = new aboutState();
	public static State HELP_STATE = new helpState();
	public static State GAME_STATE = new gameState();
	
	public static void resetGameState() {
		GAME_STATE = new gameState();
	}
	
	public static void resetMenuState() {
		MENU_STATE = new menuState();
	}
	
	public static void resetAboutState() {
		ABOUT_STATE = new aboutState();
	}
	
	public static void resetHelpState() {
		HELP_STATE = new helpState();
	}
	
	// ============================
	
	private static State currentState = null;
		
	public static void setState(State newState) {
		if (newState == currentState) return;
		if (currentState == null) {
			currentState = newState;
		} else {
			currentState = newState;
			currentState.init();
		}
	}
	
	public static State getState() {
		return currentState;
	}
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render(Graphics2D g);
	
	public void KeyDown(int keyCode) {
		
	}
	
	public void KeyUp(int keyCode) {
		
	}
	
}