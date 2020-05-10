package engine.internal.types.blocks;

import engine.core.Draw;
import engine.core.EngineCore;
import engine.internal.types.Texture;

public class Block {
	
	public Texture[] faces;
	
	public static final Block ERRORBLOCK = new Block("error") {
		@Override
		public boolean getCull(int side) {
			return false;
		}
	};
	
	public static final int SIDETOP = 0;
	public static final int SIDEFRONT = 1;
	public static final int SIDEBACK = 2;
	public static final int SIDELEFT = 3;
	public static final int SIDERIGHT = 4;
	
	public Block(Texture[] faces) {
		this.faces = faces;
	}
	
	public Block(String face) {
		faces = new Texture[5];
		faces[0] = new Texture(face);
		faces[1] = new Texture(face);
		faces[2] = new Texture(face);
		faces[3] = new Texture(face);
		faces[4] = new Texture(face);
	}
	
	public Block(String side, int animations, double speed) {
		faces = new Texture[5];
		faces[0] = new Texture(side, animations, speed);
		faces[1] = new Texture(side, animations, speed);
		faces[2] = new Texture(side, animations, speed);
		faces[3] = new Texture(side, animations, speed);
		faces[4] = new Texture(side, animations, speed);
	}
	
	public boolean getCull(int side) {
		switch(side) {
			case 0: return true;
			case 1: return true;
			case 2: return true;
			case 3: return true;
			case 4: return true;
			default: return false;
		}
	}
	
	public boolean isTranslucent() {
		return false;
	}
	
	public double[][][] getLightChecks() {
		return new double[][][] {
			{{0, 1, 0},		{-.25, .49, .25},	{.25, .49, .25},	{.25, .49, -.25},	{-.25, .49, -.25}},
			{{0, 0, -1},	{-.25, .25, -.49},	{.25, .25, -.49},	{.25, -.25, -.49},	{-.25, -.25, -.49}},
			{{0, 0, 1},		{-.25, .25, .49},	{.25, .25, .49},	{.25, -.25, .49},	{-.25, -.25, .49}},
			{{-1, 0, 0},	{-.49, -.25, .25},	{-.49, .25, .25},	{-.49, .25, -.25},	{-.49, -.25, -.25}},
			{{1, 0, 0},		{.49, -.25, .25},	{.49, .25, .25},	{.49, .25, -.25},	{.49, -.25, -.25}},
		};
	}
	
	public void render(EngineCore core, Draw draw, double rot, boolean[] cull, float[][] lighting) {
		if (!cull[SIDETOP]) draw.drawBlockTop(faces[SIDETOP], rot, lighting[SIDETOP]);
		if (!cull[SIDEFRONT]) draw.drawBlockSide(faces[SIDEFRONT], true, rot, lighting[SIDEFRONT]);
		if (!cull[SIDEBACK]) draw.drawBlockSide(faces[SIDEBACK], true, rot+Math.PI, lighting[SIDEBACK]);
		if (!cull[SIDELEFT]) draw.drawBlockSide(faces[SIDELEFT], true, rot+Math.PI/2, lighting[SIDELEFT]);
		if (!cull[SIDERIGHT]) draw.drawBlockSide(faces[SIDERIGHT], true, rot-Math.PI/2, lighting[SIDERIGHT]);
	}
	
}
