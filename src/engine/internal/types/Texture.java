package engine.internal.types;

import java.awt.image.BufferedImage;

import engine.internal.utility.ImageUtil;
import engine.io.FileReader;

public class Texture {
	
	private BufferedImage[] textures;
	private double speed = 1;
	
	public Texture(String path) {
		textures = new BufferedImage[1];
		textures[0] = ImageUtil.toCompatible(FileReader.readImage(path));
	}
	
	public Texture(String path, String altPath) {
		textures = new BufferedImage[1];
		textures[0] = ImageUtil.toCompatible(FileReader.readImage(path, altPath));
	}
	
	public Texture(String path, int animations, double speed) {
		this.speed = speed;
		BufferedImage img = ImageUtil.toCompatible(FileReader.readImage(path));
		if (animations <= 0) {
			animations = img.getHeight() / img.getWidth();
		}
		textures = new BufferedImage[animations];
		for (int i = 0; i < animations; i++) {
			textures[i] = img.getSubimage(0, img.getHeight() / animations * i, img.getWidth(), img.getHeight() / animations);
		}
	}
	
	public Texture(String path, String altPath, int animations, double speed) {
		this.speed = speed;
		BufferedImage img = ImageUtil.toCompatible(FileReader.readImage(path, altPath));
		if (animations <= 0) {
			animations = img.getHeight() / img.getWidth();
		}
		textures = new BufferedImage[animations];
		for (int i = 0; i < animations; i++) {
			textures[i] = img.getSubimage(0, img.getHeight() / animations * i, img.getWidth(), img.getHeight() / animations);
		}
	}
	
	public BufferedImage getImg(long tickTime) {
		return textures[(int) (tickTime * speed) % textures.length];
	}
	
}
