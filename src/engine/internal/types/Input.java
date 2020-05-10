package engine.internal.types;

import engine.core.EngineCore;

public abstract class Input {
	
	private String input;
	
	private boolean pressed = false;
	private boolean holdable = false;
	
	public Input(String input, boolean holdable) {
		this.setInput(input);
		this.setHoldable(holdable);
	}
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public boolean isHoldable() {
		return holdable;
	}
	
	public void setHoldable(boolean holdable) {
		this.holdable = holdable;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public abstract void run(EngineCore core);
	
}
