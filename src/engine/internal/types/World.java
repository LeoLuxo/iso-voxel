package engine.internal.types;

import java.util.ArrayList;
import java.util.HashMap;

import engine.internal.types.blocks.*;
import engine.internal.utility.ImageUtil;
import engine.io.Console;

public class World {
	
	private HashMap<String, Block> blocks;
	
	private Block[][][] terrain;
	
	public float[] ambientLight;
	public ArrayList<Light> lights;
	
	public int size;
	
	public World() {
		
		size = 32;
		terrain = new Block[size][size][size];
		
		blocks = new HashMap<String, Block>();
		loadBlocks();
		
		ambientLight = new float[] {0.3f, 0.3f, 0.3f};
		
		lights = new ArrayList<Light>();
		lights.add(new Light(new float[] {1f, 1f, 0.8f}, 1, 4, 100, 4, Double.POSITIVE_INFINITY));
		lights.add(new Light(new float[] {1f, 0.6f, 0f}, 0.6, 7.5, 2.5, 6.9, 4));
		lights.add(new Light(new float[] {1f, 0f, 0f}, 1, 5, 3, 2, 10));
		lights.add(new Light(new float[] {1f, 0f, 1f}, 1, 1, 2, 0, 5));
	}
	
	public void loadBlocks() {
		blocks.put("brick", new Block("blocks/brick"));
		blocks.put("diamond_ore", new Block("blocks/diamond_ore"));
		blocks.put("diamond_block", new Block("blocks/diamond_block"));
		blocks.put("redstone_ore", new Block("blocks/redstone_ore"));
		blocks.put("redstone_block", new Block("blocks/redstone_block"));
		
		blocks.put("furnace", new Block(ImageUtil.findBlock("blocks/furnace", "furnace")));
		blocks.put("crafting_table", new Block(ImageUtil.findBlock("blocks/crafting_table", "crafting_table")));
		
		blocks.put("water", new BlockWater("blocks/water", 0, 0.2));
		blocks.put("portal", new BlockWater("blocks/portal", 0, 0.5));
		
		blocks.put("brick_stairs", new BlockStairs("blocks/brick"));
		
		Console.println("Loaded blocks:\n    " + blocks.keySet().toString());
	}
	
	public void generate() {
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < 2; y++) {
				for (int z = 0; z < size; z++) {
					setBlock("diamond_ore", x, y, z);
					if (y >= 1 && x % 3 == 0 && z % 3 == 0) {
						setBlock("water", x, y, z);
					}
				}
			}
		}
		
		setBlock("brick_stairs", 0, 2, 2);
		setBlock("brick_stairs", 7, 1, 5);
		
		setBlock("furnace", 7, 2, 7);
		setBlock("redstone_block", 5, 1, 2);
		setBlock("portal", 1, 2, 0);
		
		for (int i = 0; i < 12; i++) {
			setBlock("diamond_block", 3, 2+i, 5);
			setBlock("diamond_block", 4, 2+i, 5);
		}
		
	}
	
	public void setBlock(Block b, int x, int y, int z) {
		terrain[x][y][z] = b;
	}
	
	public void setBlock(String s, int x, int y, int z) {
		Block b = blocks.get(s);
		if (!(x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size)) {
			if (b != null) {
				terrain[x][y][z] = b;
			} else {
				terrain[x][y][z] = Block.ERRORBLOCK;
			}
		}
	}
	
	public Block getBlock(int x, int y, int z) {
		if (x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size) {
			return null;
		} else {
			return terrain[x][y][z];
		}
	}
	
	public Block[][][] getTerrain() {
		return terrain;
	}
	
}
