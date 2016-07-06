import java.awt.*;

/**
 * 
 * This is the shape superclass.
 * It contains all methods and fields common across all shapes. It is also contains abstract methods meaning each subclass must contain that method (even if it is empty).
 * This class handles the movement, rotation (each shape subclass implements this method in their own way) and collision detection of any shape. 
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public abstract class Shape {

	// Array to hold the X coordinates of the shape
	protected int[] shapeX = new int[4];
	// Array to hold the Y coordinates of the shape
	protected int[] shapeY = new int[4];

	protected int rotationCount = 0;

	// shapeRotated is necessary to determine whether a shape has yet been rotated. Without this variable and with the rotateShape method's body in its previous state the rotation would
	// not change because rotation count is 0, when it is 0 the first rotation is called, however when rotationCount is 0 rotationCount is then set to 1, when rotationCount is 1 it is
	// also then set back to 0 resulting in an endless loop and no visible rotation.
	protected boolean shapeRotated = false;
	
	// Initially the shape can rotate
	protected boolean canRotate = true;

	/**
	 * Sets the value of the shape's cells
	 * @param grid
	 * @param value
	 */
	public void setShapeValue(int[][] grid, int value) {

		// sets each rectangle of the shape within the grid to the value of the
		// parameter
		grid[shapeX[0]][shapeY[0]] = value;
		grid[shapeX[1]][shapeY[1]] = value;
		grid[shapeX[2]][shapeY[2]] = value;
		grid[shapeX[3]][shapeY[3]] = value;
	}

	/**
	 * Will set the initial position of a shape. 
	 * Each shape class will implement this method in their own way to set that shape's initial position. 
	 * 
	 * @param gridWidth
	 */
	protected abstract void initialShape(int gridWidth);

	/**
	 * Will handle the rotation of a shape.
	 * Each shape class will implement this method in their own way to set that shape's rotation states.
	 * @param grid
	 */
	protected abstract void rotateShape(int[][] grid);

	/**
	 * Draws the shape and sets its initial colour and colour once a shape becomes fixed in its position.
	 * 
	 * @param g
	 * @param gridWidth
	 * @param gridHeight
	 * @param grid
	 * @param cellSize
	 * @param color
	 */
	public void drawShape(Graphics g, int gridWidth, int gridHeight,
			int[][] grid, int cellSize, Color color) {

		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {

				// if a cell in the grid has a value of 1 draw the shape
				// with colour
				if (grid[i][j] == 1) {
					g.setColor(color);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				// end if

				// if a cell is occupied fix those cells as the shape's colour
				if (grid[i][j] == 2) {
					g.setColor(Color.orange);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				if (grid[i][j] == 3) {
					g.setColor(Color.red);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				if (grid[i][j] == 4) {
					g.setColor(Color.magenta);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				if (grid[i][j] == 5) {
					g.setColor(Color.cyan);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				if (grid[i][j] == 6) {
					g.setColor(Color.green);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				if (grid[i][j] == 7) {
					g.setColor(Color.pink);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				if (grid[i][j] == 8) {
					g.setColor(Color.yellow);
					g.fillRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
					g.setColor(Color.black);
					g.drawRect(i * cellSize + 12, j * cellSize + 35, cellSize,
							cellSize);
				}
				
				// end if
			}
		}
	}

	/**
	 * Drops the shape down one row.
	 * 
	 * @param grid
	 */
	private void dropShape(int[][] grid) {
		// clears shape position on the grid
		grid[shapeX[0]][shapeY[0]] = 0;
		grid[shapeX[1]][shapeY[1]] = 0;
		grid[shapeX[2]][shapeY[2]] = 0;
		grid[shapeX[3]][shapeY[3]] = 0;

		// sets shape's Y position to plus one to the previous position
		shapeY[0] += 1;
		shapeY[1] += 1;
		shapeY[2] += 1;
		shapeY[3] += 1;

	}

	/**
	 * Moves the shape left one column. 
	 * 
	 * @param grid
	 */
	public void moveShapeLeft(int[][] grid) {

		// clears shape position on the grid
		grid[shapeX[0]][shapeY[0]] = 0;
		grid[shapeX[1]][shapeY[1]] = 0;
		grid[shapeX[2]][shapeY[2]] = 0;
		grid[shapeX[3]][shapeY[3]] = 0;

		// moves shape left
		shapeX[0] -= 1;
		shapeX[1] -= 1;
		shapeX[2] -= 1;
		shapeX[3] -= 1;
	}

	/**
	 * Moves the shape right one column.
	 * 
	 * @param grid
	 */
	public void moveShapeRight(int[][] grid) {

		// clears shape position on the grid
		grid[shapeX[0]][shapeY[0]] = 0;
		grid[shapeX[1]][shapeY[1]] = 0;
		grid[shapeX[2]][shapeY[2]] = 0;
		grid[shapeX[3]][shapeY[3]] = 0;

		// moves shape right
		shapeX[0] += 1;
		shapeX[1] += 1;
		shapeX[2] += 1;
		shapeX[3] += 1;
	}

	/**
	 * checks that the cells immediately below the shape are not occupied and
	 * then drops the shape if it is clear
	 * 
	 * @param grid
	 */
	public void checkShapeDrop(int[][] grid) {
		// if the cells immediately below the shape are not occupied
		if (!(grid[shapeX[0]][shapeY[0] + 1] >= 2 && grid[shapeX[0]][shapeY[0] + 1] <= 8)
				&& !(grid[shapeX[1]][shapeY[1] + 1] >= 2 && grid[shapeX[1]][shapeY[1] + 1] <= 8)
				&& !(grid[shapeX[2]][shapeY[2] + 1] >= 2 && grid[shapeX[2]][shapeY[2] + 1] <= 8)
				&& !(grid[shapeX[3]][shapeY[3] + 1] >= 2 && grid[shapeX[3]][shapeY[3] + 1] <= 8)) {
			// then drop the shape
			dropShape(grid);
		}
	}

	/**
	 * Checks if a shape has collided with an occupied cell, if it has a new
	 * shape is needed
	 * 
	 * @param grid
	 * @param gridWidth
	 * @param gridHeight
	 * @return
	 */
	public boolean checkCollision(int[][] grid, int currentShape) {
		// if the cells immediately below the shape are occupied 
		if ((grid[shapeX[0]][shapeY[0] + 1] >= 2 && grid[shapeX[0]][shapeY[0] + 1] <= 8)
				|| (grid[shapeX[1]][shapeY[1] + 1] >= 2 && grid[shapeX[1]][shapeY[1] + 1] <= 8)
				|| (grid[shapeX[2]][shapeY[2] + 1] >= 2 && grid[shapeX[2]][shapeY[2] + 1] <= 8)
				|| (grid[shapeX[3]][shapeY[3] + 1] >= 2 && grid[shapeX[3]][shapeY[3] + 1] <= 8)) {
			// set the value of the shape's cells to 2 (fixing them in position).
			setShapeValue(grid, currentShape + 1);
			if(currentShape == 0){
				setShapeValue(grid, 8);
			}
			// shape has collided
			return true;
		}
		// otherwise it hasn't
		return false;
	}
	
	public boolean checkCollisionDown(int[][] grid) {
		// if the cells immediately below the shape are occupied 
		if ((grid[shapeX[0]][shapeY[0] + 1] >= 2 && grid[shapeX[0]][shapeY[0] + 1] <= 8)
				|| (grid[shapeX[1]][shapeY[1] + 1] >= 2 && grid[shapeX[1]][shapeY[1] + 1] <= 8)
				|| (grid[shapeX[2]][shapeY[2] + 1] >= 2 && grid[shapeX[2]][shapeY[2] + 1] <= 8)
				|| (grid[shapeX[3]][shapeY[3] + 1] >= 2 && grid[shapeX[3]][shapeY[3] + 1] <= 8)) {
			// shape has collided
			return false;
		}
		// otherwise it hasn't
		return true;
	}

	/**
	 * Checks if a cell immediately to right of a shape is occupied, if it is
	 * then a shape cannot move right
	 * 
	 * @param grid
	 * @return
	 */
	public boolean checkCollisionRight(int[][] grid) {
		// if the cells immediately to the right of the shape are occupied
		if ((grid[shapeX[0] + 1][shapeY[0]] >= 2 && grid[shapeX[0] + 1][shapeY[0]] <= 8)
				|| (grid[shapeX[1] + 1][shapeY[1]] >= 2 && grid[shapeX[1] + 1][shapeY[1]] <= 8)
				|| (grid[shapeX[2] + 1][shapeY[2]] >= 2 && grid[shapeX[2] + 1][shapeY[2]] <= 8)
				|| (grid[shapeX[3] + 1][shapeY[3]] >= 2 && grid[shapeX[3] + 1][shapeY[3]] <= 8)) {
			// shape cannot move right
			return false;
		}
		// otherwise the shape can move right
		return true;
	}

	/**
	 * Checks if a cell immediately to the left of a shape is occupied, if it is
	 * then a shape cannot move left
	 * 
	 * @param grid
	 * @return
	 */
	public boolean checkCollisionLeft(int[][] grid) {
		// if the cells immediately to the left of the shape are occupied
		if ((grid[shapeX[0] - 1][shapeY[0]] >= 2 && grid[shapeX[0] - 1][shapeY[0]] <= 8)
				|| (grid[shapeX[1] - 1][shapeY[1]] >= 2 && grid[shapeX[1] - 1][shapeY[1]] <= 8)
				|| (grid[shapeX[2] - 1][shapeY[2]] >= 2 && grid[shapeX[2] - 1][shapeY[2]] <= 8)
				|| (grid[shapeX[3] - 1][shapeY[3]] >= 2 && grid[shapeX[3] - 1][shapeY[3]] <= 8)) {
			// shape cannot move left
			return false;
		}
		// otherwise the shape can move left
		return true;
	}
	
	/**
	 * Getter for shapeX, value passed to the parameter is the position within the array.
	 * 
	 * @param i
	 * @return
	 */
	public int getShapeX(int i){
		return shapeX[i];
	}
	
	/**
	 * Getter for shapeY, value passed to the parameter is the position within the array.
	 * 
	 * @param i
	 * @return
	 */
	public int getShapeY(int i){
		return shapeY[i];
	}
	
	/**
	 * getter for canRotate
	 * 
	 * @return
	 */
	public boolean getCanRotate(){
		return canRotate;
	}
}