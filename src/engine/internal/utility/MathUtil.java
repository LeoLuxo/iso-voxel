package engine.internal.utility;

import engine.internal.types.World;

public class MathUtil {
	
	public static final double SIN45 = Math.sqrt(2) / 2;
	
	public static boolean isInQuadrant(double angle, boolean q1, boolean q2, boolean q3, boolean q4) {
		while (angle < 0) angle += Math.PI*2;
		angle %= Math.PI*2;
		return ((angle >= 0 && angle < Math.PI/2) && q1) || ((angle >= Math.PI/2 && angle < Math.PI) && q2) || ((angle >= Math.PI && angle < Math.PI/2*3) && q3) || ((angle >= Math.PI/2*3 && angle < Math.PI*2) && q4);
	}
	
	public static boolean cullSurround(boolean[] cull) {
		return (cull[0] && cull[1] && cull[2] && cull[3] && cull[4]);
	}
	
	public static float[] addColor(float[] a, float[] b) {
		float[] c = a.clone();
		c[0] += b[0];
		c[1] += b[1];
		c[2] += b[2];
		return c;
	}
	
	public static float[] mulColor(float[] a, double b) {
		float[] c = a.clone();
		c[0] *= b;
		c[1] *= b;
		c[2] *= b;
		return c;
	}
	
	public static boolean segIntersect(World w, double x0, double y0, double z0, double x1, double y1, double z1) {
		
		double dx = Math.abs(x1 - x0);
	    double dy = Math.abs(y1 - y0);
		double dz = Math.abs(z1 - z0);

	    int ox0 = (int) Math.floor(x0);
	    int oy0 = (int) Math.floor(y0);
	    int oz0 = (int) Math.floor(z0);
	    int ox1 = (int) Math.floor(x1);
	    int oy1 = (int) Math.floor(y1);
	    int oz1 = (int) Math.floor(z1);
	    
	    int x = ox0;
	    int y = oy0;
	    int z = oz0;
	    
	    double dtdx = 1.0 / dx;
	    double dtdy = 1.0 / dy;
	    double dtdz = 1.0 / dz;
	    
		@SuppressWarnings("unused")
	    double t = 0;

	    int n = 1;
	    int xInc, yInc, zInc;
	    double tNextY, tNextX, tNextZ;
		
	    if (dx == 0) {
	        xInc = 0;
	        tNextX = dtdx;
	    } else if (x1 > x0) {
	        xInc = 1;
	        n += (int) Math.floor(x1) - x;
	        tNextX = (Math.floor(x0) + 1 - x0) * dtdx;
	    } else {
	        xInc = -1;
	        n += x - (int)Math.floor(x1);
	        tNextX = (x0 - Math.floor(x0)) * dtdx;
	    }
	    
	    if (dy == 0) {
	        yInc = 0;
	        tNextY = dtdy;
	    } else if (y1 > y0) {
	        yInc = 1;
	        n += (int) Math.floor(y1) - y;
	        tNextY = (Math.floor(y0) + 1 - y0) * dtdy;
	    } else {
	        yInc = -1;
	        n += y - (int)Math.floor(y1);
	        tNextY = (y0 - Math.floor(y0)) * dtdy;
	    }
	    
	    if (dz == 0) {
	        zInc = 0;
	        tNextZ = dtdz;
	    } else if (z1 > z0) {
	        zInc = 1;
	        n += (int) Math.floor(z1) - z;
	        tNextZ = (Math.floor(z0) + 1 - z0) * dtdz;
	    } else {
	        zInc = -1;
	        n += z - (int)Math.floor(z1);
	        tNextZ = (z0 - Math.floor(z0)) * dtdz;
	    }
	    
	    for (; n > 0; --n) {
	    	if (!(x == ox0 && y == oy0 && z == oz0) && !(x == ox1 && y == oy1 && z == oz1) && w.getBlock(x, y, z) != null && !w.getBlock(x, y, z).isTranslucent()) {
	    		return true;
	    	}
	    	
	    	if ((x >= w.size && x1 >= w.size) || (x < 0 && x1 < 0) || (y >= w.size && y1 >= w.size) || (y < 0 && y1 < 0) || (z >= w.size && z1 >= w.size) || (z < 0 && z1 < 0)) {
	    		return false;
	    	}
	    	
		    if (tNextX <= tNextY && tNextX <= tNextZ) {
			    x += xInc;
			    t = tNextX;
			    tNextX += dtdx;
		    } else if (tNextY <= tNextX && tNextY <= tNextZ) {
			    y += yInc;
			    t = tNextY;
			    tNextY += dtdy;
		    } else {
			    z += zInc;
			    t = tNextZ;
			    tNextZ += dtdz;
		    }
	    }
	    
		return false;
	}
	
}
