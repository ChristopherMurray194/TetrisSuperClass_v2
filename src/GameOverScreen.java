import java.awt.Font;
import java.awt.Graphics;

/**
 * 
 * This class' sole purpose is to create the screen to be displayed at the end game situation. 
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class GameOverScreen {

	// font size
	private int fontSize = 28;
	// standard x position for strings 
	private int xPos = 100; 
	//initial y position for strings
	private int yPos = 100;
	
	/**
	 * creates the game over screen to be shown when the game is over
	 * 
	 * @param g
	 * @param windowWidth
	 * @param windowHeight
	 * @param score
	 * @param lineCounter
	 */
	public void createGameOverScreen(Graphics g, int windowWidth, int windowHeight, int score, int lineCounter){
		
		// clear screen
		g.clearRect(0, 0, windowWidth, windowHeight);
		
		String gameOver = "GAME OVER";
		String escInstr = "Press Esc to quit";

		g.setFont(new Font("Serif", Font.BOLD, fontSize));
		g.drawString(gameOver, xPos, yPos);
		g.drawString(escInstr, xPos, yPos+fontSize);
		g.drawString("Score: " + score, xPos, yPos+fontSize*2);
		g.drawString("Lines: " + lineCounter, xPos, yPos+fontSize*3);
	}
}
