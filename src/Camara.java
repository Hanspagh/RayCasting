import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Camara extends Canvas {

	private static final int mapSize = 5;
	private static final int mapOffset = 20;
	private int resolution;
	private double focalLength;
	private double range = 14;
	private double spacing;
	private double height = 600;
	private double width = 800;

	private BufferedImage sky;
	private BufferedImage knife;
	private BufferedImage wallImage;

	private double scale;

	public Camara(int resolution, double focalLength, int height2, int width2) {
		super();
		this.resolution = resolution;
		this.focalLength = focalLength;
		height = height2;
		width = width2;
		spacing = width / resolution;
		this.scale = (this.width + this.height) / 1200;
		try {
			sky = ImageIO
					.read(new File(
							"C:/Users/HansP/workspace/RayCasting/src/res/deathvalley_panorama.jpg"));
			knife = ImageIO
					.read(new File(
							"C:/Users/HansP/workspace/RayCasting/src/res/knife_hand.png"));
			wallImage = ImageIO.read(new File(
					"C:/Users/HansP/workspace/RayCasting/src/res/wall.jpg"));
		} catch (IOException e) {
			System.out.println("Image could not be read");
			System.exit(1);
		}
	}

	public void render(Graphics canvas, Player player, Map map) {
		drawSky(canvas, player);
		drawColumns(canvas, player, map);
		drawPlayerOnMap(canvas, player);
		drawMap(canvas, map);
		drawWeapon(canvas, player.paces);

	}

	public void drawColumns(Graphics canvas, Player player, Map map) {
		for (double column = 0; column < resolution; column++) {
			double x = column / resolution - 0.5;
			double angle = Math.atan2(x, focalLength);
			Ray ray = map.cast(new Point(player.x, player.y, player.z),
					player.direction + angle, this.range);
			this.drawColumn(canvas, column, ray, angle, map);
		}
	}

	private void drawPlayerOnMap(Graphics canvas, Player player) {
		canvas.setColor(Color.RED);
		Graphics2D g2 = (Graphics2D) canvas;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		double x = Math.floor(player.x);
		double y = Math.floor(player.y);
		Shape r = new Rectangle2D.Double(x * mapSize + mapOffset,
				y * mapSize + mapOffset, mapSize, mapSize);
		g2.fill(r);
		
		x = x * mapSize + mapOffset + mapSize/2;
		y = y * mapSize + mapOffset+mapSize/2;
		Shape l = new Line2D.Double(x, y,
				x + (Math.cos(player.direction)) * 7,
				y  + Math.sin(player.direction) * 7);
		g2.draw(l);
	}

	private void drawMap(Graphics canvas, Map map) {
		for (int i = 0; i < map.size; i++) {
			for (int j = 0; j < map.size; j++) {
				double k = map.get(i, j);
				if (k > 0) {
					canvas.setColor(Color.green);
					canvas.fillRect(i * mapSize + mapOffset, j * mapSize + mapOffset, mapSize, mapSize);
				}
			}
		}
	}

	public void drawSky(Graphics canvas, Player player) {
		double width = 2000;
		double left = (player.direction / (Math.PI * 2)) * -width;

		canvas.drawImage(sky, (int) left, 0, (int) width, (int) this.height,
				this);
		if (left < width - this.width) {
			canvas.drawImage(sky, (int) (left + width), 0, (int) width,
					(int) this.height, this);
		}
	}

	private void drawWeapon(Graphics canvas, double paces) {
		double bobX = Math.cos(paces * 2) * this.scale * 6;
		double bobY = Math.sin(paces * 4) * this.scale * 6;
		double left = this.width * 0.66 + bobX;
		double top = this.height * 0.6 + bobY;
		canvas.drawImage(knife, (int) left, (int) top,
				(int) (320 * this.scale), (int) (320 * this.scale), this);
	}

	private void drawColumn(Graphics canvas, double column, Ray ray,
			double angle, Map map) {
		int hit = -1;
		double left = Math.floor(column * this.spacing);
		double width = Math.ceil(this.spacing);

		while (++hit < ray.steps.size() && ray.steps.get(hit).height <= 0)
			;

		for (int s = ray.steps.size() - 1; s >= 0; s--) {
			Step step = ray.steps.get(s);
			if (s == hit) {
				Projection wall = project(step.height, angle, step.distance);
				// Graphics2D g2 = (Graphics2D) canvas;
				// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				// canvas.setColor(Color.gray);
				// Shape r = new Rectangle2D.Double(left, wall.top, width,
				// wall.height);
				// g2.fill(r);
				canvas.drawImage(wallImage, (int) left, wall.top,
						(int) (width), (wall.height), this);
				// canvas.drawImage(texture.image, textureX, 0, 1,
				// texture.height, left, wall.top, width, wall.height);
				// canvas.setColor(Color.black);
				// canvas.fillRect(left, wall.top, width, wall.height);

			}
		}
	}

	private Projection project(double height, double angle, double distance) {
		double z = distance * Math.cos(angle);
		double wallHeight = this.height * height / z;
		double bottom = this.height / 2 * (1 + 1 / z);
		return new Projection(bottom - wallHeight, wallHeight);
	}

}
