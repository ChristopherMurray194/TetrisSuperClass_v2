import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 * 
 * This class handles the drawing of the introduction screen.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class IntroScreen {
	
	private URL title_url = getClass().getResource("images/TetrisTitle.png");
	private Image title = Toolkit.getDefaultToolkit().createImage(title_url);
	
	// used to set the standard xPosition of the strings
	private int xPos = 70;
	
	// used to set the yPos for the instructions
	private int iYPos = 280;
	
	// used to set the font size for the instructions
	private int fontSize = 16;
	
	/**
	 * Draws the various strings and images to be shown at the intro screen
	 * 
	 * @param g
	 * @param windowWidth
	 * @param windowHeight
	 */
	public void drawIntroScreen(Graphics g, int windowWidth, int windowHeight){
		// clear screen 
		g.clearRect(0, 0, windowWidth, windowHeight);
		
		g.drawImage(title,100,100, 200,200, null);
		g.setFont(new Font("Serif", Font.BOLD, 28));
		g.drawString("Press ENTER to begin.", xPos, 220);
		
		// instructions for game \\
		g.setFont(new Font("Serif", Font.BOLD, fontSize));
		g.drawString("Controls:", xPos, iYPos);
		g.drawString("Left key - Moves shape left", xPos, iYPos+fontSize*2);
		g.drawString("Right key - Moves shape right", xPos, iYPos+fontSize*3);
		g.drawString("Down key - Moves shape down", xPos, iYPos+fontSize*4);
		g.drawString("Up key - Rotates shape", xPos, iYPos+fontSize*5);
		g.drawString("Spacebar - Drops the shape straight down", xPos, iYPos+fontSize*6);
		//-----------------------\\
	}
}
