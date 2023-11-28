
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * HoldingAreas for the pieces in the Maze Game
 * <p>
 * HoldingArea defines the holding area for the Pieces, There are 2 holding
 * areas. One on each side of the board. Two of these objects are instantiated
 * in the GameController, each have half the tiles added to them.
 * <p>
 * The Left HoldingArea gets tiles 0-7 and the right HoldingArea gets tiles
 * 8-15. This Object is a JPanel with a lighter gray background. It can Add and
 * remove pieces from itself.
 * <p>
 * There is a list of Pieces which is what is used to update the holding area
 * when a piece is moved. For example when a piece moves from the holding area
 * to the board, its position is updated with a placeholder piece and then this
 * object is redrawn.
 * 
 * @see Piece
 * @see GameController
 * 
 * @author Sebastian Colwell
 * @version %I%, %G%
 * @since 2.0
 */
public class HoldingArea extends JPanel {
  // Default serialVersion (required for extension of JPanel)
  private static final long serialVersionUID = 1L;

  // Light grey background for the holding area
  private static final Color dgreyBackground = Color.decode("#4A4A4A");

  // The list of pieces held by this holding area
  private List<Piece> heldPieces = new ArrayList<Piece>();

  /**
   * Class Constructor
   * <p>
   * The HoldingArea object is just a light gray JPanel its size is defined by
   * the number of pieces inside this object. This is why the Piece object needs
   * a second constructor where the piece is visible but can be masked with this
   * light gray color. This keeps the holding area the same size no matter how
   * many pieces have been replaced with the placeholders. This avoids the
   * shrinking/expanding of this object when pieces are moved.
   * 
   * @see Piece
   * 
   * @since 3.0
   */
  public HoldingArea() {
    this.setBackground(dgreyBackground);
    this.setLayout(new GridBagLayout());
    return;
  }

  /**
   * Getter for heldPieces
   * 
   * @return List of Held Pieces
   */
  public List<Piece> getPieces() {
    return heldPieces;
  }

  /**
   * Draws all pieces inside the held pieces list
   * <p>
   * This removes all pieces from the holding area, then redraws the holding
   * area with the pieces from the pieces list.
   * <p>
   * When a piece is moved onto the board it is removed from the pieces list,
   * then the holding area is redrawn with the placeholder in its spot.
   * <p>
   * The first piece gets a 15px inset at the top, the last piece gets a 15px
   * inset at the bottom, eveyrthing else gets a 3px border top & bottom. This
   * creates a 15 pixel border around all pieces to make them look nice within
   * the holding area
   * 
   * @since 3.0
   */
  public void drawPieces() {
    this.removeAll();
    GridBagConstraints c = new GridBagConstraints();
    for (int i = 0; i < heldPieces.size(); i++) {
      c.gridy = i;
      if (i == 0) {
        c.insets = new Insets(15, 15, 3, 15);
      } else if (i == heldPieces.size() - 1) {
        c.insets = new Insets(3, 15, 15, 15);
      } else {
        c.insets = new Insets(3, 15, 3, 15);
      }
      this.add(heldPieces.get(i), c);
    }
    this.revalidate();
    this.repaint();
    return;
  }

  /**
   * Adds a piece to the held pieces list and redraws the holding area
   * 
   * @param piece the piece to be added to the list
   * 
   * @see Piece
   * 
   * @since 4.0
   */
  public void addPiece(Piece piece) {
    heldPieces.add(piece);
    drawPieces();
    return;
  }

  /**
   * Clears the list of held pieces
   * 
   * @since 4.0
   */
  public void clearPieces() {
    heldPieces.clear();
    return;
  }

  /**
   * Replace an existing piece with a place holder.
   * <p>
   * This takes an existing piece (the placeholder slot) and the newPiece (the
   * selected piece on the board) and replaces the existing piece with the new
   * piece. This is how pieces move back to a holding area from the board.
   * 
   * @param existing The existing piece in the holding area
   * @param newPiece The piece that will replace the existing piece
   * 
   * @see Piece
   * 
   * @since 4.0
   */
  public void replace(Piece existing, Piece newPiece) {
    heldPieces.set(heldPieces.indexOf(existing), newPiece);
    drawPieces();
    return;
  }

  /**
   * Remove a piece from the holding area and replace it with a blank
   * <p>
   * To remove a piece from the holding area it must be replaced with an empty
   * placeholder slot. So a blank is created within the GameController and
   * passed in when a piece is being removed from the holding area and moved
   * onto the board.
   * <p>
   * The blank is created in the GameController so the controller can listen to
   * for a click on a blank slot. This is how pieces are moved back to a holding
   * area.
   * <p>
   * If the piece exits in the holding area, replace the piece with the blank
   * 
   * @param piece The selected piece being moved out of the holding area
   * @param blank The blank placeholder that will take its place in the holding
   *              area
   * 
   * @see GameController
   * 
   * @since 4.0
   */
  public void removePiece(Piece piece, Piece blank) {
    int pieceIndex = heldPieces.indexOf(piece);
    if (pieceIndex >= 0) {
      blank.setHoldingArea(this);
      heldPieces.set(heldPieces.indexOf(piece), blank);
    }
    drawPieces();
    return;
  }

}