package com.soukied.game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Image {

	public static BufferedImage Load(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Image.class.getResource(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
}
