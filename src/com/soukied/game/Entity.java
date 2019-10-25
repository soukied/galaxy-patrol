package com.soukied.game;

import java.awt.image.BufferedImage;

public class Entity {

	private int x = 0, y = 0;
	private int width = 1;
	private int height = 1;
	private BufferedImage sprite = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	
	public Entity(int x, int y) {
		setCord(x, y);
	}
	
	public Entity(BufferedImage sprite, int x, int y) {
		this(x, y);
		embedImage(sprite);
	}
	
	public Entity(BufferedImage sprite) {
		embedImage(sprite);
	}
	
	public void embedImage(BufferedImage sprite) {
		this.sprite = sprite;
		this.width = this.sprite.getWidth();
		this.height = this.sprite.getHeight();
	}
	
	public void setSize(int size) {
		this.width = size;
		this.height = size;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void addSize(int size) {
		this.width += size;
		this.height += size;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isCollide(Entity entity) {
		boolean upSide = this.getY() < entity.getY() + this.getHeight();
		boolean downSide = this.getY() + this.getHeight() > entity.getY();
		boolean rightSide = this.getX() + this.getWidth() > entity.getX();
		boolean leftSide = this.getX() < entity.getX() + entity.getHeight();
		return upSide && downSide && rightSide && leftSide;
	}
	
	public void moveX(int x) {
		this.x += x;
	}
	
	public void moveY(int y) {
		this.y += y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setCord(int x, int y) {
		this.x = x;
		this.y = y;
	} 
}
