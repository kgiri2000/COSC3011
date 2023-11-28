
import java.awt.*;
import javax.swing.*;

/**
 * Menu Panel for Maze Game
 * <p>
 * The Menu object is a light grey JPanel which has 3 buttons. This creates a
 * nested appearance when displayed on the GUI. The Menu itself is a darker gray
 * color and that is what is placed into the GameWindow.
 * <p>
 * Each button defines its own size and the menu will adjust its own size based
 * on the button layout
 * 
 * @see GameController
 * 
 * @author Sebastian Colwell
 * @version %I%, %G%
 * @since 2.0
 **/
public class Menu extends JPanel {

  // Default serialVersion (required for extension of JPanel)
  private static final long serialVersionUID = 1L;

  // Sky Blue color for buttons (same as pieces)
  private static final Color buttonColor = Color.decode("#02CCFE");

  // Buttons are a 100x50 to show the entirety of the text
  private static final Dimension buttonSize = new Dimension(100, 50);

  // The menu itself is a light grey color
  private static final Color menuColor = Color.decode("#4A4A4A");

  // The buttons on the menu are protected so the controller can access them
  private JButton new_game, reset, quit;

  /**
   * Class Constructor
   * <p>
   * The constructor creates a non-fixed size JPanel with a light grey color.
   * Then adds the 3 buttons to the menu.
   * <p>
   * The buttons are stored in protected variables so that the GameController
   * can access them and add the necessary functionality
   * 
   * @see GameController
   * 
   * @since 3.0
   **/
  public Menu() {
    this.setBackground(menuColor);
    this.setLayout(new GridBagLayout());
    new_game = addMenuButton("New Game", 0);
    reset = addMenuButton("Reset", 1);
    quit = addMenuButton("Quit", 2);
    return;
  }

  /**
   * Getter for New Game button
   * 
   * @return new game button
   */
  public JButton getNewGame() {
    return new_game;
  }

  /**
   * Getter for Reset button
   * 
   * @return reset button
   */
  public JButton getReset() {
    return reset;
  }

  /**
   * Getter for Quit button
   * 
   * @return quit button
   */
  public JButton getQuit() {
    return quit;
  }

  /**
   * Add a JButton to the Menu Panel
   * <p>
   * This creates and adds a JButton to the menu. Both the text displayed on the
   * button and the position within the menu must be specified. The buttons have
   * 10 px insets to create a spacing between the buttons within the menu.
   * 
   * @param text      The text displayed in the center of the button
   * @param xPosition Where the button "slots" into. This is along the x-axis of
   *                  the subFrame so slot=0 is leftmost button.
   * 
   * @returns button The JButton is returned so it can be stored in the class.
   *          This allows GameController to access it
   * 
   * @see GameController
   * 
   * @since 3.0
   */
  private JButton addMenuButton(String label, int xPosition) {
    JButton button = new JButton(label);
    button.setBackground(buttonColor);
    button.setPreferredSize(buttonSize);
    button.setMinimumSize(buttonSize);

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 10, 10);
    c.gridx = xPosition;
    c.anchor = GridBagConstraints.CENTER;
    this.add(button, c);
    return button;
  }
}