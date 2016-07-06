import java.awt.*;

/**
 * 
 * This class handles the initial position of the box shape. As the shape does
 * not need to rotate, the rotation method is created (because it is an abstract
 * method in the superclass) but does not carry out any tasks, nor are any
 * rotation check methods needed.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class BoxShape extends Shape {

	/**
	 * Sets the initial position of the box shape.
	 */
	public void initialShape(int gridWidth) {
		// initial position of shape
		shapeX[0] = gridWidth / 2 - 1;
		shapeY[0] = 0;

		shapeX[1] = gridWidth / 2;
		shapeY[1] = 0;

		shapeX[2] = gridWidth / 2 - 1;
		shapeY[2] = 1;

		shapeX[3] = gridWidth / 2;
		shapeY[3] = 1;
		//
	}

	/**
	 * As this is an abstract method in the superclass, it must be implemented
	 * by this subclass, however as the box shape does not need to rotate it
	 * does not carry out any code.
	 * 
	 */
	public void rotateShape(int[][] grid) {

	}
}