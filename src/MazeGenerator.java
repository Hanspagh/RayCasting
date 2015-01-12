import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;


public class MazeGenerator {

	Random r = new Random();
	Cell[][] cells;
	int size;
	ArrayList<Cell> stack = new ArrayList<Cell>();
	
	public MazeGenerator(int size) {
		this.size = size;
		cells = new Cell[size][size];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j] = new Cell(i,j);
			}
		}
	}
	
	public double[][] generate() {
		int visited = 0;
		Cell currentCell = cells[1][1];
		currentCell.visisted = true;
		currentCell.wall = false;
		
		ArrayList<Cell> unVisited = getUnVisited();
		while(unVisited.size() > 0) {
			ArrayList<Cell> neighbours = getNeighbours(currentCell.x, currentCell.y);
			neighbours = removeNullAndLessThanTwoWalls(neighbours);
			if(neighbours.size() != 0) {
				Cell cell = neighbours.get(r.nextInt(neighbours.size()));
				stack.add(currentCell);
				currentCell = cell;
				currentCell.visisted = true;
				currentCell.wall = false;
				} else if(stack.size() > 0) {
					currentCell = stack.get(stack.size()-1);
					stack.remove(stack.size()-1);
					
				} else {
					currentCell = unVisited.get(r.nextInt(unVisited.size()));
					currentCell.visisted = true;
					
				}
			unVisited = getUnVisited();
			}
		
		return toArray(cells);
		
	
	}
	
	public double[][] PrimGenerate() {
		ArrayList<Cell> walls = new ArrayList<Cell>();
		Cell currentCell = cells[1][1];
		currentCell.wall = false;
		currentCell.visisted = true;
		
		ArrayList<Cell> neighbours = getNeighbours(currentCell.x, currentCell.y);
		
		walls.addAll(neighbours);
		
		while(walls.size() > 0) {
			Cell cell = walls.get(r.nextInt(walls.size()));
			if(cell.hasThreeWalls()) {
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2 ;j++) {
					if(Math.abs(i) - Math.abs(j) == 0) continue;
					if(!get(cell.x+i, cell.y+j).wall) {
						if(get(cell.x+i*-1, cell.y+j*-1).wall) {
							cell.wall = false;
							cell.visisted = true;
							neighbours = getNeighbours(cell.x, cell.y);
							walls.addAll(neighbours);
						}
					}
				}
			}
					
			}
			walls.remove(cell);
			//draw();
		}
		return toArray(cells);
	}


	public ArrayList<Cell> getUnVisited() {
		ArrayList<Cell> result = new ArrayList<MazeGenerator.Cell>();
		for (int i = 1; i < cells.length-1; i++) {
			for (int j = 1; j < cells.length-1; j++) {
				if(!cells[i][j].visisted) { 
						result.add(cells[i][j]);
					}
				}
			}
		
		return result;
	}
	
	private double[][] toArray(Cell[][] cells2) {
		double[][] array = new double[size][size];
		for (int i = 0; i < cells2.length; i++) {
			for (int j = 0; j < cells2.length; j++) {
				array[i][j] = cells2[i][j].wall ? 1 : 0;
			}
		}
		return array;
	}

	public ArrayList<Cell> getNeighbours(int x, int y) {
		ArrayList<Cell> result = new ArrayList<MazeGenerator.Cell>();
		addCell(x+1, y, result);
		addCell(x-1, y, result);
		addCell(x, y+1, result);
		addCell(x, y-1, result);
		return result;
	}

	private void addCell(int x, int y, ArrayList<Cell> result) {
		Cell cell = get(x,y);
		if(cell != null) {
			result.add(cell);
		}
	}
	
	public Cell get(int x, int y) {
		if(x >= 0 && y >=0 && x < size && y < size) {
			return cells[x][y];
		}
		return null;
		
		
	}
	
	public ArrayList<Cell> removeNullAndLessThanTwoWalls(ArrayList<Cell> result) {
		ArrayList<Cell> newResult = new ArrayList<MazeGenerator.Cell>();
		for (int i = 0; i < result.size(); i++) {
			Cell cell = result.get(i);
			if(!cell.visisted && cell.hasThreeWalls()) {
				newResult.add(cell);
			}
		}
		
		return newResult;
	}
	
	public void draw() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				System.out.print(cells[i][j].wall ? 1 : 0);
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public class Cell {

		boolean visisted = false;
		boolean wall = true;
		int x;
		int y;
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public boolean hasThreeWalls() {
			int count = 0;
			for (Cell cell : getNeighbours(x, y)) {
				if(cell.wall) {
					count++;
				}
			}
			if(x == 0) count = 0;
			if(y == 0) count = 0;
			if(x == size-1) count = 0;
			if(y == size-1) count = 0;
			
			return count >= 3;
		}
		
		
	}

}
