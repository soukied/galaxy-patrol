package soukied.game.test;

import java.awt.image.BufferedImage;

public class Sprite {

	private BufferedImage image;
	
	public Sprite(BufferedImage image) {
		this.image = image;
	}
	
	public Sprite(String path) {
		this.image = Image.Load(path);
	}
	
	public Sprite getSub(int x, int y, int width, int height) {
		return new Sprite(image.getSubimage(x, y, width, height));
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
}
