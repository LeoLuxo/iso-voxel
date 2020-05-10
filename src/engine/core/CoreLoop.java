package engine.core;

import engine.core.EngineCore;
import engine.io.Config;

public class CoreLoop {
	
	private EngineCore core;
	
	public Thread tickThread;
	
	private boolean run = false;
	
	public int currentTPS = 0;
	public int currentFPS = 0;
	
	private int expectedTPS;
	
	public int fpsCounter = 0;
	public int tpsCounter = 0;
	
	public long initialTime;
	public double time;
	public double delta;
	public long secTimer;
	
	public CoreLoop(EngineCore core) {
		
		this.expectedTPS = Config.TICKS;
		this.core = core;
		
	}
	
	public void stop() {
		
		run = false;
		
		if (tickThread.isAlive()) {
			tickThread.interrupt();
		}
		
	}
	
	public void reset() {
		
		initialTime = System.nanoTime();
		time = 1000000000 / expectedTPS;
		delta = 0;
		secTimer = System.currentTimeMillis();
		
	}
	
	public void start() {
		
		tickThread = new Thread(new Runnable() {public void run() {
			
			reset();
			
			while (run) {
				
				long currentTime = System.nanoTime();
				delta += (currentTime - initialTime) / time;
				initialTime = currentTime;
				
				while (delta >= 1) {
					
					core.tick();
					
					if (Config.LIMITFPS && delta < 2) {
						core.tickRender();
						fpsCounter++;
					}
					
					tpsCounter++;
					delta -= 1;
					
				}
				
				if (!Config.LIMITFPS) {
					core.tickRender();
					fpsCounter++;
				}
				
				if (System.currentTimeMillis() - secTimer > 1000) {
					
					currentFPS = fpsCounter;
					currentTPS = tpsCounter;
					
					fpsCounter = 0;
					tpsCounter = 0;
					
					secTimer += 1000;
					
				}
				
			}
			
		}}, "Ticking thread");
		
		run = true;
		tickThread.start();
		
	}
	
}
