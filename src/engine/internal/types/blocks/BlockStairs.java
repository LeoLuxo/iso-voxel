package engine.internal.types.blocks;

import java.awt.Color;
import engine.core.Draw;
import engine.core.EngineCore;
import engine.internal.types.Texture;
import engine.internal.utility.MathUtil;

public class BlockStairs extends Block {
	
	public BlockStairs(Texture[] faces) {
		super(faces);
	}
	
	public BlockStairs(String face) {
		super(face);
	}
	
	public BlockStairs(String side, int animations, double speed) {
		super(side, animations, speed);
	}
	
	@Override
	public boolean getCull(int side) {
		switch(side) {
			case 0: return true;
			case 1: return true;
			case 2: return false;
			case 3: return false;
			case 4: return false;
			default: return false;
		}
	}
	
	@Override
	public double[][][] getLightChecks() {
		return new double[][][] {
			{{0, 1, 0}, {-.25, .49, .25}, {.25, .49, .25}},
			{{0, 0, -1}, {.25, -.25, -.49}, {-.25, -.25, -.49}},
			{{0, 0, 1}, {-.25, .25, .49}, {.25, .25, .49}, {.25, -.25, .49}, {-.25, -.25, .49}},
			{{-1, 0, 0}, {-.49, -.25, .25}, {-.49, .25, .25}, {-.49, -.25, -.25}},
			{{1, 0, 0}, {.49, -.25, .25}, {.49, .25, .25}, {.49, -.25, -.25}},
			{{0, 1, 0}, {-.25, -.01, -.25}, {.25, -.01, -.25}},
			{{0, 0, -1}, {.25, .25, .01}, {-.25, .25, .01}},
		};
	}
	
	@Override
	public void render(EngineCore core, Draw draw, double rot, boolean[] cull, float[][] lighting) {
		
		// rot *= 2;
		
		if (!MathUtil.cullSurround(cull)) draw.drawBlockTop(faces[SIDETOP], rot, lighting[5], 0, 0.5, 0.5, 0, 0.5, 1, 0.5);
		
		if (MathUtil.isInQuadrant(rot, false, true, true, false)) {
			draw.gPushTransform();
			draw.gPushClip();
			
			draw.transBlockSide(rot, 0, 0, -0.5);
			
			draw.gScale(draw.gGetUnit(), draw.gGetUnit());
			draw.gDirect().setClip(0, 0, 1, 1);
//			draw.gRotate(draw.getTT() / 100.0, draw.gGetUnit()/2, draw.gGetUnit()/4);
			draw.gScale(0.5, 0.5);
			
			draw.drawTextDropShadow(draw.getTT() + "", 0, 0, 2, true, Color.PINK, Color.BLACK, core.fontManager.getFont("cubeworld1"));
			
			draw.gPopTransform();
			draw.gPopClip();
		}
		
		if (!cull[SIDETOP]) draw.drawBlockTop(faces[SIDETOP], rot, lighting[SIDETOP], 0, 0, 0, 0, 0, 1, 0.5);
		
		if (!MathUtil.cullSurround(cull)) draw.drawBlockSide(faces[SIDEFRONT], true, rot, lighting[6], 0, 0, -0.5, 0, 0, 1, 0.5);
		if (!cull[SIDEFRONT]) draw.drawBlockSide(faces[SIDEFRONT], true, rot, lighting[SIDEFRONT], 0, 0.5, 0, 0, 0.5, 1, 0.5);
		
		if (!cull[SIDELEFT]) draw.drawBlockSide(faces[SIDELEFT], true, rot+Math.PI/2, lighting[SIDELEFT], 0, 0.5, 0, 0, 0.5, 1, 0.5);
		if (!cull[SIDELEFT]) draw.drawBlockSide(faces[SIDELEFT], true, rot+Math.PI/2, lighting[SIDELEFT], 0, 0, 0, 0, 0, 0.5, 0.5);
		
		if (!cull[SIDERIGHT]) draw.drawBlockSide(faces[SIDERIGHT], true, rot-Math.PI/2, lighting[SIDERIGHT], 0, 0.5, 0, 0, 0.5, 1, 0.5);
		if (!cull[SIDERIGHT]) draw.drawBlockSide(faces[SIDERIGHT], true, rot-Math.PI/2, lighting[SIDERIGHT], 0.5, 0, 0, 0.5, 0, 0.5, 0.5);
		
		if (!cull[SIDEBACK]) draw.drawBlockSide(faces[SIDEBACK], true, rot+Math.PI, lighting[SIDEBACK]);
		
		if (MathUtil.isInQuadrant(rot, true, false, false, true)) {
			draw.gPushTransform();
			draw.gPushClip();
			
			draw.transBlockSide(rot, 0, 0, -0.5);
			
			draw.gClipUnit(0, 0, 1, 0.5);
			draw.gRotateUnit(draw.getTT() / 100.0, 0.5, 0.25);
			draw.gScale(0.5, 0.5);
			
			draw.drawTextDropShadow(draw.getTT() + "", 0, 0, 2, false, Color.WHITE, Color.BLACK, core.fontManager.getFont("font1"));
			
			draw.gPopTransform();
			draw.gPopClip();
		}
	}
	
}
