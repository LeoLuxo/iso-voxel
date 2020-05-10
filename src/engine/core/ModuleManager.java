package engine.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;

import engine.internal.modules.Module;
import engine.io.Console;

public class ModuleManager {
	
	private EngineCore core;
	
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManager(EngineCore core) {
		this.core = core;
	}
	
	public void addModule(Module module) {
		modules.add(module);
		sort();
		Console.println("Module added:\n    \"" + module.getClass().getName() + "\"");
	}
	
	public Module getModule(Class<?> c) {
		for (Module m : modules) {
			if (c.isInstance(m)) {
				return m;
			}
		}
		return null;
	}
	
	public void enableModule(Class<?> c, boolean enable) {
		for (Module m : modules) {
			if (c.isInstance(m)) {
				m.enabled = enable;
			}
		}
	}
	
	public void init() {
		for (Module m : modules) {
			m.init();
			m.enabled = true;
			Console.println("Module initialized:\n    \"" + m.getClass().getName() + "\"");
		}
	}
	
	public void tick(long tickTime) {
		for (Module m : modules) {
			if (m.enabled) {
				m.tick(core, tickTime);
			}
		}
	}
	
	public void render(Draw draw) {
		for (Module m : modules) {
			if (m.enabled) {
				m.render(core, draw);
			}
		}
	}
	
	private void sort() {
		modules.sort(new Comparator<Module>() {
		    @Override
		    public int compare(Module m1, Module m2) {
		        return m2.priority - m1.priority;
		    }
		});
	}
	
}
