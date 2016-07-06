import java.awt.*;

/**
 * 
 * This class handles the initial position, rotation positions and the rotation
 * checking for the L shape.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class LShape extends Shape {

	/**
	 * Sets the initial position of the L shape.
	 */
	public void initialShape(int gridWidth) {
		// initial position of shape
		shapeX[0] = gridWidth / 2 - 1;
		shapeY[0] = 0;

		shapeX[1] = gridWidth / 2;
		shapeY[1] = 0;

		shapeX[2] = gridWidth / 2 - 1;
		shapeY[2] = 1;

		shapeX[3] = gridWidth / 2 - 1;
		shapeY[3] = 2;
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

		// if second rotation has been done
		if (rotationCount == 1 && shapeRotated) {
			// allow for it to be rotated again
			rotationCount = 2;
			// shape has not yet been rotated
			shapeRotated = false;
		}

		// --step 6--\\

		// if third rotation has been done
		if (rotationCount == 2 && shapeRotated) {
			// allow for it to be rotated again
			rotationCount = 3;
			// shape has not yet been rotated
			shapeRotated = false;
		}

		// --step 8--\\

		// if fourth rotation has been done
		if (rotationCount == 3 && shapeRotated) {
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

		// --step 5--\\

		if (rotationCount == 2) {
			canRotate = checkRotationThree(grid);
			if (canRotate) {
				setShapeValue(grid, 0);
				// third rotation position
				rotationPosThree();
				shapeRotated = true;
			}
		}

		// --step 7--\\

		if (rotationCount == 3) {
			canRotate = checkRotationFour(grid);
			if (canRotate) {
				setShapeValue(grid, 0);
				// fourth rotation position
				rotationPosFour();
				shapeRotated = true;
			}
		}
	}

	/**
	 * First rotation state
	 */
	private void rotationPosOne() {
		// redraw in new position, rotated 90 degrees

		shapeX[0] += 1;
		shapeY[0] += 1;

		// shapeX[1] does not change
		shapeY[1] += 2;

		shapeX[3] -= 1;
		shapeY[3] -= 1;
	}

	/**
	 * Second rotation state
	 */
	private void rotationPosTwo() {

		shapeX[0] -= 1;
		shapeY[0] += 1;

		shapeX[1] -= 2;
		// shapeY[1] does not change

		shapeX[3] += 1;
		shapeY[3] -= 1;
	}

	/**
	 * Third rotation state
	 */
	private void rotationPosThree() {
		shapeX[0] -= 1;
		shapeY[0] -= 1;

		shapeY[1] -= 2;

		shapeX[3] += 1;
		shapeY[3] += 1;
	}

	/**
	 * revert back to initial shape position
	 */
	private void rotationPosFour() {
		shapeX[0] += 1;
		shapeY[0] -= 1;

		shapeX[1] += 2;

		shapeX[3] -= 1;
		shapeY[3] += 1;
	}

	/**
	 * checks whether the positions for the first rotation state are free, if
	 * they are not false is returned, otherwise the shape can rotate
	 * 
	 * @param grid
	 * @return
	 */
	private boolean checkRotationOne(int[][] grid) {
		if ((grid[shapeX[0] + 1][shapeY[0] + 1] >= 2 && grid[shapeX[0] + 1][shapeY[0] + 1] <= 8)
				|| (grid[shapeX[1]][shapeY[1] + 2] >= 2 && grid[shapeX[1]][shapeY[1] + 2] <= 8)
				|| (grid[shapeX[2]][shapeY[2]] >= 2 && grid[shapeX[2]][shapeY[2]] <= 8)
				|| (grid[shapeX[3] - 1][shapeY[3] - 1] >= 2 && grid[shapeX[3] - 1][shapeY[3] - 1] <= 8)) {
			return false;
		}
		return true;
	}

	/**
	 * checks whether the positions for the second rotation state are free, if
	 * they are not false is returned, otherwise the shape can rotate
	 * 
	 * @param grid
	 * @return
	 */
	private boolean checkRotationTwo(int[][] grid) {
		if ((grid[shapeX[0] - 1][shapeY[0] + 1] >= 2 && grid[shapeX[0] - 1][shapeY[0] + 1] <= 8)
				|| (grid[shapeX[1] - 2][shapeY[1]] >= 2 && grid[shapeX[1] - 2][shapeY[1]] <= 8)
				|| (grid[shapeX[2]][shapeY[2]] >= 2 && grid[shapeX[2]][shapeY[2]] <= 8)
				|| (grid[shapeX[3] + 1][shapeY[3] - 1] >= 2 && grid[shapeX[3] + 1][shapeY[3] - 1] <= 8)) {
			return false;
		}
		return true;
	}

	/**
	 * checks whether the positions for the third rotation state are free, if
	 * they are not false is returned, otherwise the shape can rotate
	 * 
	 * @param grid
	 * @return
	 */
	private boolean checkRotationThree(int[][] grid) {
		if ((grid[shapeX[0] - 1][shapeY[0] - 1] >= 2 && grid[shapeX[0] - 1][shapeY[0] - 1] <= 8)
				|| (grid[shapeX[1] - 2][shapeY[1]] >= 2 && grid[shapeX[1] - 2][shapeY[1]] <= 8)
				|| (grid[shapeX[2]][shapeY[2]] >= 2 && grid[shapeX[2]][shapeY[2]] <= 8)
				|| (grid[shapeX[3] + 1][shapeY[3] + 1] >= 2 && grid[shapeX[3] + 1][shapeY[3] + 1] <= 8)) {
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
	private boolean checkRotationFour(int[][] grid) {
		if ((grid[shapeX[0] + 1][shapeY[0] - 1] >= 2 && grid[shapeX[0] + 1][shapeY[0] - 1] <= 8)
				|| (grid[shapeX[1] + 2][shapeY[1]] >= 2 && grid[shapeX[1] + 2][shapeY[1]] <= 8)
				|| (grid[shapeX[2]][shapeY[2]] >= 2 && grid[shapeX[2]][shapeY[2]] <= 8)
				|| (grid[shapeX[3] - 1][shapeY[3] + 1] >= 2 && grid[shapeX[3] - 1][shapeY[3] + 1] <= 8)) {
			return false;
		}
		return true;
	}
}