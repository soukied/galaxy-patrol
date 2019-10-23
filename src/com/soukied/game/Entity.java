package com.soukied.game;

public class Entity {

	private int x, y;
	private int width = 10;
	private int height = 10;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
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
}
