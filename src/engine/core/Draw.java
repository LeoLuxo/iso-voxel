package engine.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.RescaleOp;
import java.util.Stack;

import engine.internal.types.Texture;
import engine.internal.utility.MathUtil;

public class Draw {
	
	private Graphics2D g;
	
	private long tt;
	private double scale;
	private int unit;
	private float alpha;
	
	private Stack<AffineTransform> transStack = new Stack<AffineTransform>();
	private Stack<Composite> compStack = new Stack<Composite>();
	private Stack<Shape> clipStack = new Stack<Shape>();
	
	public void gSet(Graphics2D g) {
		this.g = g;
	}
	
	public void gDispose() {
		g.dispose();
	}
	
	public Graphics2D gDirect() {
		return g;
	}
	
	public void gSetScale(double scale) {
		this.scale = scale;
	}
	
	public double gGetScale() {
		return scale;
	}
	
	public void gSetUnit(int unit) {
		this.unit = unit;
	}
	
	public int gGetUnit() {
		return unit;
	}
	
	public void gSetAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public float gGetAlpha() {
		return alpha;
	}
	
	public void gTranslate(double tx, double ty) {
		g.translate(tx, ty);
	}
	
	public void gScale(double sx, double sy) {
		g.scale(sx, sy);
	}
	
	public void gRotate(double theta, double x, double y) {
		g.rotate(theta, x, y);
	}
	
	public void gShear(double shx, double shy) {
		g.shear(shx, shy);
	}
	
	public void gClip(int x, int y, int w, int h) {
		g.setClip(x, y, w, h);
	}
	
	public void gTranslateUnit(double tx, double ty) {
		g.translate(tx * unit, ty * unit);
	}
	
	public void gScaleUnit(double sx, double sy) {
		g.scale(sx * unit, sy * unit);
	}
	
	public void gRotateUnit(double theta, double x, double y) {
		g.rotate(theta, x * unit, y * unit);
	}
	
	public void gShearUnit(double shx, double shy) {
		g.shear(shx * unit, shy * unit);
	}
	
	public void gClipUnit(double x, double y, double w, double h) {
		g.setClip((int) (x * unit), (int) (y * unit), (int) (w * unit), (int) (h * unit));
	}
	
	public void gPushTransform() {
		transStack.push(g.getTransform());
	}
	
	public void gPopTransform() {
		g.setTransform(transStack.pop());
	}
	
	public void gClearTransform() {
		transStack.clear();
	}
	
	public void gPushComposite() {
		compStack.push(g.getComposite());
	}
	
	public void gPopComposite() {
		g.setComposite(compStack.pop());
	}
	
	public void gClearComposite() {
		compStack.clear();
	}
	
	public void gPushClip() {
		clipStack.push(g.getClip());
	}
	
	public void gPopClip() {
		g.setClip(clipStack.pop());
	}
	
	public void gClearClip() {
		clipStack.clear();
	}
	
	public void gSetAlphaComposite(float alpha) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}
	
	
	
	
	
	public void setTT(long tt) {
		this.tt = tt;
	}
	
	public long getTT() {
		return tt;
	}
	
	
	
	
	
	public void transBlockTop(double rot, double offsetX, double offsetY, double offsetZ) {
		gScale(scale, MathUtil.SIN45 * scale);
		gTranslate(-unit / 2, offsetY * unit - unit);
		gRotate(rot, unit/2, unit/2);
		gTranslate(offsetX * unit, offsetZ * unit);
	}
	
	public void transBlockSide(double rot, double offsetX, double offsetY, double offsetZ) {
		gTranslate(((Math.cos(rot + Math.PI/4*3) * MathUtil.SIN45 + 0.5 + Math.cos(rot + Math.PI / 2) * offsetZ) - 0.5) * unit * scale, ((Math.sin(rot + Math.PI/4*3) * MathUtil.SIN45 + 0.5 + offsetY + Math.sin(rot + Math.PI / 2) * offsetZ) - 1) * MathUtil.SIN45 * unit * scale);
		gShear(0, Math.sin(rot) / Math.cos(rot) * MathUtil.SIN45);
		gScale(Math.cos(rot) * scale, MathUtil.SIN45 * scale);
		gTranslate(offsetX * unit, 0);
	}
	
	
	
	
	
	public void drawText(String text, int x, int y, Color color) {
		Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
		g.setColor(color);
		g.drawString(text, x, (int) (y + r.getHeight()));
	}
	
	public void drawText(String text, int x, int y, Color color, Font font) {
		Font f = g.getFont();
		g.setFont(font);
		drawText(text, x, y, color);
		g.setFont(f);
	}
	
	
	
	
	
	public void drawTextBackground(String text, int x, int y, Color fcolor, Color bcolor) {
		Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
		g.setColor(bcolor);
		g.fillRect(x, y, (int) r.getWidth(), (int) r.getHeight());
		g.setColor(fcolor);
		g.drawString(text, x, (int) (y + r.getHeight()));
	}
	
	public void drawTextBackground(String text, int x, int y, Color fcolor, Color bcolor, int margin) {
		Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
		g.setColor(bcolor);
		g.fillRect(x - margin, y - margin, (int) r.getWidth() + margin*2, (int) r.getHeight() + margin*2);
		g.setColor(fcolor);
		g.drawString(text, x, (int) (y + r.getHeight()));
	}
	
	public void drawTextBackground(String text, int x, int y, Color fcolor, Color bcolor, Font font) {
		Font f = g.getFont();
		g.setFont(font);
		drawTextBackground(text, x, y, fcolor, bcolor);
		g.setFont(f);
	}
	
	public void drawTextBackground(String text, int x, int y, Color fcolor, Color bcolor, int margin, Font font) {
		Font f = g.getFont();
		g.setFont(font);
		drawTextBackground(text, x, y, fcolor, bcolor, margin);
		g.setFont(f);
	}
	
	
	
	
	
	public void drawTextDropShadow(String text, int x, int y, int offset, boolean opaque, Color color, Color scolor) {
		if (opaque) {
			for (int i = 0; i < offset; i++) {
				drawText(text, x + i, y + i, scolor);
			}
		} else {
			drawText(text, x + offset, y + offset, scolor);
		}
		drawText(text, x, y, color);
	}
	
	public void drawTextDropShadow(String text, int x, int y, int offset, boolean opaque, Color color, Color scolor, Font font) {
		Font f = g.getFont();
		g.setFont(font);
		drawTextDropShadow(text, x, y, offset, opaque, color, scolor);
		g.setFont(f);
	}
	
	
	
	
	
	public void drawBlockTop(Texture tex, double rot, float[] colorScale) {
		gPushTransform();
		gPushComposite();
		transBlockTop(rot, 0, 0, 0);
		RescaleOp rsop = new RescaleOp(colorScale, new float[] {0, 0, 0, 0}, null);
		gSetAlphaComposite(alpha);
		g.drawImage(tex.getImg(tt), rsop, 0, 0);
		gPopTransform();
		gPopComposite();
	}
	
	public void drawBlockTop(Texture tex, double rot, float[] colorScale, double offsetX, double offsetY, double offsetZ) {
		gPushTransform();
		gPushComposite();
		transBlockTop(rot, offsetX, offsetY, offsetZ);
		RescaleOp rsop = new RescaleOp(colorScale, new float[] {0, 0, 0, 0}, null);
		gSetAlphaComposite(alpha);
		g.drawImage(tex.getImg(tt), rsop, 0, 0);
		gPopTransform();
		gPopComposite();
	}
	
	public void drawBlockTop(Texture tex, double rot, float[] colorScale, double offsetX, double offsetY, double offsetZ, double cropX, double cropY, double cropW, double cropH) {
		gPushTransform();
		gPushComposite();
		gPushClip();
		transBlockTop(rot, offsetX, offsetY, offsetZ);
		RescaleOp rsop = new RescaleOp(colorScale, new float[] {0, 0, 0, 0}, null);
		gSetAlphaComposite(alpha);
		g.setClip(0, 0, (int) (cropW * unit), (int) (cropH * unit));
		g.drawImage(tex.getImg(tt), rsop, -(int) (cropX * unit), -(int) (cropY * unit));
		gPopTransform();
		gPopComposite();
		gPopClip();
	}
	
	
	
	
	
	public void drawBlockSide(Texture tex, boolean culling, double rot, float[] colorScale) {
		if (culling && MathUtil.isInQuadrant(rot, false, true, true, false)) return;
		gPushTransform();
		gPushComposite();
		transBlockSide(rot, 0, 0, 0);
		RescaleOp rsop = new RescaleOp(colorScale, new float[] {0, 0, 0, 0}, null);
		gSetAlphaComposite(alpha);
		g.drawImage(tex.getImg(tt), rsop, 0, 0);
		gPopTransform();
		gPopComposite();
	}
	
	public void drawBlockSide(Texture tex, boolean culling, double rot, float[] colorScale, double offsetX, double offsetY, double offsetZ) {
		if (culling && MathUtil.isInQuadrant(rot, false, true, true, false)) return;
		gPushTransform();
		gPushComposite();
		transBlockSide(rot, offsetX, offsetY, offsetZ);
		RescaleOp rsop = new RescaleOp(colorScale, new float[] {0, 0, 0, 0}, null);
		gSetAlphaComposite(alpha);
		g.drawImage(tex.getImg(tt), rsop, 0, 0);
		gPopTransform();
		gPopComposite();
	}
	
	public void drawBlockSide(Texture tex, boolean culling, double rot, float[] colorScale, double offsetX, double offsetY, double offsetZ, double cropX, double cropY, double cropW, double cropH) {
		if (culling && MathUtil.isInQuadrant(rot, false, true, true, false)) return;
		gPushTransform();
		gPushComposite();
		gPushClip();
		transBlockSide(rot, offsetX, offsetY, offsetZ);
		RescaleOp rsop = new RescaleOp(colorScale, new float[] {0, 0, 0, 0}, null);
		gSetAlphaComposite(alpha);
		g.setClip(0, 0, (int) (cropW * unit), (int) (cropH * unit));
		g.drawImage(tex.getImg(tt), rsop, -(int) (cropX * unit), -(int) (cropY * unit));
		gPopTransform();
		gPopComposite();
		gPopClip();
	}
}
