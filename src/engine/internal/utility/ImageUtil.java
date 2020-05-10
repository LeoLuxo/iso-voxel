package engine.internal.utility;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import engine.internal.types.Texture;
import engine.io.FileReader;

public class ImageUtil {
	
	public static BufferedImage toCompatible(BufferedImage src) {
		BufferedImage dst = null;
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		dst = gc.createCompatibleImage(src.getWidth(), src.getHeight(), src.getTransparency());
		Graphics2D g = dst.createGraphics();
		g.drawImage(src, 0, 0, null);
		g.dispose();
		return dst;
	}
	
	public static Texture[] findBlock(String path, String name) {
		Texture[] faces = new Texture[5];
		if (FileReader.packageExists("/assets/textures/" + path + "/")) {
			faces[0] = new Texture(path + "/" + name + "_top", path + "/" + name);
			faces[1] = new Texture(path + "/" + name + "_front", path + "/" + name + "_side");
			faces[2] = new Texture(path + "/" + name + "_back", path + "/" + name + "_side");
			faces[3] = new Texture(path + "/" + name + "_left", path + "/" + name + "_side");
			faces[4] = new Texture(path + "/" + name + "_right", path + "/" + name + "_side");
		} else {
			faces[0] = new Texture(path);
			faces[1] = new Texture(path);
			faces[2] = new Texture(path);
			faces[3] = new Texture(path);
			faces[4] = new Texture(path);
		}
		return faces;
	}
	
	public static Texture[] findBlock(String path, String name, int animations, double speed) {
		Texture[] faces = new Texture[5];
		if (FileReader.packageExists("/assets/textures/" + path + "/")) {
			faces[0] = new Texture(path + "/" + name + "_top", path + "/" + name, animations, speed);
			faces[1] = new Texture(path + "/" + name + "_front", path + "/" + name + "_side", animations, speed);
			faces[2] = new Texture(path + "/" + name + "_back", path + "/" + name + "_side", animations, speed);
			faces[3] = new Texture(path + "/" + name + "_left", path + "/" + name + "_side", animations, speed);
			faces[4] = new Texture(path + "/" + name + "_right", path + "/" + name + "_side", animations, speed);
		} else {
			faces[0] = new Texture(path, animations, speed);
			faces[1] = new Texture(path, animations, speed);
			faces[2] = new Texture(path, animations, speed);
			faces[3] = new Texture(path, animations, speed);
			faces[4] = new Texture(path, animations, speed);
		}
		return faces;
	}
	
}
