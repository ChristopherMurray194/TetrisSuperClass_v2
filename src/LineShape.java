import java.awt.*;

/**
 * 
 * This class handles the initial position, rotation positions and the rotation
 * checking for the line shape.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class LineShape extends Shape {

	/**
	 * Sets the initial position of the line shape.
	 */
	public void initialShape(int gridWidth) {
		// initial position of shape
		shapeX[0] = gridWidth / 2 - 1;
		shapeY[0] = 0;

		shapeX[1] = gridWidth / 2 - 1;
		shapeY[1] = 1;

		shapeX[2] = gridWidth / 2 - 1;
		shapeY[2] = 2;

		shapeX[3] = gridWidth / 2 - 1;
		shapeY[3] = 3;
		//

		rotationCount = 0;
		shapeRotated = false;
	}

	/**
	 * Controls which rotation state the shape needs to be in
	 */
	public void rotateShape(int[][] grid) {
		// --step 2--\\

		// if first rotation has been done
		if (rotationCount == 0 && shapeRotated) {
			// allow for second rotation position
			rotationCount = 1;
			// shape has not yet been rotated
			shapeRotated = false;
		}

		// --step 4--\\

		// if line shape has been reverted to initial rotation position
		// go back to step 1
		if (rotationCount == 1 && shapeRotated) {
			// allow for it to be rotated again
			rotationCount = 0;
			// shape has not yet been rotated
			shapeRotated = false;
		}

		// --step 1--\\

		// will rotate whilst shapeRotated = false
		if (rotationCount == 0) {
			canRotate = checkRotationOne(grid);
			// if the shape is free to rotate - determined by the
			// checkRotationOne method
			if (canRotate) {
				// sets value of shape to 0 (clears shape)
				setShapeValue(grid, 0);
				// first rotation position
				rotationPosOne();
				shapeRotated = true;
			}
		}

		// --step 3--\\

		if (rotationCount == 1) {
			canRotate = checkRotationTwo(grid);
			// if the shape is free to rotate - determined by the
			// checkRotationTwo method
			if (canRotate) {
				// sets value of shape to 0 (clears shape)
				setShapeValue(grid, 0);
				// second rotation position
				rotationPosTwo();
				shapeRotated = true;
			}
		}
	}

	/**
	 * First rotation state
	 */
	private void rotationPosOne() {
		// redraw in new position, rotated 90 degrees
		shapeX[0] -= 1;
		shapeY[0] += 1;

		shapeX[2] += 1;
		shapeY[2] -= 1;

		shapeX[3] += 2;
		shapeY[3] -= 2;
	}

	/**
	 * Second rotation state, which for the line shape is it's initial position
	 */
	private void rotationPosTwo() {
		// reverts line shape back to it's initial position
		shapeX[0] += 1;
		shapeY[0] -= 1;

		shapeX[2] -= 1;
		shapeY[2] += 1;

		shapeX[3] -= 2;
		shapeY[3] += 2;
	}

	/**
	 * checks whether the positions for the first rotation state are free, if
	 * they are not false is returned, otherwise the shape can rotate
	 * 
	 * @param grid
	 * @return
	 */
	private boolean checkRotationOne(int[][] grid) {
		if ((grid[shapeX[0] - 1][shapeY[0] + 1] >= 2 && grid[shapeX[0] - 1][shapeY[0] + 1] <= 8)
				|| (grid[shapeX[1]][shapeY[1]] >= 2 && grid[shapeX[1]][shapeY[1]] <= 8)
				|| (grid[shapeX[2] + 1][shapeY[2] - 1] >= 2 && grid[shapeX[2] + 1][shapeY[2] - 1] <= 8)
				|| (grid[shapeX[3] + 2][shapeY[3] - 2] >= 2 && grid[shapeX[3] + 2][shapeY[3] - 2] <= 8)) {
			return false;
		}
		return true;
	}

	/**
	 * checks whether the positions for the final rotation state are free, if
	 * they are not false is returned, otherwise the shape can rotate
	 * 
	 * @param grid
	 * @return
	 */
	private boolean checkRotationTwo(int[][] grid) {
		if ((grid[shapeX[0] + 1][shapeY[0] - 1] >= 2 && grid[shapeX[0] + 1][shapeY[0] - 1] <= 8)
				|| (grid[shapeX[1]][shapeY[1]] >= 2 && grid[shapeX[1]][shapeY[1]] <= 8)
				|| (grid[shapeX[2] - 1][shapeY[2] + 1] >= 2 && grid[shapeX[2] - 1][shapeY[2] + 1] <= 8)
				|| (grid[shapeX[3] - 2][shapeY[3] + 2] >= 2 && grid[shapeX[3] - 2][shapeY[3] + 2] <= 8)) {
			return false;
		}
		return true;
	}
}