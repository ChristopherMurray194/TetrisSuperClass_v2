import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * 
 * This class handles creation of the User interface components.
 * 
 * @author Christopher Murray
 * @version 1.0 (April 2014)
 */
public class UserInterface {

	// used to determine whether the volume is muted
	private boolean mute = false;
	// used to determine whether to show the grid or not
	private boolean showGrid = true;
	// used to determine whether to pause the game or not
	private boolean pause = false;

	// ------Component fields------\\

	// maximum value allowed by the system is 6.0206
	private final int maxVolumeValue = 6;
	// minimum value allowed by the system is -80.0
	private final int minVolumeValue = -66;
	// intial volume value
	private int volumeValue = -20;

	private int gridButtonCount = 0;
	private JButton gridVisibility = new JButton();
	private JButton pauseButton = new JButton();

	// -----end sound fields----\\

	private final JSlider volumeControl = new JSlider(minVolumeValue, maxVolumeValue,
			volumeValue);

	private final int soundSliderWidth = 200;
	private final int soundSliderHeight = 50;

	private final int extent = 6;

	private JPanel UI;

	private final int UIBorderWeight = 2;
	// ---end component fields---\\

	private URL fullVolume_url = getClass().getResource("images/fullVolume.png"); 
	private ImageIcon fullVolume = new ImageIcon(fullVolume_url);
	
	private URL lowVolume_url = getClass().getResource("images/lowVolume.png");
	private ImageIcon lowVolume = new ImageIcon(lowVolume_url);
	
	private URL noVolume_url = getClass().getResource("images/noVolume.png");
	private ImageIcon noVolume = new ImageIcon(noVolume_url);
	
	private URL muteVolume_url = getClass().getResource("images/muteVolume.png");
	private ImageIcon muteVolume = new ImageIcon(muteVolume_url);

	private JButton volumeButton = new JButton(fullVolume);
	private JPanel panel;

	/**
	 * Creates the show grid button and sets its characteristics and behaviours
	 * 
	 */
	public void createGridVisibilityButton() {
		gridVisibility.setText("Hide Grid");
		gridVisibility.setFont(new Font("Serif", Font.BOLD, 10));
		gridVisibility.setPreferredSize(new Dimension(100, 15));
		gridVisibility.setBackground(null);
		gridVisibility.setFocusable(false);
		gridVisibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if button has been pressed and shows show grid string (grid
				// is currently visible)
				if (event.getSource() == gridVisibility
						&& gridVisibility.getText().equals("Hide Grid")) {
					// hide grid
					showGrid = false;
				}
				// if button has been pressed and shows hide grid string (grid
				// is currently hidden)
				if (event.getSource() == gridVisibility
						&& gridVisibility.getText().equals("Show Grid")) {
					// show grid
					showGrid = true;
				}
			}
		});

	}

	/**
	 * Creates the pause button and sets its characteristics and behaviours
	 * 
	 */
	public void createPauseButton() {
		// ----pause button---\\
		pauseButton.setText("Pause");
		pauseButton.setFont(new Font("Serif", Font.BOLD, 10));
		pauseButton.setPreferredSize(new Dimension(100, 15));
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == pauseButton
						&& pauseButton.getText().equals("Pause")) {
					pause = true;
				}
				if (event.getSource() == pauseButton
						&& pauseButton.getText().equals("Unpause")) {
					pause = false;
				}
			}
		});
		pauseButton.setFocusable(false);
		pauseButton.setVisible(true);
		// -------------------\\
	}

	/**
	 * Creates the volume button and sets its characteristics and behaviours
	 * 
	 */
	public void createVolumeButton() {
		// ---volume button----\\
		volumeButton.setPreferredSize(new Dimension(24, 24));
		volumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if button is pressed mute sound
				if (event.getSource() == volumeButton && !mute) {
					mute = true;
					// set volumeValue to lowest value the system allows (if the
					// set value is smaller than the minimum value allowed, the
					// value that is set will be ignored).
					volumeValue = -80;
				} else {
					mute = false;
					// set volumeValue to mid value
					volumeValue = -10;
				}
			}
		});
		volumeButton.setFocusable(false);
		volumeButton.setVisible(true);
		// -------------------------\\
	}

	/**
	 * Creates the volume slider and sets its characteristics
	 * 
	 */
	public void createVolumeSlider() {
		// ---Creates volume Control slider---\\
		volumeControl.setOrientation(JSlider.HORIZONTAL);
		volumeControl.setPreferredSize(new Dimension(soundSliderWidth,
				soundSliderHeight));

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();

		JLabel volumeLabel = new JLabel(fullVolume);
		labels.put(volumeControl.getMinimum(), volumeLabel);
		labels.put(volumeControl.getMaximum(), new JLabel("+"));
		labels.put(-28, new JLabel("VolumeControl"));

		volumeControl.setLabelTable(labels);
		// increment the slider can be moved at (currently 6)
		volumeControl.setExtent(extent);
		volumeControl.setMajorTickSpacing(0);
		volumeControl.setMinorTickSpacing(extent);
		volumeControl.setPaintTicks(true);
		volumeControl.setSnapToTicks(true);
		volumeControl.setPaintLabels(true);
		volumeControl.setBackground(null);
		// When changes are made to the slider it will not stop key press events
		// being picked up by the main frame.
		// As a result of this the slider will not pickup any key presses and
		// can only be adjusted via the cursor.
		volumeControl.setFocusable(false);
		volumeControl.setVisible(true);
		// -----------------------------------\\
	}

	/**
	 * Creates the a panel to hold the UI components, the panel is then added to
	 * the container and sets its characteristics
	 * 
	 */
	public void createPanel(int UIWidth, int UIHeight) {

		// Create the UI panel and its components.
		panel = new JPanel();

		// add components to UI
		panel.add(gridVisibility);
		panel.add(pauseButton);
		panel.add(volumeButton);
		panel.add(volumeControl);
		//

		panel.setPreferredSize(new Dimension(UIWidth, UIHeight));
		panel.setBackground(null);
		panel.setFocusable(false);

	}

	/**
	 * Creates the UIPanel and sets its characteristics
	 * 
	 * @param UIWidth
	 */
	public void createUIPanel(int UIWidth, int UIHeight) {

		// Create the panel that contains the "cards".
		UI = new JPanel(new CardLayout());
		UI.setPreferredSize(new Dimension(UIWidth, UIHeight));
		UI.setBorder(BorderFactory
				.createLineBorder(Color.white, UIBorderWeight));
		UI.add(panel);
		UI.setFocusable(false);
	}

	/**
	 * getter for volumeValue
	 * 
	 * @return
	 */
	public int getVolumeValue() {
		return volumeValue;
	}

	/**
	 * setter for volumeValue
	 * 
	 * @param volumeValue
	 */
	public void setVolumeValue(int volumeValue) {
		this.volumeValue = volumeValue;
	}
	
	/**
	 * getter for maxVolumeValue
	 * 
	 * @return
	 */
	public int getMaxVolumeValue(){
		return maxVolumeValue;
	}
	
	/**
	 * getter for minVolumeValue
	 * 
	 * @return
	 */
	public int getMinVolumeValue(){
		return minVolumeValue;
	}

	/**
	 * getter for UIBorderWeight
	 * 
	 * @return
	 */
	public int getUIBorderWeight() {
		return UIBorderWeight;
	}

	/**
	 * getter for showGrid
	 * 
	 * @return
	 */
	public boolean getShowGrid() {
		return showGrid;
	}

	/**
	 * getter for pause
	 * 
	 * @return
	 */
	public boolean getPause() {
		return pause;
	}

	/**
	 * getter for pauseButton
	 * 
	 * @return
	 */
	public JButton getPauseButton() {
		return pauseButton;
	}

	/**
	 * getter for UI
	 * 
	 * @return
	 */
	public JPanel getUI() {
		return UI;
	}

	/**
	 * getter for mute
	 * 
	 * @return
	 */
	public boolean getMute() {
		return mute;
	}

	/**
	 * getter for gridButtonCount
	 * 
	 * @return
	 */
	public int getGridButtonCount() {
		return gridButtonCount;
	}

	/**
	 * getter for gridVisibility
	 * 
	 * @return
	 */
	public JButton getGridVisibility() {
		return gridVisibility;
	}

	/**
	 * getter for volumeButton
	 * 
	 * @return
	 */
	public JButton getVolumeButton() {
		return volumeButton;
	}

	/**
	 * getter for panel
	 * 
	 * @return
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * getter volumeControl
	 * 
	 * @return
	 */
	public JSlider getVolumeControl() {
		return volumeControl;
	}

	/**
	 * getter for soundSliderHeight
	 * 
	 * @return
	 */
	public int getSoundSliderHeight() {
		return soundSliderHeight;
	}
	
	/**
	 * getter for fullVolume images icon
	 * @return
	 */
	public ImageIcon getFullVolumeIcon(){
		return fullVolume;
	}
	
	/**
	 * getter for lowVolume image icon
	 * @return
	 */
	public ImageIcon getLowVolumeIcon(){
		return lowVolume;
	}
	
	/**
	 * getter for noVolume image icon
	 * @return
	 */
	public ImageIcon getNoVolumeIcon(){
		return noVolume;
	}
	
	/**
	 * getter for muteVolume image icon
	 * @return
	 */
	public ImageIcon getMuteVolumeIcon(){
		return muteVolume;
	}
}
