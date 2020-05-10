package engine.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.io.Config;

public class Screen extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private EngineCore core;
	private JPanel panel;
	private Draw draw;
	
	public double clientRotation = 0;
	public double clientZoom = 8;
	public double clientCamX = 3.5;
	public double clientCamY = 0.5;
	public double clientCamZ = 3.5;
	
	public Screen(EngineCore core) {
		this.core = core;
	}
	
	public void init() {
		
		System.setProperty("sun.java2d.opengl", "true");
		
		setTitle(Config.PROJECTNAME);
		setSize(Config.SCREENWIDTH, Config.SCREENHEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(false);
		
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g) {
				render((Graphics2D) g);
			}
		};
		
		panel.setSize(this.getSize());
		
		add(panel);
		addKeyListener(core.inputManager);
		addMouseMotionListener(core.inputManager);
		
		if (Config.MOUSECONTROL) {
			BufferedImage blankCursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			panel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(blankCursor, new Point(0, 0), "blank_cursor"));
		}
		
		setVisible(true);
		requestFocus();
		
		draw = new Draw();
		
	}
	
	public void paintPanel(Graphics2D g) {
		panel.paint(g);
	}
	
	public void tickRender() {
		panel.paintImmediately(panel.getBounds());
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
		g.setClip(0, 0, core.screen.getWidth(), core.screen.getHeight());
		
		draw.gSet(g);
		draw.gPushTransform();
		
		core.render(draw);
		
		draw.gPopTransform();
		draw.gClearTransform();
		draw.gClearComposite();
		draw.gClearClip();
		draw.gDispose();
	}
	
}
