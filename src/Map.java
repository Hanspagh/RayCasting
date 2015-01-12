import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Map {

	int size;
	double[][] grid;
	double sin;
	double cos;
	
	
	public Map(int size) {
		this.size = size;
		grid = new double[size][size];
		makeMaze();
	}
	
	public double get(double x, double y) {
		 x = Math.floor(x);
		 y = Math.floor(y);
		if (x < 0 || x > this.size - 1 || y < 0 || y > this.size - 1) return -1;
		return grid[(int) x][(int) y];
	}
	
	public void randomize() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = Math.random() < 0.3 ? 1 : 0;
			}
		}
	}
	
	public void makeMaze() {
		MazeGenerator mg = new MazeGenerator(size);
		grid = mg.generate();
		
		
	}

	public Ray cast(Point p, double angle, double range) {
	    return new Ray(this, new Step(p.x, p.y, p.z, 0, 0, 0, 0, 0), Math.sin(angle), Math.cos(angle), range);
		
	}

	public void update(Controls controls) {
		if(controls.randomMap) {
			makeMaze();
		}
		
	}
	
	
	
}
