public class Projection {
	protected int top;
	protected int height;
	
	public Projection(double height, double top) {
		
		this.top = (int) Math.round(height);
		this.height = (int) Math.round(top);
	}
}