
public class Player {
	double x;
	double y;
	double direction;
	private double paces;
	
	
	public Player(double x, double y, double direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	  public void rotate(double angle) {
		    this.direction = (this.direction + angle + Math.PI * 2) % (Math.PI * 2);
		  }

		  public void walk(double distance, Map map) {
		    double dx = Math.cos(this.direction) * distance;
		    double dy = Math.sin(this.direction) * distance;
		    if (map.get(this.x + dx, this.y) <= 0) this.x += dx;
		    if (map.get(this.x, this.y + dy) <= 0) this.y += dy;
		    this.paces += distance;
		  }

		  public void update(Controls controls, Map map, double seconds) {
		    if (controls.left) this.rotate(-Math.PI * seconds*2);
		    if (controls.right) this.rotate(Math.PI * seconds*2);
		    if (controls.forward) this.walk(8 * seconds, map);
		    if (controls.backward) this.walk(-8 * seconds, map);
		  }
	
}
