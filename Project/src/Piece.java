
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.List;

/**
 * The Piece object has 2 different constructors, one to create a regular piece
 * (this is used for the visible and invisible pieces) and one to create a slot
 * in the HoldingArea. The slot is a piece which takes a color for the
 * background, this should match its surrounding areas color. The slot piece
 * will also receive a border around it so it looks like a hollow square.
 * <p>
 * Once a pieces dats is set (ie. the PieceData object from the .mze file) the
 * maze image is drawn on the piece.
 * <p>
 * A piece can also have its holding area set so when the piece is moved the
 * controller can simply check where it needs to be removed from.
 * <p>
 * A piece can be selected or deselected which will change its color to a darker
 * shade of blue if selected and reset back to the sky blue if deselected
 * 
 * @author Sebastian Colwell
 * @author Zach Kingsmore
 * @version %I%, %G%
 * @since 2.0
 */
public class Piece extends JPanel implements ActionListener {

  // Default serialVersion (required for extension of JPanel)
  private static final long serialVersionUID = 1L;

  // 100x100 per the instructions by Kim Buckner
  private static final Dimension pieceSize = new Dimension(100, 100);

  // Light blue color for pieces (by default)
  private static final Color pieceColor = Color.decode("#02CCFE");

  // When a piece is selected its color changes to a darker shade
  private static final Color selectedColor = Color.decode("#017a98");

  // When the PieceData is set then drawLines becomes true and the lines
  // are drawn
  private boolean drawLines = false;

  // The data from the default.mze file for this piece
  private PieceData pieceData;

  // Pointer to the holding area that holds this piece
  private HoldingArea holdingArea;

  // Used for the error flash when the user makes an invalid move
  private int flashCount = 1;

  /**
   * Class Constructor
   * <p>
   * This constructor creates a regular piece 100x100 and sky blue
   * 
   * @since 4.0
   */
  public Piece() {
    this.setBackground(pieceColor);
    this.setPreferredSize(pieceSize);
    return;
  }

  /**
   * Class Constructor that takes a color for the background of the piece
   * <p>
   * This constructor is also used to create an invisible piece but the color is
   * set to the background of the PlayerPieces holding area. This piece also
   * gets a blue border to make it look like an hollow square
   * 
   * @param background Color object used to "mask" the piece
   * 
   * @see HoldingArea
   * 
   * @since 3.0
   **/
  public Piece(Color background) {
    this.setBackground(background);
    this.setPreferredSize(pieceSize);
    Border border = BorderFactory.createLineBorder(pieceColor);
    this.setBorder(border);
    return;
  }

  /**
   * Sets the pointer to the holding area for this piece
   * 
   * @param HoldingArea The pointer to the HoldingArea object
   * 
   * @see HoldingArea
   * 
   * @since 4.0
   */
  public void setHoldingArea(HoldingArea HoldingArea) {
    holdingArea = HoldingArea;
    return;
  }

  /**
   * Returns the pointer to this pieces holding area
   * 
   * @returns holdingArea the pointer to the HoldingArea object
   * 
   * @since 4.0
   */
  public HoldingArea getHoldingArea() {
    return holdingArea;
  }

  /**
   * Draw the lines for the maze image
   * <p>
   * This will also rotate the lines according to the PieceDatas rotation value
   * 
   * @param PieceData the data that defines the coordinates for the lines on
   *                  this piece
   * 
   * @since 4.0
   */
  public void drawImage(PieceData PieceData) {
    pieceData = PieceData;
    drawLines = true;
    for (int i = 0; i < PieceData.getRotation() + 1; i++) {
      this.rotate();
    }
    this.revalidate();
    this.repaint();
    return;
  }

  /**
   * Change the color to a darker shade of blue
   * 
   * @since 4.0
   */
  public void select() {
    this.setBackground(selectedColor);
    return;
  }

  /**
   * Change the color to the original shade of blue
   * 
   * @since 4.0
   */
  public void deselect() {
    this.setBackground(pieceColor);
    return;
  }

  /**
   * Checks if the piece has lines drawn
   * <p>
   * Use to check if the piece has no lines (indicates that this is a
   * placeholder piece)
   * 
   * @returns boolean The piece is blank if no lines are drawn
   */
  public boolean isBlank() {
    return !drawLines;
  }

  /**
   * Rotates the pieceData and redraws the piece
   * 
   * @since 4.0
   */
  public void rotate() {
    pieceData.rotate();
    this.revalidate();
    this.repaint();
    return;
  }

  /**
   * Draw the Lines from the pieceData onto the JPanel
   * <p>
   * This overriden method draws the graphics onto the piece. If the piece is a
   * placeholder, do not draw anything. Otherwise set the line color to black
   * (may change to dark gray). Then use the coordinates from The tile data to
   * draw each line onto the Piece.
   * 
   * @param g Graphics object for the JPanel
   * 
   * @since 3.0
   **/
  @Override
  protected void paintComponent(Graphics g) {
    if (drawLines) {
      super.paintComponent(g);
      Graphics2D graphics = (Graphics2D) g;
      graphics.setColor(Color.black);
      graphics.setStroke(new BasicStroke(2));
      List<Integer> coords = pieceData.getCoords();
      for (int i = 0; i < coords.size(); i += 4) {
        graphics.drawLine(coords.get(i), coords.get(i + 1), coords.get(i + 2),
            coords.get(i + 3));
      }
    }
    return;
  }

  /**
   * Flash a Red border around the piece when an invalid move was made
   * <p>
   * This method handles the invalid move flashing. The GameController will
   * determine when an invalid move was made, then set a Timer for this Piece.
   * This Piece will then flash a border and stop the timer after 4 flashes.
   * 
   * @see GameController
   * @since 4.0
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (flashCount == 8) {
      flashCount = 1;
      this.setBorder(null);
      ((Timer) e.getSource()).stop();
      return;
    }
    if (flashCount % 2 == 0) {
      Border border = BorderFactory.createLineBorder(Color.RED, 5);
      this.setBorder(border);
    } else {
      this.setBorder(null);
    }
    flashCount++;
    return;
  }
}