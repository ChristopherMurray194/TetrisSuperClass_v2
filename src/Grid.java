import java.awt.*;

/**
 * 
 * This class' sole purpose is to draw the grid.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class Grid {
	
	// the x position offset of the grid in relation to the frame 
	private int xOffset = 12;
	// the y position offset of the grid in relation to the frame
	private int yOffset = 35;

	/**
	 * Draws the grid
	 * 
	 * @param g
	 * @param gridWidth
	 * @param gridHeight
	 * @param cellSize
	 */
	public void drawGrid(Graphics g, int gridWidth, int gridHeight, int cellSize) {
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				// condition will result in the last row not being drawn
				if (j < gridHeight - 1) {
					g.setColor(Color.red);
					g.drawRect(xOffset + i * cellSize, yOffset + j * cellSize, cellSize,
							cellSize);
				}
			}// end for
		}// end for
	}
}
