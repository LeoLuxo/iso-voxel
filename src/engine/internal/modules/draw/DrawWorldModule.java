package engine.internal.modules.draw;

import java.awt.Color;
import engine.core.Draw;
import engine.core.EngineCore;
import engine.internal.modules.Module;
import engine.internal.types.Light;
import engine.internal.types.World;
import engine.internal.types.blocks.Block;
import engine.internal.utility.MathUtil;

public class DrawWorldModule extends Module {
	
	public DrawWorldModule(EngineCore core) {
		super(core);
	}

	public DrawWorldModule(EngineCore core, int priority) {
		super(core, priority);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void tick(EngineCore core, long tickTime) {
		core.world.lights.get(0).x = Math.cos(tickTime / 100.0) * 80.0 + 4;
		core.world.lights.get(0).z = Math.sin(tickTime / 100.0) * 80.0 + 4;
		core.world.lights.get(1).intensity = 0.6 + Math.sin(tickTime / 5.0) * 0.2;
	}
	
	@Override
	public void render(EngineCore core, Draw draw) {
		World w = core.world;
		
		double rot = core.screen.clientRotation;
		double zoom = core.screen.clientZoom;
		double camX = core.screen.clientCamX;
		double camY = core.screen.clientCamY;
		double camZ = core.screen.clientCamZ;
		
		draw.gDirect().setColor(new Color(w.ambientLight[0], w.ambientLight[1], w.ambientLight[2]));
		draw.gDirect().fillRect(0, 0, core.screen.getWidth(), core.screen.getHeight());
		
		draw.gPushTransform();
		
		draw.gTranslate(core.screen.getWidth()/2, core.screen.getHeight()/2);
		draw.gSetScale(zoom);
		draw.gSetUnit(16);
		draw.gSetAlpha(1f);
		
		int max = 16;
		int minX = (int) (Math.round(camX) - Math.floor(max/2));
		int maxX = (int) (Math.round(camX) + Math.ceil(max/2));
		int minY = 0;
		int maxY = max;
		int minZ = (int) (Math.round(camZ) - Math.floor(max/2));
		int maxZ = (int) (Math.round(camZ) + Math.ceil(max/2));
		
		double rotC = Math.cos(rot);
		double rotS = Math.sin(rot);
		
		for (int fy = minY; fy < maxY; fy++) {
			for (int fx = minX; fx < maxX; fx++) {
				for (int fz = minZ; fz < maxZ; fz++) {
					int x = fx;
					int y = fy;
					int z = fz;
					
					if (MathUtil.isInQuadrant(rot, true, false, false, true)) {
						z = (maxZ - 1) - z + minZ;
					} if (MathUtil.isInQuadrant(rot, false, false, true, true)) {
						x = (maxX - 1) - x + minX;
					}
					
					if (w.getBlock(x, y, z) != null) {
						double transX = ((x - camX) * rotC + (z - camZ) * rotS) * 16 * zoom;
						double transY = ((x - camX) * rotS - (z - camZ) * rotC - (y - camY)) * MathUtil.SIN45 * 16 * zoom;
						
						double offset = MathUtil.SIN45*1.5*zoom*16;
						if (transX > -core.screen.getWidth()/2 - offset && transX < core.screen.getWidth()/2 + offset && transY > -core.screen.getHeight()/2 - offset && transY < core.screen.getHeight()/2 + offset) {
							
							draw.gTranslate(transX, transY);
							
							boolean[] cull = {
								y+1 >= maxY ? false : w.getBlock(x, y+1, z) == null ? false : w.getBlock(x, y+1, z).getCull(Block.SIDETOP),
								z-1 < minZ ? false : w.getBlock(x, y, z-1) == null ? false : w.getBlock(x, y, z-1).getCull(Block.SIDEFRONT),
								z+1 >= maxZ ? false : w.getBlock(x, y, z+1) == null ? false : w.getBlock(x, y, z+1).getCull(Block.SIDEBACK),
								x-1 < minX ? false : w.getBlock(x-1, y, z) == null ? false : w.getBlock(x-1, y, z).getCull(Block.SIDELEFT),
								x+1 >= maxX ? false : w.getBlock(x+1, y, z) == null ? false : w.getBlock(x+1, y, z).getCull(Block.SIDERIGHT),
							};
							
							double[][][] lightChecks = w.getBlock(x, y, z).getLightChecks();
							float[][] lighting = new float[lightChecks.length][];
							
							for (int i = 0; i < lightChecks.length; i++) lighting[i] = w.ambientLight;
							
							for (Light l : w.lights) {
								for (int i = 0; i < lightChecks.length; i++) {
									float[] il = new float[] {0, 0, 0};
									
									for (int j = 1; j < lightChecks[i].length; j++) {
										double[] lc = lightChecks[i][j];
										double tx = lc[0] + x + 0.5;
										double ty = lc[1] + y + 0.5;
										double tz = lc[2] + z + 0.5;
										double nx = lightChecks[i][0][0];
										double ny = lightChecks[i][0][1];
										double nz = lightChecks[i][0][2];
										
										if ((i >= cull.length || !cull[i]) && !MathUtil.segIntersect(w, tx, ty, tz, l.x, l.y, l.z)) {
											double d = Math.sqrt(Math.pow((l.x - tx), 2) + Math.pow((l.y - ty), 2) + Math.pow((l.z - tz), 2));
											il = MathUtil.addColor(il, MathUtil.mulColor(MathUtil.mulColor(l.color, l.intensity), Math.max(((l.x - tx) * nx + (l.y - ty) * ny + (l.z - tz) * nz) / d * Math.max(1 - d / l.radius, 0), 0)));
										}
									}
									
									lighting[i] = MathUtil.addColor(lighting[i], MathUtil.mulColor(il, 1.0/lightChecks[i].length));
								}
							}
							
							try {
//								float alpha = 1f;
//								
//								if (x == minX) {
//									alpha -= (float) (camX - 0.5 - Math.floor(camX - 0.5));
//								} if (x == maxX-1) {
//									alpha -= 1 - (float) (camX - 0.5 - Math.floor(camX - 0.5));
//								} if (z == minZ) {
//									alpha -= (float) (camZ - 0.5 - Math.floor(camZ - 0.5));
//								} if (z == maxZ-1) {
//									alpha -= 1 - (float) (camZ - 0.5 - Math.floor(camZ - 0.5));
//								}
//								
//								draw.gSetAlpha(alpha);
								
								w.getBlock(x, y, z).render(core, draw, rot, cull, lighting);
								
							} catch (Exception e) {
								if (!e.getMessage().contains("Number of bands")) {
									throw e;
								}
							}
							
							draw.gTranslate(-transX, -transY);
							
						}
					}
				}
			}
		}
		
		draw.gDirect().setColor(Color.CYAN);
		draw.gDirect().drawRect(-2, -2, 4, 4);
		
		draw.gPopTransform();
		
	}
	
}
