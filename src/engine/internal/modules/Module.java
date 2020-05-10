package engine.internal.modules;

import engine.core.Draw;
import engine.core.EngineCore;

public abstract class Module {
	
	protected EngineCore core;

	public boolean enabled = false;
	public int priority = 0;
	
	public Module(EngineCore core) {
		this.core = core;
	}
	
	public Module(EngineCore core, int priority) {
		this.core = core;
		this.priority = priority;
	}
	
	public abstract void init();
	public abstract void tick(EngineCore core, long tickTime);
	public abstract void render(EngineCore core, Draw draw);
	
}
