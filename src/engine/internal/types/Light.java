package engine.internal.types;

public class Light {
	
	public float[] color;
	public double intensity;
	public double x;
	public double y;
	public double z;
	public double radius;
	
	public Light(float[] color, double intensity, double x, double y, double z, double radius) {
		this.color = color;
		this.intensity = intensity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
	}
	
	public Light(float r, float g, float b, double intensity, double x, double y, double z, double radius) {
		this.color = new float[] {r, g, b};
		this.intensity = intensity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
	}
	
	public Light(float[] color, double intensity, int x, int y, int z, double radius) {
		this.color = color;
		this.intensity = intensity;
		this.x = x + 0.5;
		this.y = y + 0.5;
		this.z = z + 0.5;
		this.radius = radius;
	}
	
	public Light(float r, float g, float b, double intensity, int x, int y, int z, double radius) {
		this.color = new float[] {r, g, b};
		this.intensity = intensity;
		this.x = x + 0.5;
		this.y = y + 0.5;
		this.z = z + 0.5;
		this.radius = radius;
	}
}
