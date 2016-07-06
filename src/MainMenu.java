import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;


public class MainMenu {
	// The window size \\
	private int windowWidth, windowHeight;
	// ------------\\

	// the default button size \\
	private int buttonWidth = 100;
	private int buttonHeight = 30;
	// -------------------------\\

	// initially show the title
	private boolean showTitle = true;
	// initial title string
	private String title = "AD XLIII";

	// maximum value allowed by the system is 6.0206
	private final int maxVolumeValue = 12;
	// minimum value allowed by the system is -80.0
	private final int minVolumeValue = -30;
	// intial volume value
	private int volumeValue = -20;

	// size of the JSlider \\
	private final int soundSliderWidth = 200;
	private final int soundSliderHeight = 50;
	// ---------------------\\

	// spacing of the JSlider ticks
	private final int extent = 6;

	// -----end sound fields----\\

	// volume controls slider
	private final JSlider volumeControl = new JSlider(minVolumeValue,
			maxVolumeValue, volumeValue);

	// Create the main menu buttons \\
	private JButton play = new JButton("Play Game");
	private JButton controls = new JButton("Controls");
	private JButton options = new JButton("Options");
	private JButton credits = new JButton("Credits");
	private JButton quit = new JButton("Quit Game");
	// --------------------\\

	// JLabel to draw the background to
	private JLabel background = new JLabel();

	// background image
	private ImageIcon menuBackground = new ImageIcon(
			" "); //TODO - get background image for menu

	// create back button
	private JButton back = new JButton("Back");

	// controls interface background image. Shows the controls that are used
	private ImageIcon controlsImage = new ImageIcon(
			" "); // TODO - get background image for controls section

	// determines whether the game should be played
	private boolean playGame = false;
	// determines whether the credits should be shown
	private boolean showCredits = false;

	// x position of the buttons
	private int x = 10;
	// y position of the buttons
	private int y = 150;

	// gap between the buttons
	private int gap = 20;
	
	public MainMenu(int windowWidth, int windowHeight){
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}

	/**
	 * Creates options interface components.
	 * 
	 * Creates the volume slider and sets its characteristics.
	 * 
	 */
	private void optionsComponents() {
		// ---Creates volume Control slider---\\
		volumeControl.setOrientation(JSlider.HORIZONTAL);
		volumeControl.setBounds(20, 100, soundSliderWidth, soundSliderHeight);

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();

		JLabel volumeLabel = new JLabel("-");
		labels.put(volumeControl.getMinimum(), volumeLabel);
		labels.put(volumeControl.getMaximum(), new JLabel("+"));
		labels.put(-10, new JLabel("VolumeControl"));

		volumeControl.setLabelTable(labels);
		// increment the slider can be moved at (currently 6)
		volumeControl.setExtent(extent);
		volumeControl.setMajorTickSpacing(0);
		volumeControl.setMinorTickSpacing(extent);
		volumeControl.setPaintTicks(true);
		volumeControl.setSnapToTicks(true);
		volumeControl.setPaintLabels(true);
		volumeControl.setBackground(null);
		volumeControl.setOpaque(false);
		// When changes are made to the slider it will not stop key press events
		// being picked up by the main frame.
		// As a result of this the slider will not pickup any key presses and
		// can only be adjusted via the cursor.
		volumeControl.setFocusable(false);
		volumeControl.setEnabled(false);
		volumeControl.setVisible(false);
		// -----------------------------------\\
	}

	/**
	 * Adds components to pane to be added to JFrame
	 * 
	 * @param pane
	 */
	public void addComponentsToPane(Container pane) {

		// call the options components otherwise throws NullPointerException
		optionsComponents();

		play.setBounds(x, y, buttonWidth, buttonHeight);
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == play) {
					// enable game to be played
					playGame = true;
					// ---disable buttons so they cannot be mouse over---\\
					play.setEnabled(false);
					controls.setEnabled(false);
					options.setEnabled(false);
					credits.setEnabled(false);
					quit.setEnabled(false);
					// ----------------------------------------------------\\
				}
			}
		});

		controls.setBounds(x, ((y + buttonHeight) + gap), buttonWidth,
				buttonHeight);
		controls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == controls) {

					// hide title
					showTitle = false;

					// --disable buttons--\\
					play.setEnabled(false);
					controls.setEnabled(false);
					options.setEnabled(false);
					credits.setEnabled(false);
					quit.setEnabled(false);
					// --------------------\\

					// --hide buttons--\\
					play.setVisible(false);
					controls.setVisible(false);
					options.setVisible(false);
					credits.setVisible(false);
					quit.setVisible(false);
					// ------------------\\

					// change the background image
					background.setIcon(controlsImage);

					// enable and show the back button
					back.setEnabled(true);
					back.setVisible(true);
				}
			}
		});

		options.setBounds(x, ((y + (buttonHeight << 1)) + (gap << 1)),
				buttonWidth, buttonHeight);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == options) {

					// change the title string
					title = "Options";

					// --disable buttons--\\
					play.setEnabled(false);
					controls.setEnabled(false);
					options.setEnabled(false);
					credits.setEnabled(false);
					quit.setEnabled(false);
					// --------------------\\

					// --hide buttons--\\
					play.setVisible(false);
					controls.setVisible(false);
					options.setVisible(false);
					credits.setVisible(false);
					quit.setVisible(false);
					// ------------------\\

					// change the background image
					background.setIcon(menuBackground);

					// enable and show the volume slider
					volumeControl.setEnabled(true);
					volumeControl.setVisible(true);

					// enable and show the back button
					back.setEnabled(true);
					back.setVisible(true);
				}
			}
		});

		credits.setBounds(x, ((y + buttonHeight * 3) + (gap * 3)), buttonWidth,
				buttonHeight);
		credits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == credits) {

					// show credit strings
					showCredits = true;

					// change title string
					title = "Credits";

					// ---enable buttons---\\
					play.setEnabled(false);
					controls.setEnabled(false);
					options.setEnabled(false);
					credits.setEnabled(false);
					quit.setEnabled(false);
					// ---------------------\\

					// ---hide buttons---\\
					play.setVisible(false);
					controls.setVisible(false);
					options.setVisible(false);
					credits.setVisible(false);
					quit.setVisible(false);
					// --------------------\\

					// change background image
					background.setIcon(menuBackground);

					// enable and show the back button
					back.setEnabled(true);
					back.setVisible(true);
				}
			}
		});

		quit.setBounds(x, ((y + (buttonHeight << 2)) + (gap << 2)),
				buttonWidth, buttonHeight);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == quit) {
					// terminate application
					System.exit(0);
				}
			}
		});

		back.setBounds(windowWidth - buttonWidth, windowHeight - (buttonHeight * 2), buttonWidth, buttonHeight);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == back) {
					// enable drawing of the title
					showTitle = true;
					// set title string
					title = "AD XLIII";

					// hide credits in case they are currently shown
					showCredits = false;

					// ---enable buttons---\\
					play.setEnabled(true);
					controls.setEnabled(true);
					options.setEnabled(true);
					credits.setEnabled(true);
					quit.setEnabled(true);
					// -----------------------\\

					// ---show buttons---\\
					play.setVisible(true);
					controls.setVisible(true);
					options.setVisible(true);
					credits.setVisible(true);
					quit.setVisible(true);
					// ------------------\\

					// --disable volume slider in case it is currently enabled
					volumeControl.setEnabled(false);
					volumeControl.setVisible(false);

					// set menu background image as background
					background.setIcon(menuBackground);

					// disable and hide the back button
					back.setEnabled(false);
					back.setVisible(false);
				}
			}
		});

		// set buttons to non-focusable so they do not affect any other events
		play.setFocusable(false);
		controls.setFocusable(false);
		options.setFocusable(false);
		credits.setFocusable(false);
		quit.setFocusable(false);
		// --------------------------------------------------------------------\\

		// size of window
		background.setBounds(0, 0, windowWidth, windowHeight);
		background.setFocusable(false);
		// draw image as background
		background.setIcon(menuBackground);
		background.setVisible(true);

		// enable buttons
		play.setEnabled(true);
		controls.setEnabled(true);
		options.setEnabled(true);
		credits.setEnabled(true);
		quit.setEnabled(true);
		// ---------------------\\

		// show buttons
		play.setVisible(true);
		controls.setVisible(true);
		options.setVisible(true);
		credits.setVisible(true);
		quit.setVisible(true);
		// ---------------\\

		// set back button to non-focusable, not enabled and not visible so it
		// is not shown unless told otherwise
		back.setFocusable(false);
		back.setEnabled(false);
		back.setVisible(false);

		// add components to the pane(container)
		pane.add(play);
		pane.add(controls);
		pane.add(options);
		pane.add(credits);
		pane.add(quit);
		pane.add(back);

		// sound interface unique components
		pane.add(volumeControl);
		// ---------------------------------\\

		pane.add(background);

		pane.setVisible(true);
		pane.setFocusable(false);
		
		pane.setBackground(Color.red);
	}

	/**
	 * Draws the title
	 * 
	 * @param g
	 */
	public void drawMenu(Graphics g) {
		int titleFontSize = 28;

		// draw the title
		if (showTitle) {
			g.setFont(new Font("Courier New", Font.BOLD, titleFontSize));
			g.drawString(title, ((windowWidth >> 1) - 60), 50);
		}
	}

	/**
	 * Draws the credits strings
	 * 
	 * @param g
	 */
	public void drawCredits(Graphics g) {
		int titleFontSize = 14;

		int x = (windowWidth >> 1) - 50;
		int y = 170;

		int gap = 20;

		// draw the credits
		if (showCredits) {
			g.setFont(new Font("Courier New", Font.BOLD, titleFontSize));
			g.drawString("Programming", x + 10, y);
			g.drawString("Chris Murray", x + 5, y + gap);
			g.drawString("Art and Design", x, y + 50);
			g.drawString("Chris Murray", x + 5, (y + 50)
					+ gap);
			g.drawString("Music", x + 35, y + 100);
			g.drawString("Third Party", x + 5, (y + 100) + gap);
		}
	}

	/**
	 * Accessor for JSlider volume control
	 * 
	 * @return
	 */
	public JSlider getVolumeControl() {
		return volumeControl;
	}

	/**
	 * Accessor for volumeValue
	 * 
	 * @return
	 */
	public int getVolumeValue() {
		return volumeValue;
	}

	/**
	 * Mutator for volumeValue
	 * 
	 * @param volumeValue
	 */
	public void setVolumeValue(int volumeValue) {
		this.volumeValue = volumeValue;
	}

	/**
	 * Acccessor for play button
	 * 
	 * @return
	 */
	public JButton getPlayButton() {
		return play;
	}

	/**
	 * Accessor for controls button
	 * 
	 * @return
	 */
	public JButton getControlsButton() {
		return controls;
	}

	/**
	 * Accessor for options button
	 * 
	 * @return
	 */
	public JButton getOptionsButton() {
		return options;
	}

	/**
	 * Accessor for credits button
	 * 
	 * @return
	 */
	public JButton getCreditsButton() {
		return credits;
	}

	/**
	 * Accessor for quit button
	 * 
	 * @return
	 */
	public JButton getQuitButton() {
		return quit;
	}

	/**
	 * Acccessor for back button
	 * 
	 * @return
	 */
	public JButton getBackButton() {
		return back;
	}

	/**
	 * Mutator for show title
	 * 
	 * @param showTitle
	 */
	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	/**
	 * Mutator for show credits
	 * 
	 * @param showCredits
	 */
	public void setShowCredits(boolean showCredits) {
		this.showCredits = showCredits;
	}

	/**
	 * Accessor for play game
	 * 
	 * @return
	 */
	public boolean getPlayGame() {
		return playGame;
	}

	/**
	 * Accessor for show credits
	 * 
	 * @return
	 */
	public boolean getShowCredits() {
		return showCredits;
	}

	/**
	 * Accessor for windowWidth
	 * 
	 * @return
	 */
	public int getWindowWidth() {
		return windowWidth;
	}

	/**
	 * Accessor for windowHeight
	 * 
	 * @return
	 */
	public int getWindowHeight() {
		return windowHeight;
	}

	/**
	 * Accessor for buttonWidth
	 * 
	 * @return
	 */
	public int getButtonWidth() {
		return buttonWidth;
	}

	/**
	 * Accessor for buttonHeight
	 * 
	 * @return
	 */
	public int getButtonHeight() {
		return buttonHeight;
	}
}
