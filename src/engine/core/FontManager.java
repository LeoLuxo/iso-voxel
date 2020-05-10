package engine.core;

import java.awt.Font;
import java.util.HashMap;

import engine.io.Console;
import engine.io.FileReader;

public class FontManager {
	
	@SuppressWarnings("unused")
	private EngineCore core;
	
	private HashMap<String, Font> fonts = new HashMap<String, Font>();
	
	public FontManager(EngineCore core) {
		this.core = core;
	}
	
	public void addFont(String name) {
		fonts.put(name, FileReader.readFont("/assets/fonts/" + name + ".ttf").deriveFont(Font.PLAIN, 12f));
		Console.println("Font added:\n    \"" + name + "\"");
	}
	
	public Font getFont(String name) {
		return fonts.get(name);
	}
	
	public Font getFont(String name, float size, int style) {
		return fonts.get(name).deriveFont(style, size);
	}

}
