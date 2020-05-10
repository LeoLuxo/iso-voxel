package engine.internal.modules.draw;

import java.awt.Color;
import java.awt.Font;
import engine.core.Draw;
import engine.core.EngineCore;
import engine.internal.modules.Module;

public class DrawDebugModule extends Module {
	
	private Color debugBGColor;
	
	public DrawDebugModule(EngineCore core) {
		super(core);
	}
	
	public DrawDebugModule(EngineCore core, int priority) {
		super(core, priority);
	}
	
	@Override
	public void init() {
		debugBGColor = new Color(0, 0, 0, 128);
	}
	
	@Override
	public void render(EngineCore core, Draw draw) {
		draw.drawTextBackground("TPS: " + core.coreLoop.currentTPS, 0, 0, Color.WHITE, debugBGColor, 3);
		draw.drawTextBackground("FPS: " + core.coreLoop.currentFPS, 50, 0, Color.WHITE, debugBGColor, 3);
		draw.drawTextDropShadow("This is a long", 100, 20, 5, true, Color.RED, Color.BLACK, core.fontManager.getFont("font2", 50, Font.ITALIC));
		draw.drawTextDropShadow("MESSSAAAAAAAAAAAAAAAAAAGE", 100, 70, 5, true, Color.CYAN, Color.BLACK, core.fontManager.getFont("font1", 50, Font.BOLD));
	}
	
	@Override
	public void tick(EngineCore core, long tickTime) {}
	
}
