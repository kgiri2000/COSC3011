
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The GameWindow object is the main GUI. This object places the Game Controller
 * within the JFrame. This object also defines its size, color, and path to the
 * default.mze file (given by Professor Kim Buckner). The GameWindow is
 * responsible for reading the default.mze data and creating/placing both the
 * Menu and Game into the GUI.
 * <p>
 * On initialization the GameWindow requires a title so our Group name (H) can
 * be displayed at the top of the window. The default close operation is also
 * set so that the program quits when the GUI is closed. The layout of the GUI
 * is GridBagLayout which is set on init as well. This layout used to size and
 * place the GameController within the GUI.
 * <p>
 * The default.mze file is attempted to be opened and read into a byte array. if
 * there is a failure, a popup is displayed. Otherwise the MzeFileReader class
 * reads the byte array into a list of PieceData objects, each stores a set of
 * coordinates for the lines to be displayed on the tiles. method.
 * 
 * @see MzeFileReader
 * @see GameController
 * 
 * @author Sebastian Colwell
 * @author Zach Kingsmore
 * @version %I%, %G%
 * @since 1.0
 */
public class GameWindow extends JFrame {

  // Default serialVersion (required for extension of JFrame)
  private static final long serialVersionUID = 1L;

  // 900x1000 is from the instructions in Prog1
  private static final Dimension windowSize = new Dimension(900, 1000);

  // Dark grey background (almost black but this is for the very back layer)
  private static final Color greyBackground = Color.decode("#1C1C1C");

  // Path to the mze file (relative) This was given by Kim Buckner
  private static final String relativeMzePath = "input/default.mze";

  // The data read from the default.mze file (stored as PieceData objects)
  private List<PieceData> pieceData;

  /**
   * Class Constructor
   * <p>
   * Initializes the GUI. This sets the window title of the GUI, the close
   * operation, and gives the GUI a GridBagLayout. The setup method is then
   * called to place the core GUI components.
   * 
   * @param windowTitle The title that gets displayed at the top of the window
   *                    when the GUI is shown.
   * @since 1.0
   */
  public GameWindow(String windowTitle) {
    super(windowTitle);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.getContentPane().setLayout(new GridBagLayout());
    setupGUI();
    return;
  }

  /**
   * Read default.mze data, and setup controller
   * <p>
   * The setup method reads the default.mze file then places the GameController.
   * <p>
   * The GameController is an extension of a JPanel, this component contains all
   * core elements of the Maze Game. This is placed in the center of the
   * GameWindow and will fill the entire JFrame with a 5px inset. The inset just
   * makes a 5px background (dark grey) border around the controller.
   * 
   * @see MzeFileReader
   * @see GameController
   * 
   * @since 3.0
   */
  private void setupGUI() {
    readMze();
    GridBagConstraints c = new GridBagConstraints();

    // 5px insets (creates background border) fills remaining screen
    c.insets = new Insets(5, 5, 5, 5);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1;
    c.weighty = 1;
    this.add(new GameController(pieceData), c);
    return;
  }

  /**
   * Read the default.mze file data
   * <p>
   * This method trys to locate the default.mze file from the relative path
   * defined. Then reads all the bytes in that file into a byte array That byte
   * array is then passed into the MzeFileReader so it can decipher the data and
   * store all the pieces data. Each pieces data is a series of coordinates
   * which coorespond to lines that will be drawn on a given piece to make a
   * maze segment.
   * <p>
   * If the file could not be found, or if the data could not be read into a
   * byte array. Display a failure popup window. This will close the program.
   * 
   * @see MzeFileReader
   * 
   * @since 3.0
   */
  private void readMze() {
    try {
      Path path = Paths.get(relativeMzePath);
      byte[] array = Files.readAllBytes(path);
      pieceData = MzeFileReader.readData(array);
    } catch (FileNotFoundException e) {
      showFailurePopup("Could not find file");
    } catch (IOException e) {
      showFailurePopup("Could not read file");
    }
    return;
  }

  /**
   * Display failure popup if default.mze could not be read
   * <p>
   * Display a JOptionPane as a failure popup. This popup only has 1 option
   * "OK", there will be a message that explains the error and once the user
   * clicks "OK" the program will exit.
   * 
   * @param text This is the error message displayed inside the popup
   * 
   * @since 3.0
   */
  private void showFailurePopup(String text) {
    JOptionPane.showConfirmDialog(null, "ERROR: " + text, "default.mze Failure",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    System.exit(0);
    return;
  }

  /**
   * Display GUI to user
   * <p>
   * This method is called inside Main once the GUI is constructed The GUI's
   * default size and color is set. Then the GUI is displayed to the user.
   * 
   * @see Main
   * 
   * @since 3.0
   */
  public void showGUI() {
    this.setSize(windowSize);
    this.getContentPane().setSize(windowSize);
    this.getContentPane().setBackground(greyBackground);
    this.setVisible(true);
    return;
  }
}