
import javax.swing.*;

/**
 * Main simply instantiates the game and calls the show method from the game
 * object. main also sets the look and feel of the UIManager to Nimbus. The look
 * and feel is wrapped within a try catch statement to avoid any possible
 * crashes
 * <p>
 * The Nimbus look and feel code, along with the try catch statements were given
 * by Professor Kim Buckner in version 1.0 of the program
 * 
 * @see GameWindow
 * 
 * @author Sebastian Colwell
 * @version %I%, %G%
 * @since 1.0
 */
public class Main {
  public static void main(String[] args) {
    // Declare GameWindow object with group name in the window title
    GameWindow game = new GameWindow("Hotel Group Maze Game");

    // Make GUI visible
    game.showGUI();

    // Set the overall Feel of the GUI to Nimbus
    // NOTE: May want to explore other look/feels?
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (UnsupportedLookAndFeelException e) {
      System.out.println("Cannot set Look and Feel to Nimbus, using default");
    } catch (ClassNotFoundException e) {
      System.out.println("UIManager class not found, import javax.swing.*");
    } catch (InstantiationException e) {
      System.out.println("Cannot Instantiate UIManager class, using default");
    } catch (IllegalAccessException e) {
      System.out.println("UIManager cannot set look at feel, using default");
    }
    return;
  }
}       