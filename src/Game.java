import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Game extends JPanel {
	private Map map;
	private Player player;
	private Camara camara;
	public Controls controls;
	private boolean gameRunning = true;
	private long lastFpsTime;
	private int fps;
	private float interpolation;
	
	
	public Game(int width, int height) {
		
		camara = new Camara(800,0.8,height,width);
		player = new Player(2, 2, Math.PI * 1);
		map = new Map(32);	
		controls = new Controls();
		addKeyListener(controls);
		
	
	}
	
    public void setInterpolation(float interp)
    {
       interpolation = interp;
    }

	void doGameUpdates(double delta)
	{
		player.update(controls,map,delta);
		map.update(controls);
		
	}
	
	public void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0, 0, 800, 800);
		camara.render(g, player, map);
        //BS way of clearing out the old rectangle to save CPU.

        g.setColor(Color.YELLOW);
        g.drawString("direction: " + player.direction, 5, 10);
        g.setColor(Color.YELLOW);
        g.drawString("pos: " + player.x + " , " + player.y, 200	, 10);
	}
}