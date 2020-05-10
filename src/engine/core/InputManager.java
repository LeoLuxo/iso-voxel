package engine.core;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import engine.internal.types.Input;
import engine.io.Config;

public class InputManager implements KeyListener, MouseMotionListener {
	
	private EngineCore core;
	
	private ArrayList<Input> inputs = new ArrayList<>();
	
	private double mouseMovementX = 0;
	
	public InputManager(EngineCore core) {
		this.core = core;
		
		if (Config.MOUSECONTROL) {
			resetMouse();
		}
	}
	
	public void resetMouse() {
		try {
			
			Robot r = new Robot();
			r.mouseMove(core.screen.getLocationOnScreen().x + core.screen.getWidth()/2, core.screen.getLocationOnScreen().y + core.screen.getHeight()/2);
			
		} catch (Exception ex) {}
	}
	
	public void addInput(Input input) {
		inputs.add(input);
	}
	
	public double getMouseMovementX() {
		double m = mouseMovementX;
		mouseMovementX = 0;
		return m;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		for (Input i : inputs) {
			if (i.getInput().equalsIgnoreCase(KeyEvent.getKeyText(e.getKeyCode()))) {
				if (i.isHoldable()) {
					i.setPressed(true);
				} else {
					i.run(core);
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		for (Input i : inputs) {
			if (i.getInput().equalsIgnoreCase(KeyEvent.getKeyText(e.getKeyCode())) && i.isHoldable()) {
				i.setPressed(false);
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseHasMoved(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseHasMoved(e);
	}
	
	public void mouseHasMoved(MouseEvent e) {
		if (Config.MOUSECONTROL) {
			mouseMovementX += (double) (e.getX() - core.screen.getWidth()/2) * Config.MOUSESENSITIVITY;
			resetMouse();
		}
	}
	
	public void tick() {
		for (Input i : inputs) {
			if (i.isHoldable() && i.isPressed()) {
				i.run(core);
			}
		}
		
		core.screen.clientRotation -= getMouseMovementX();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
}
