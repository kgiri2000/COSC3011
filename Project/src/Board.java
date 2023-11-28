
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The game board for the Maze game.
 * <p>
 * The board object creates a 400x400 board with lines drawn on the JPanel to
 * create a board effect. This object is composed of a list of pieces named
 * positions, each index of this list is a position on the board. To handle the
 * movement, pieces can be added and removed from the positions list. When a
 * piece is removed, an invisible piece gets swapped in its place. This keeps
 * all other pieces in the same position after a piece is removed.
 * <p>
 * Each time a piece is added or removed the board is redrawn. This removes all
 * elements from the board, and replaces each piece in the positions list.
 * <p>
 * On initialization the positions list is filled with invisible Pieces. When a
 * piece is moved from the holding area to the board, the positions list is
 * updated and the board is redrawn. This is how the board object handles
 * movement of pieces. In order to move a piece onto a different square in the
 * board, it must be removed from the board first then added to a different
 * position.
 * 
 * @see GameController
 * @see Piece
 * 
 * @author Sebastian Colwell
 * @author Zach Kingsmore
 * 
 * @version %I%, %G%
 * @since 2.0
 */
public class Board extends JPanel {

  // Default serialVersion (required for extension of JPanel)
  private static final long serialVersionUID = 1L;

  // 16 Pieces = 4x4 grid each Piece is 100x100
  private static final Dimension boardSize = new Dimension(400, 400);

  // Sky blue background (same as the pieces)
  private static final Color boardColor = Color.decode("#02CCFE");

  // List of every piece on the board
  private List<Piece> positions = new ArrayList<Piece>();

  /**
   * Class Constructor.
   * <p>
   * This gives the board a sky blue background, and a 400x400 size This also
   * sets a 4x4 GridLayout used to keep all pieces in the correct spot within
   * the board.
   * <p>
   * clearBoard is called to initialize the positions list
   * 
   * @since 3.0
   **/
  public Board() {
    this.setBackground(boardColor);
    this.setPreferredSize(boardSize);
    this.setLayout(new GridLayout(4, 4));
    clearBoard();
    return;
  }

  /**
   * Returns the board positions
   * 
   * @returns positions List of all pieces on the board
   * @since 4.0
   */
  public List<Piece> getPositions() {
    return positions;
  }

  /**
   * Resets the board positions to all blanks.
   * <p>
   * Clear the list of pieces and then populate it with 16 blank pieces This
   * resets the board. The board is redrawn upon a clear.
   * 
   * @since 4.0
   */
  public void clearBoard() {
    positions.clear();
    for (int i = 0; i < 16; i++) {
      Piece p = new Piece();
      p.setVisible(false);
      positions.add(p);
    }
    drawBoard();
    return;
  }

  /**
   * Displays all pieces inside the positions list.
   * <p>
   * Removes all the Pieces on the board first. This ensures that no pieces are
   * drawn over. Then once the board is blank, the positions list is iterated
   * over and each Piece is added to the board.
   * <p>
   * Revalidate and repaint are necessary to update the JPanel
   * 
   * @since 2.0
   */
  public void drawBoard() {
    this.removeAll();
    for (int i = 0; i < 16; i++) {
      this.add(positions.get(i));
    }
    this.revalidate();
    this.repaint();
    return;
  }

  /**
   * Adds a piece to a specific coordinate on the gird.
   * <p>
   * Check if the piece already exists on the board, remove it if the piece
   * already exists. This avoids duplicated pieces or "stuck" pieces. Add the
   * piece to the correct grid location.
   * 
   * @param piece The piece that is being moved on the board
   * @param x     The integer x coordinate of the grid
   * @param y     the integer y coordinate of the grid
   * 
   * @since 4.0
   */
  public void addPiece(Piece piece, int x, int y) {
    if (piece.getParent() == this) {
      removePiece(piece);
    }

    // The positions list is row-major, (single dimension)
    // Given a coordinate pair ie (4,3) calculate the index
    int index = (y * 4) + x;
    positions.set(index, piece);
    drawBoard();
    return;
  }

  /**
   * Remove a piece from the board
   * <p>
   * The piece must exist within the board. Then it is replaced with an
   * invisible piece.
   * 
   * @param piece The piece to be moved off the board
   * 
   * @since 4.0
   */
  public void removePiece(Piece piece) {
    if (piece.getParent() == this) {
      Piece p = new Piece();
      p.setVisible(false);
      positions.set(positions.indexOf(piece), p);
      drawBoard();
    }
    return;
  }

  /**
   * Draw the lines onto the board to make a grid.
   * <p>
   * This method draws the grid lines. This is a method from JPanel and overrode
   * here. 3 Black lines are drawn 100px apart down the horizontal and vertical
   * axis of the Board object. This is done through the Graphics.drawLine method
   * which takes 4 coordinates in order to draw the line on the current JPanel.
   * 
   * @param g The graphics object of the JPanel
   * 
   * @since 3.0
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.black);
    for (int i = 100; i < 400; i += 100) {
      g.drawLine(i, 0, i, 400);
      g.drawLine(0, i, 400, i);
    }
    return;
  }
}