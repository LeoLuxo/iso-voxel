package engine.internal.types.blocks;

import java.awt.AlphaComposite;

import engine.core.Draw;
import engine.core.EngineCore;
import engine.internal.types.Texture;
import engine.internal.utility.MathUtil;

public class BlockWater extends Block {
	
	public BlockWater(Texture[] faces) {
		super(faces);
	}
	
	public BlockWater(String face) {
		super(face);
	}
	
	public BlockWater(String side, int animations, double speed) {
		super(side, animations, speed);
	}
	
	@Override
	public boolean getCull(int side) {
		return false;
	}
	
	@Override
	public boolean isTranslucent() {
		return true;
	}
	
	@Override
	public double[][][] getLightChecks() {
		return new double[][][] {
			{{0, 1, 0}, {-.25, -.31, .25}, {.25, -.31, .25}, {.25, -.31, -.25}, {-.25, -.31, -.25}},
			{{0, 0, -1}, {-.25, .1, -.49}, {.25, .1, -.49}},
			{{0, 0, 1}, {-.25, .1, .49}, {.25, .1, .49}},
			{{-1, 0, 0}, {-.49, .1, .25}, {-.49, .1, -.25}},
			{{1, 0, 0}, {.49, .1, .25}, {.49, .1, -.25}},
		};
	}
	
	@Override
	public void render(EngineCore core, Draw draw, double rot, boolean[] cull, float[][] lighting) {
		draw.gPushComposite();
		
		draw.gDirect().setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		
		if (!MathUtil.cullSurround(cull)) draw.drawBlockTop(faces[0], rot, lighting[SIDETOP], 0, 0.8, 0);
		if (!cull[SIDEFRONT]) draw.drawBlockSide(faces[1], true, rot, lighting[SIDEFRONT], 0, 0.8, 0, 0, 0, 1, 0.2);
		if (!cull[SIDEBACK]) draw.drawBlockSide(faces[2], true, rot+Math.PI, lighting[SIDEBACK], 0, 0.8, 0, 0, 0, 1, 0.2);
		if (!cull[SIDELEFT]) draw.drawBlockSide(faces[3], true, rot+Math.PI/2, lighting[SIDELEFT], 0, 0.8, 0, 0, 0, 1, 0.2);
		if (!cull[SIDERIGHT]) draw.drawBlockSide(faces[4], true, rot-Math.PI/2, lighting[SIDERIGHT], 0, 0.8, 0, 0, 0, 1, 0.2);
		
		draw.gPopComposite();
	}
	
}
