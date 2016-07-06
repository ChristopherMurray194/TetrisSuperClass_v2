import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * The program is a version of Tetris. A shape descends one row ever mS at the
 * top of the grid. If it reaches the bottom or an occupied space the shape is
 * fixed in position and a new shape is randomly selected and begins its descent
 * from the top of the screen. The shape can be moved left, right and down by
 * the user when the corresponding keys are pressed, these key events happen at
 * a faster rate than the speed of the shape's drop meaning a shape can be moved
 * several places left, right or down before it is automatically dropped. There
 * are seven shapes and each shape can be rotated 90 degrees. If a row is filled
 * with occupied cells it is removed and all rows above it drop down. The score
 * is increased each time the block stops its descent or when a row is removed
 * (when a row is removed more points are awarded).
 * 
 * This main interface class and creates instances of all other classes. This
 * class handles the main functions of the game.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */

public class TetrisStart extends JFrame implements Runnable, KeyListener,
		ChangeListener {

	// ---class instances---\\
	
	Grid gridClass = new Grid();
	UserInterface UIClass = new UserInterface();
	IntroScreen introClass = new IntroScreen();
	GameOverScreen gOScreenClass = new GameOverScreen();

	// ---shape object instances---\\
	Shape shape;
	LShape lShape = new LShape();
	BoxShape boxShape = new BoxShape();
	ReverseLShape reverseLShape = new ReverseLShape();
	LineShape lineShape = new LineShape();
	SittingShape sittingShape = new SittingShape();
	ReverseSittingShape reverseSittingShape = new ReverseSittingShape();
	TShape tShape = new TShape();
	// ----------------------\\

	private boolean leftPressed, rightPressed, downPressed;

	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	// default window width
	final private int windowWidth = 400;
	// default window height
	final private int windowHeight = 600;
	
	MainMenu menu = new MainMenu(windowWidth, windowHeight);

	// ----Sound fields----\\

	private Clip clip;

	// sound files (can not be more than 16 bit)
	
	private URL failed = getClass().getResource("sounds/rotation_failed.wav");
	private URL rowComplete = getClass().getResource("sounds/row_complete.wav");
	private URL rotateSound = getClass().getResource("sounds/dropping.wav");
	private URL landedSound = getClass().getResource("sounds/landed.wav");

	// -----end sound fields----\\

	final private int UIWidth;
	private int UIHeight = 90;

	// --Background fields--\\
	
	private URL icon_url = getClass().getResource("images/TetrisIcon.png");
	private Image icon = toolkit.createImage(icon_url);

	private URL frameBackground_url = getClass().getResource("images/frameBackground.png");
	private Image frameBackground = toolkit
			.createImage(frameBackground_url);
	
	private URL gridBackground_url = getClass().getResource("images/gridBackground.png");
	private Image gridBackground = toolkit
			.createImage(gridBackground_url);

	// --end background fields--\\

	// score to be calculated when shape hit bottom or another shape and when a
	// line is completed
	private int score = 0;

	// when ever a line is completed and removed this value will be increased by
	// one
	private int lineCounter = 0;

	// ---grid fields---\\
	private int gridWidth = 12;
	private int gridHeight = 22;
	private int[][] grid = new int[gridWidth][gridHeight];

	private int cellSize = 20;
	// --end grid fields--\\

	// used to set the color of a shape
	private Color color = null;

	// time elapsed before the shape drops (mS)
	private int speedOfShapeDrop = 500;
	// start time of the system
	private int startTime;
	// current time of the system
	private int currentTime;
	
	//--- create Next shape button icon urls ---\\
	URL nextShape_url;
	URL lShape_url = getClass().getResource("images/Next Shape icon/LShape.png");
	URL reverseLShape_url = getClass().getResource("images/Next Shape icon/reverseLShape.png");
	URL lineShape_url = getClass().getResource("images/Next Shape icon/lineShape.png");
	URL sittingShape_url = getClass().getResource("images/Next Shape icon/sittingShape.png");
	URL reverseSittingShape_url = getClass().getResource("images/Next Shape icon/reverseSittingShape.png");
	URL tShape_url = getClass().getResource("images/Next Shape icon/tShape.png");
	URL boxShape_url = getClass().getResource("images/Next Shape icon/boxShape.png");
	//------------------------------------------\\

	// used to get a random number used to select a shape
	private int nextShape = 0;
	private int currentShape = 0;

	// used to count number of occupied cells in a row
	private int counterCheck = 0;
	// used to count number of rows to remove
	private int foundRow = 0;

	// display introScreen
	private boolean introScreen = true;
	// is the game over or not
	private boolean gameOver = false;
	// is a new shape needed
	private boolean newShapeRequired = false;
	// can a shape rotate
	private boolean canRotate = false;

	// determines whether shape is to be dropped straight down until it
	// collides.
	private boolean autoDrop = false;

	// allows the drop method to run. to be set false when intro screen is shown
	// so that the game cannot be played in the background.
	private boolean allow = false;
	
	private boolean playGame = false;
	private boolean drawMenu = true;

	/**
	 * Constructor
	 */
	public TetrisStart() {
		// title of the window
		setTitle("Tetris");
		// will close when the exit button is pressed at top right of window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(windowWidth, windowHeight);

		UIWidth = getWidth();

		UIClass.getVolumeControl().addChangeListener(this);

		// randomly select a number between 0 and 6 and assign it to random
		// shape
		currentShape = 0 + (int) (Math.random() * ((6 - 0) + 1));
		
		// randomly select the first shape
		randomlySelectShape();

		addKeyListener(this);

		// --set the icon image for the frame--\\
		setIconImage(icon);
		// ------------------------------------\\

		// Set up the content pane.
		addComponentsToPane(getContentPane());

		// wont appear in top left of screen, as default appears in center of
		// screen when parameter is null
		setLocationRelativeTo(null);

		// cannot be resized whilst running
		setResizable(false);
		// Ensures focus is on the window when all components are
		// setFocusable(false)
		// If slider is moved it or buttons pressed does not affect the
		// keyListeners
		setFocusable(true);
		// sets the window to visible
		setVisible(true);
		createBufferStrategy(2);
	}

	// end Constructor

	/**
	 * Handles key pressed key events.
	 */
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			// if left is pressed shape is moved left
			leftPressed = true;
			moveLeft();
		}
		if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			// if right is pressed shape is moved right
			rightPressed = true;
			moveRight();
		}
		if (key.getKeyCode() == KeyEvent.VK_DOWN) {
			// if down is pressed shape is moved down
			downPressed = true;
			moveDown();
		}
		if (key.getKeyCode() == KeyEvent.VK_UP) {
			// if up key is pressed the current shape will rotate
			if (allow) {
				playSound(rotateSound);
				rotate();
			}
		}
		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			// if space is pressed the shape will automatically drop until it
			// collides
			autoDrop = true;
		}
		if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			// if enter is pressed the intro screen is no longer displayed
			introScreen = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// if escape is pressed game will exit
			System.exit(0);
		}
	}

	/**
	 * Handles key released key events.
	 */
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = false;
		if (key.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPressed = false;
		if (key.getKeyCode() == KeyEvent.VK_DOWN)
			downPressed = false;
	}

	/**
	 * Handles key typed key events. However is not being used currently.
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * redraws the objects every millisecond
	 */
	public void run() {
		// when the application first starts up, draw the main menu
		// TODO - put menu on own Thread. 
		if(drawMenu){
			// draw menu
			drawMenu();
		}
		while (true) {
			// show the UI
			UIClass.getUI().setVisible(true);
			
			pause();
			unPause();
			changeGridButtonString();
			autoDrop();
			// runs the drawObjects function
			drawInterface();
			// handles the end game conditions
			endState();
			// display game over screen
			gameOverScreen();
			System.out.println(currentShape + " " + nextShape);
		}
	}

	/**
	 * Pauses the game.
	 */
	private synchronized void pause() {
		// while paused
		while (UIClass.getPause()) {
			// set the pause button's text
			UIClass.getPauseButton().setText("Unpause");
			try {
				// stops the thread (if argument is 0 the thread will stop until
				// another thread calls notify() or notifyAll())
				this.wait(1);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Resumes game
	 */
	private synchronized void unPause() {
		// if unpaused
		if (!UIClass.getPause()) {
			// set the pause button's text
			UIClass.getPauseButton().setText("Pause");
			// wakes up all threads which are waiting.
			this.notifyAll();
		}
	}

	/**
	 * Changes the text of the griVisibility JButton
	 */
	private void changeGridButtonString() {
		// if the grid is not displayed
		if (!UIClass.getShowGrid()) {
			// set the grid button's text
			UIClass.getGridVisibility().setText("Show Grid");
		}
		// if the grid is displayed
		if (UIClass.getShowGrid()) {
			// set the grid button's text
			UIClass.getGridVisibility().setText("Hide Grid");
		}
	}

	/**
	 * changes the button Icon based upon the volumeValue
	 * 
	 */
	private void changeVolumeButtonIcon() {
		// if the volume value is at the highest volume value or less than it
		if (UIClass.getVolumeValue() <= UIClass.getMaxVolumeValue()) {
			// set the volume button's icon
			UIClass.getVolumeButton().setIcon(UIClass.getFullVolumeIcon());
		}
		// if not muted and volume is less than -10 and not the lowest value on
		// the slider
		if (!UIClass.getMute() && UIClass.getVolumeValue() <= -10
				&& UIClass.getVolumeValue() != UIClass.getMinVolumeValue()) {
			// set the volume button's icon
			UIClass.getVolumeButton().setIcon(UIClass.getLowVolumeIcon());
		}
		// if the volume is at the lowest value on the slider
		if (UIClass.getVolumeValue() == UIClass.getMinVolumeValue()) {
			// set the volume button's icon
			UIClass.getVolumeButton().setIcon(UIClass.getNoVolumeIcon());
		}

		// if muted
		if (UIClass.getMute()) {
			// change the volume button icon
			UIClass.getVolumeButton().setIcon(UIClass.getMuteVolumeIcon());
		}
	}

	/**
	 * Adds any UI components to the container
	 * 
	 * @param pane
	 */
	public void addComponentsToPane(Container pane) {

		// when the menu needs to be drawn
		if(drawMenu){
			// add menu components to pane
			menu.addComponentsToPane(pane);
		}
		
		UIClass.createGridVisibilityButton();

		UIClass.createPauseButton();

		UIClass.createVolumeButton();

		UIClass.createVolumeSlider();

		UIClass.createPanel(UIWidth, UIHeight);

		UIClass.createUIPanel(UIWidth, UIHeight);

		// add UI panel to container and place at the bottom
		pane.add(UIClass.getUI(), BorderLayout.SOUTH);
		// hide the User interface to begin with
		UIClass.getUI().setVisible(false);
		
		pane.setVisible(true);
		pane.setFocusable(false);
	}

	/**
	 * Handles the changes to the volume slider
	 * 
	 * @param e
	 */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		// if slider is not being adjusted
		if (!source.getValueIsAdjusting()) {
			// assign the slider's value to volVal
			int volVal = (int) source.getValue();
			// assign volVal to volume value
			UIClass.setVolumeValue(volVal);
		}
	}

	/**
	 * Will play an audio file passed as the parameter. If an audio file using
	 * this method is already playing, it will be stopped and closed so that the
	 * new sound can be played instead and memory is not used.
	 * 
	 * @param file
	 */
	private void playSound(URL file) {
		try {
			// obtain audio file
			clip = AudioSystem.getClip();
			// open the audio file
			clip.open(AudioSystem.getAudioInputStream(file));
			// take control of the volume
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			// set the volume value
			gainControl.setValue((float) UIClass.getVolumeValue());

			// stop the currently playing clip
			clip.stop();

			clip.addLineListener(new LineListener() {
				public void update(LineEvent event) {
					// if the stop event has been called
					if (event.getType() == LineEvent.Type.STOP) {
						// close the clip
						event.getLine().close();
					}
				}
			});
			// restart at the beginning of clip
			clip.setFramePosition(0);
			// begin playback
			clip.start();

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * When there is no more room to add a shape, the game is over
	 */
	private void endState() {
		for (int i = 0; i < gridWidth; i++) {
			// if the first cell is occupied, meaning there will be no available
			// space for a new shape.
			if (grid[i][0] >= 2 && grid[i][0] <= 8)
				// the game is over
				gameOver = true;
		}
	}
	
	/**
	 * draw the main menu
	 */
	private void drawMenu() {
		do {
			BufferStrategy bf = getBufferStrategy();
			Graphics g = bf.getDrawGraphics();
			// first clear the screen
			g.clearRect(0, 0, getWidth(), getHeight());
			
			// set playGame to the value of playGame in the MainMenu class. So if the continue button is pressed playGame becomes true 
			setPlayGame(menu.getPlayGame());
	
			// set size of window to changed values
			this.setSize(windowWidth, windowHeight);
			
			// if the game should begin
			if (playGame) {
				// hide the menu 
				drawMenu = false;
			}

			// draw components
			super.paintComponents(g);

			// draws on top of components
			menu.drawMenu(g);
			// draws credits when they need to be drawn
			menu.drawCredits(g);
			
			g.dispose();
			bf.show();
			Toolkit.getDefaultToolkit();
		} while (drawMenu);
	}
	
	/**
	 * Mutator method for playGame
	 * @param playGame
	 */
	private void setPlayGame(boolean playGame){
		this.playGame = playGame;
	}

	/**
	 * draws any graphics onto the window
	 */
	private void drawInterface() {
		if(playGame){
			BufferStrategy bf = getBufferStrategy();
			Graphics g = bf.getDrawGraphics();
		
			// while the game is not over
			if (!gameOver && !drawMenu) {
				// first clear the screen
				g.clearRect(0, 0, windowWidth, windowHeight);
		
				// allow the shape to drop
				allow = true;
		
				// needs to be called here, if put in the run method, the
				// volumeButton will always be drawn even when the intro and game
				// over screen are shown
				changeVolumeButtonIcon();
				// paints all components
				super.paintComponents(g);
		
				// draw any backgrounds
				g.drawImage(
						frameBackground,
						0,
						0,
						windowWidth,
						(windowHeight - UIHeight) - UIClass.getUIBorderWeight() * 2,
						null);
				g.drawImage(gridBackground, 12, 35, gridWidth * cellSize + 1,
						(gridHeight * cellSize + 1) - cellSize, null);
				//
		
				int fontSize = 20;
				int stringX = (windowWidth - 110) - fontSize;
				int stringY = 150;
				g.setFont(new Font("Arial", Font.BOLD, fontSize));
				g.setColor(Color.red);
				g.drawString("Score: " + score, stringX, stringY);
				g.drawString("Lines: " + lineCounter, stringX, stringY + 33);
		
				// if grid needs to be shown
				if (UIClass.getShowGrid()) {
					// draw the grid
					gridClass.drawGrid(g, gridWidth, gridHeight, cellSize);
				}
		
				// method calls the main tasks to be carried out by the game
				mainTasks();
		
				// draws the selected shape
				shape.drawShape(g, gridWidth, gridHeight, grid, cellSize, color);
			}
			//
		
			g.dispose();
			bf.show();
			Toolkit.getDefaultToolkit();
		}
	}

	/**
	 * Display game over screen.
	 * 
	 */
	private void gameOverScreen() {

		BufferStrategy bf = getBufferStrategy();
		Graphics g = bf.getDrawGraphics();

		// if the game is over clear the window and redraw the end game screen
		if (gameOver) {
			setBackground(Color.lightGray);
			// draws the game over screen
			gOScreenClass.createGameOverScreen(g, windowWidth, windowHeight,
					score, lineCounter);
		}

		g.dispose();
		bf.show();
		Toolkit.getDefaultToolkit();
	}

	/**
	 * Randomly selects a shape
	 */
	private void randomlySelectShape() {

		// randomly select a number between 0 and 6
		nextShape = 0 + (int) (Math.random() * ((6 - 0) + 1));

		// draw the shape (determined by the value of currentShape) in its initial
		// position
		switch (currentShape) {
		case 1:
			lShape.initialShape(gridWidth);
			color = Color.orange;
			shape = lShape;
			break;
		case 2:
			reverseLShape.initialShape(gridWidth);
			color = Color.red;
			shape = reverseLShape;
			break;
		case 3:
			lineShape.initialShape(gridWidth);
			color = Color.magenta;
			shape = lineShape;
			break;
		case 4:
			sittingShape.initialShape(gridWidth);
			color = Color.cyan;
			shape = sittingShape;
			break;
		case 5:
			reverseSittingShape.initialShape(gridWidth);
			color = Color.green;
			shape = reverseSittingShape;
			break;
		case 6:
			tShape.initialShape(gridWidth);
			color = Color.pink;
			shape = tShape;
			break;
		default:
			boxShape.initialShape(gridWidth);
			color = Color.yellow;
			shape = boxShape;
			break;
		}
		// start the timer
		startTime = (int) System.currentTimeMillis();
	}

	/**
	 * Calls the rotation method from the shape superclass. Catches
	 * ArrayIndexOutOfBoundsException when the rotation will go off the
	 * boundaries of the grid.
	 */
	private void rotate() {
		try {
			// rotate shape
			shape.rotateShape(grid);
			canRotate = shape.getCanRotate();
			// if shape cannot rotate
			if (!canRotate) {
				// play rotationFail sound
				playSound(failed);
			}
			// catches the ArrayIndexOutOfBoundsException error, if this error
			// occurs the rotation fail sound is played.
		} catch (ArrayIndexOutOfBoundsException e) {
			// play rotationFail sound
			playSound(failed);
		}
	}

	/**
	 * Calls the main game functions
	 */
	private void mainTasks() {
		// allows main game functions to happen
		if (allow) {
			newShapeRequired();
			dropShapeTimed();
		}
	}

	/**
	 * If a new shape is needed a randomly selected shape will be created and
	 * timer will begin
	 * 
	 */
	private void newShapeRequired() {
		if (newShapeRequired == true) {
			// autoDrop set to false here so that the shapes do not continue to
			// drop when a new shape is created.
			autoDrop = false;
			// play landedSound
			playSound(landedSound);
			// the current shape is now the next shape shown
			currentShape = nextShape;
			// calls randomlySelectShape method to randomly select what will be the next shape
			randomlySelectShape();
			// if a new shape is required the shape has either hit the bottom or
			// collided meaning the score needs to be increased
			score += 5;
			// reset newShapeRequired
			newShapeRequired = false;
		}// end if
	}

	/**
	 * Will check whether a shape can move left if it can shape is allowed to
	 * move left
	 * 
	 */
	private void moveLeft() {
		// if a shape is not off the left side of the grid
		if (shape.getShapeX(0) > 0 && shape.getShapeX(1) > 0
				&& shape.getShapeX(2) > 0 && shape.getShapeX(3) > 0) {
			// check the left collision with an occupied space and assign
			// leftPressed to the value returned
			leftPressed = shape.checkCollisionLeft(grid);
			if (leftPressed)
				// move the shape left
				shape.moveShapeLeft(grid);
		}// end if

		// if cannot move left
		if (!leftPressed) {
			// play the rotationFail sound
			playSound(failed);
		}
	}

	/**
	 * Will check whether a shape can move right if it can shape is allowed to
	 * move right
	 * 
	 */
	private void moveRight() {
		// if a shape is not off the right side of the grid
		if (shape.getShapeX(0) < gridWidth - 1
				&& shape.getShapeX(1) < gridWidth - 1
				&& shape.getShapeX(2) < gridWidth - 1
				&& shape.getShapeX(3) < gridWidth - 1) {
			// check the right collision with an occupied space and assign
			// rightPressed to the value returned
			rightPressed = shape.checkCollisionRight(grid);
			if (rightPressed)
				// move the shape right
				shape.moveShapeRight(grid);
		}// end if

		// if cannot move left
		if (!rightPressed) {
			// play the rotationFail sound
			playSound(failed);
		}
	}

	/**
	 * Will check whether a shape can move down, if it can shape is allowed to be
	 * moved down.
	 */
	private void moveDown() {
		// if shape is allowed to move down
		if (allow) {
			// if the shape is not at the bottom
			if (shape.getShapeY(0) < gridHeight - 2
					&& shape.getShapeY(1) < gridHeight - 2
					&& shape.getShapeY(2) < gridHeight - 2
					&& shape.getShapeY(3) < gridHeight - 2) {
				// if down is pressed
				if (downPressed)
					// check the shape can drop, if it can, drop it.
					shape.checkShapeDrop(grid);
			}
		}
	}

	/**
	 * If autoDrop is true the shape will drop straight down until it collides
	 * with the bottom of the grid or an occupied space
	 */
	private void autoDrop() {
		// if shape is allowed to drop and shape needs to drop straight down
		if (autoDrop && allow) {
			// if the shape is not at the bottom
			if (shape.getShapeY(0) < gridHeight - 2
					&& shape.getShapeY(1) < gridHeight - 2
					&& shape.getShapeY(2) < gridHeight - 2
					&& shape.getShapeY(3) < gridHeight - 2) {
				// check the shape can drop, if it can, drop it.
				shape.checkShapeDrop(grid);
			}
		}
	}

	/**
	 * Will check for and remove rows that are filled
	 * 
	 * @param grid
	 * @param gridWidth
	 * @param gridHeight
	 */
	private void removeRow() {
		for (int j = 0; j < gridHeight; j++) {
			// reset counterCheck
			counterCheck = 0;
			for (int i = 0; i < gridWidth; i++) {
				// if a cell is occupied
				if (grid[i][j] >= 2 && grid[i][j] <= 8) {
					// counterCheck increases by one
					counterCheck++;
				}
			}
			// If counterCheck equals gridWidth a row is complete
			if (counterCheck == gridWidth) {
				for (int i = 0; i < gridWidth; i++) {
					// get the row to be removed
					foundRow = j;
					// remove row
					grid[i][j] = 0;
					// play rowComplete sound
					playSound(rowComplete);
					// increase score(more points for getting a
					// complete row).
					score += 10;
				} // end for

				// lineCounter increases by one each time a row is removed
				lineCounter++;
				// call drop down method
				dropDownRow();
			}
		}
	}

	/**
	 * Will drop down the rows above the removed row
	 * 
	 */
	private void dropDownRow() {
		for (int i = 0; i < gridWidth; i++) {
			// loops back to 0 from the removed row
			for (int j = foundRow; j >= 0; j--) {
				if (j < gridHeight - 2) {
					// if a block is occupied
					if (grid[i][j] >= 2 && grid[i][j] <= 8){
						// remove all current occupied blocks above the removed
						// one 
						// TODO - optimise if statements!
						if(grid[i][j] == 2){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 2;
						}
						if(grid[i][j] == 3){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 3;
						}
						if(grid[i][j] == 4){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 4;
						}
						if(grid[i][j] == 5){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 5;
						}
						if(grid[i][j] == 6){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 6;
						}
						if(grid[i][j] == 7){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 7;
						}
						if(grid[i][j] == 8){
							grid[i][j] = 0;
							// re-draw the blocks plus one vertical position
							grid[i][j + 1] = 8;
						}
					}
				}
			} // end inner for
		} // end outer for
	}

	/**
	 * Handles the dropping of a shape
	 */
	private void dropShapeTimed() {
		// Will change the value of a grid cell occupied by a shape to 1
		shape.setShapeValue(grid, 1);
		// --------\\

		// Get the current time
		currentTime = (int) System.currentTimeMillis();
		// If speedOfShapeDrop(500 mS) has elapsed then the shape needs
		// to be dropped
		if (startTime + speedOfShapeDrop <= currentTime) {
			// if shape is not at the bottom
			if (shape.getShapeY(0) < gridHeight - 2
					&& shape.getShapeY(1) < gridHeight - 2
					&& shape.getShapeY(2) < gridHeight - 2
					&& shape.getShapeY(3) < gridHeight - 2) {
				// checks whether the cell below is not occupied if it is not,
				// the shape will drop
				shape.checkShapeDrop(grid);
				// reset the start time
				startTime = (int) System.currentTimeMillis();
			}

			// check whether the shape has collided and assign newShapeRequired
			// to the returned value
			newShapeRequired = shape.checkCollision(grid, currentShape);

			// if shape is at the bottom
			if (shape.getShapeY(0) == gridHeight - 2
					|| shape.getShapeY(1) == gridHeight - 2
					|| shape.getShapeY(2) == gridHeight - 2
					|| shape.getShapeY(3) == gridHeight - 2) {
				// Will set the value of a grid cell occupied by a shape > 2
				// fixing
				// the shape in place
				shape.setShapeValue(grid, currentShape + 1);
				if(currentShape == 0){
					shape.setShapeValue(grid, 8);
				}
				newShapeRequired = true;
			}
			
			// Will remove row when row is complete and drop the rows above
			// down. 
			// Called here so that a row is only removed once a shape has set in its place and a new shape has been created. 
			removeRow();
		}
	}

	public static void main(String[] args) {
		TetrisStart window = new TetrisStart();
		// creates a new Thread of window
		Thread runner = new Thread(window);
		// starts the thread
		runner.start();
	}
}
