package engine.io;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import engine.io.Console;

public class FileReader {
	
	public static boolean packageExists(String path) {
		return FileReader.class.getResource(path) != null;
	}
	
	public static BufferedImage readImage(String path) {
		
		path = "/assets/textures/" + path + ".png";
		
		try {
			
			InputStream in = FileReader.class.getResourceAsStream(path); 
			return ImageIO.read(in);
			
		} catch (Exception e) {
			
			Console.errprintln("Couldn't load image \"" + path + "\" !");
			
			if (!path.equalsIgnoreCase("/assets/textures/error.png")) {
				try {
					
					return readImage("error");
					
				} catch (Exception e2) {}
			}
			
			return null;
			
		}
		
	}
	
	public static BufferedImage readImage(String path, String altPath) {
		
		path = "/assets/textures/" + path + ".png";
		
		try {
			
			InputStream in = FileReader.class.getResourceAsStream(path); 
			return ImageIO.read(in);
			
		} catch (Exception e) {
			
			try {
				
				return readImage(altPath);
				
			} catch (Exception e2) {}
			
			return null;
			
		}
		
	}
	
	public static String[] readText(String path) {
		
		try {
			
			InputStream in = FileReader.class.getResourceAsStream(path); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = "";
			String fullFile = "";
			
			while ((line = reader.readLine()) != null) {
				
				fullFile = fullFile + "§" + line;
				
			}
			
			in.close();
			
			return fullFile.split("§");
			
		} catch (Exception e) {
			
			Console.errprintln("Couldn't load text file \"" + path + "\" !");
			Console.err(e);
			return null;
			
		}
		
	}
	
	public static String readTextSingleLine(String path) {
		
		try {
			
			InputStream in = FileReader.class.getResourceAsStream(path); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = "";
			String fullFile = "";
			
			while ((line = reader.readLine()) != null) {
				
				fullFile = fullFile + line;
				
			}
			
			in.close();
			
			return fullFile;
			
		} catch (Exception e) {
			
			Console.errprintln("Couldn't load text file \"" + path + "\" !");
			Console.err(e);
			return null;
			
		}
		
	}
	
	public static Font readFont(String path) {
	
		try {
			
			InputStream in = FileReader.class.getResourceAsStream(path);
			
			Font f = Font.createFont(Font.TRUETYPE_FONT, in);
			in.close();
			
			return f;
			
		} catch (Exception e) {
			
			Console.errprintln("Couldn't load font file \"" + path + "\" !");
			Console.err(e);
			return null;
			
		}
		
	}
	
	
}
