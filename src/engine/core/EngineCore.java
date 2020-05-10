package engine.core;

import engine.internal.modules.draw.*;
import engine.internal.types.Input;
import engine.internal.types.Light;
import engine.internal.types.World;
import engine.io.Config;
import engine.io.Console;

public class EngineCore {
	
	public Screen screen;
	public ModuleManager moduleManager;
	public InputManager inputManager;
	public FontManager fontManager;
	public CoreLoop coreLoop;
	
	public long tickTime = 0;
	
	public int fpsCounter = 0;
	public int tpsCounter = 0;
	
	public World world;
	
	public void startup() {
		
		Console.println("Starting up engine...");
		
		initCore();
		setupModules();
		initScreen();
		initInputs();
		initFonts();
		startMainLoop();
		setupWorld();
		initModules();
		generateWorld();
		
	}

	private void initCore() {
		screen = new Screen(this);
		moduleManager = new ModuleManager(this);
		inputManager = new InputManager(this);
		fontManager = new FontManager(this);
		coreLoop = new CoreLoop(this);
		
		Console.println("Successfully initialized core");
	}
	
	private void setupModules() {
		moduleManager.addModule(new DrawDebugModule(this, -10));
		moduleManager.addModule(new DrawWorldModule(this, 0));
		
		Console.println("Successfully set up modules");
	}
	
	private void initScreen() {
		screen.init();
		
		Console.println("Successfully initialized main screen");
	}
	
	private void initInputs() {
		inputManager.addInput(new Input("W", true) {public void run(EngineCore core) {core.screen.clientCamZ += Config.CAMSPEED;}});
		inputManager.addInput(new Input("A", true) {public void run(EngineCore core) {core.screen.clientCamX -= Config.CAMSPEED;}});
		inputManager.addInput(new Input("S", true) {public void run(EngineCore core) {core.screen.clientCamZ -= Config.CAMSPEED;}});
		inputManager.addInput(new Input("D", true) {public void run(EngineCore core) {core.screen.clientCamX += Config.CAMSPEED;}});
		inputManager.addInput(new Input("Q", true) {public void run(EngineCore core) {core.screen.clientCamY -= Config.CAMSPEED;}});
		inputManager.addInput(new Input("E", true) {public void run(EngineCore core) {core.screen.clientCamY += Config.CAMSPEED;}});
		
		inputManager.addInput(new Input("1", true) {public void run(EngineCore core) {core.world.ambientLight = new float[] {0.3f, 0.3f, 0.3f}; core.world.lights.set(0, new Light(new float[] {1f, 1f, 0.8f}, 1, 4, 100, 4, Double.POSITIVE_INFINITY));}});
		inputManager.addInput(new Input("2", true) {public void run(EngineCore core) {core.world.ambientLight = new float[] {0.05f, 0.05f, 0.1f}; core.world.lights.set(0, new Light(new float[] {0f, 0f, 0f}, 0, 0, 0, 0, 0));}});
		
		Console.println("Successfully initialized inputs");
	}
	
	private void initFonts() {
		fontManager.addFont("font1");
		fontManager.addFont("font2");
		
		Console.println("Successfully initialized fonts");
	}
	
	private void startMainLoop() {
		coreLoop.start();
		
		Console.println("Successfully started main loop");
	}
	
	private void setupWorld() {
		world = new World();
		
		Console.println("Successfully set up client world");
	}
	
	private void initModules() {
		moduleManager.init();
		
		Console.println("Successfully initialized modules");
	}
	
	private void generateWorld() {
		world.generate();
	}
	
	public void tick() {
		
		inputManager.tick();
		moduleManager.tick(tickTime);
		
		tickTime++;
		
	}
	
	public void tickRender() {
		
		screen.tickRender();
		
	}
	
	public void render(Draw draw) {
		
		draw.setTT(tickTime);
		
		moduleManager.render(draw);
		
	}
	
}
